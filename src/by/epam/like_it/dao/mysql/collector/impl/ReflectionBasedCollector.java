package by.epam.like_it.dao.mysql.collector.impl;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.dao.mysql.collector.type.TypeHandler;
import by.epam.like_it.dao.mysql.collector.type.TypeHandlerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionBasedCollector<T> implements FullStackCollector<T> {

    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    protected static final TypeHandlerFactory factory = TypeHandlerFactory.getInstance();

    public enum Mode {
        SET_NULL_IF_NULL, SKIP_IF_NULL
    }
    private int skip;
    private Mode mode;

    public ReflectionBasedCollector() {
        skip = 0;
        mode = Mode.SET_NULL_IF_NULL;
    }

    public ReflectionBasedCollector(int skip) {
        this.skip = skip;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     *  So there no point in this type of PROFILE_COLLECTOR using aliasPrefix,
     *  redirect criteria_to other method for simplifying overriding in case of inheritance;
     *  Attention! For reason criteria_to simplify interconnection with java instance containers
     *  the shift counter start with 0, so use 0 as initial
     *  value for shift
     */
    @Override
    public T collectEntity(ResultSet set, int shift,
                           String aliasPrefix, T example) throws SQLException, PersistenceCollectorException {
        return reallyCollect(set, shift, example);
    }
    /**


     /**
     *  State S1009 - reason of incorrect getXXX
     *  Attention! For reason criteria_to simplify interconnection with java instance containers
     *  the shift counter start with 0, so use 0 as initial
     *  value for shift
     * @param set - Result Set as source
     * @param shift - current position -1 in row of result set
     * @param example - example
     * @return collected entity
     * @throws SQLException - skipped exception
     * @throws PersistenceCollectorException - internal breakage of collector
     */
    public T reallyCollect(ResultSet set, int shift, T example) throws SQLException, PersistenceCollectorException {
        String referencedTable = ResourceNavigator.getRefTable(example.getClass());

        Field[] declaredFields = null;
        T instance = null;
        try{
            instance = (T) example.getClass().newInstance();
            declaredFields = instance.getClass().getDeclaredFields();
            Arrays.stream(declaredFields).forEach(s-> s.setAccessible(true));
            int position = shift + 1;
            for (int i = skip; i < declaredFields.length; i++) {
                Field field = declaredFields[i];
                try{
                    Object extract = extract(set, position, field.getType().getSimpleName(), field, instance);
                    if ( field.getName().equals("id") && (extract == null || extract.equals(0))) {
                        return null;
                    }
                } catch (SQLException e){
                    if (e.getSQLState().equals("S1009")){
                        LOGGER.info("Incorrect types in bean lead criteria_to incorrect using statement.getXX.", e);
                    }
                }
                position++;
            }
        } catch (IllegalAccessException e) {
            throw new PersistenceCollectorException("Unexpected error in collecting", e);
        } catch (InstantiationException e) {
            throw new PersistenceCollectorException("This is not bean instance : not contain default constructor", e);
        } finally {
            if (declaredFields != null){
                Arrays.stream(declaredFields).forEach(s->s.setAccessible(false));
            }
        }
        return instance;
    }



    protected Object extract(ResultSet set, int pos, String typeName, Field field, T instance)
            throws IllegalAccessException, SQLException {
        TypeHandler<?> wrapperByClassName = factory.getWrapperByClassName(typeName);
        Object dataFromResultSet = wrapperByClassName.getDataFromResultSet(set, pos);
        field.set(instance, dataFromResultSet);
        return dataFromResultSet;
    }

    /**
     *
     * @param statement - PreparedStatement need be fulfilled
     * @param from - position from which start the enriching
     * @param instance - source of data
     * @return real position on which the filling statement stopped.
     * @throws SQLException - skipped exception
     * @throws PersistenceCollectorException - internal breakage of collector
     */
    @Override
    public int fillStatement(PreparedStatement statement, int from, T instance)
            throws SQLException, PersistenceCollectorException {
        ParameterMetaData parameterMetaData = statement.getParameterMetaData();
        int parameterCount = parameterMetaData.getParameterCount();
        if (parameterCount == 0){
            return from;
        }
        String referencedTable = ResourceNavigator.getRefTable(instance.getClass());
        List<Integer> sqlTypes = ResourceNavigator.getAllTableTypesList(referencedTable);
        Field[] declaredFields = null;
        int position =  from + 1;
        try{
            declaredFields = instance.getClass().getDeclaredFields();
            Arrays.stream(declaredFields).forEach(s->s.setAccessible(true));

            List<Field> fields = Arrays.asList(declaredFields);

            if (mode == Mode.SKIP_IF_NULL){
                fields = Arrays.stream(declaredFields).filter(s -> {
                    try {
                        return s.get(instance) != null;
                    } catch (IllegalAccessException e) {
                        LOGGER.debug("Inappropriate bean ");
                        return false;
                    }
                }).collect(Collectors.toList());
            }

            for (int i = skip; i < fields.size(); i++) {
                Field field = fields.get(i);
                Object value = field.get(instance);
                String simpleTypeName = field.getType().getSimpleName();
                if (value == null || ( simpleTypeName.equals("String") && ((String) value).isEmpty()) ){
                    Integer appropriateType = getAppropriateType(declaredFields, position, sqlTypes, field);
                    setNullToStatement(statement, position, appropriateType);
                } else {
                    setToStatement(statement, position,simpleTypeName, value);
                }
                position++;
            }
        } catch (IllegalAccessException e) {
            throw new PersistenceCollectorException("Unexpected error in collecting", e);
        }  finally {
            if (declaredFields != null){
                Arrays.stream(declaredFields).forEach(s->s.setAccessible(false));
            }
        }
        return position;
    }

    private Integer getAppropriateType(Field[] fields, int position, List<Integer> types, Field field){
        if (this.mode == Mode.SET_NULL_IF_NULL){
            return types.get(position);
        } else {
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].equals(field)){
                    return types.get(i);
                }
            }
            return 1;
        }
    }

    private void setNullToStatement(PreparedStatement statement, int position, Integer sqlType) throws SQLException {
        statement.setNull(position, sqlType);
    }


    private void setToStatement(PreparedStatement statement, int position,
                                String typeName, Object value)
            throws IllegalAccessException, SQLException {
        TypeHandler<?> wrapperByClassName = factory.getWrapperByClassName(typeName);
        wrapperByClassName.applySetToStatement(statement, position, value);

    }
}
