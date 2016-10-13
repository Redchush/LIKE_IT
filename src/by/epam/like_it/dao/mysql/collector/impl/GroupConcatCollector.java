package by.epam.like_it.dao.mysql.collector.impl;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.mysql.collector.type.TypeHandler;
import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;
import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GroupConcatCollector<T> extends ReflectionBasedCollector<T> {

    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    /**
     *
     * @param set - current Result Set
     * @param aliasPrefix - can be both number in resultSet and alias for the field
     * @param example - example of bean to be collected
     * @return list of collected entities
     * @throws SQLException - skipped db exception
     * @throws PersistenceCollectorException - if something wrong with collector
     */
    @Override
    public List<T> collectEntityList(ResultSet set, String aliasPrefix, T example)
            throws SQLException, PersistenceCollectorException {

        String referencedTable = ResourceNavigator.getRefTable(example.getClass());
        List<String> allBeanTypes = ResourceNavigator.getALLTypessList(example.getClass()
                                                                          .getSimpleName(), false);
        int shift = 0;
        try {
            shift = Integer.parseInt(aliasPrefix);
        } catch (NumberFormatException e){
            /*NOP*/
        }

        Field[] declaredFields = null;
        List<T> result;
        try{
            declaredFields = example.getClass().getDeclaredFields();
            Arrays.stream(declaredFields).forEach(s->s.setAccessible(true));
            result = Collections.nCopies(declaredFields.length, example);

            for (int i = 0; i < declaredFields.length; i++) {
                Field field = declaredFields[i];
                int position =  i + shift + 1;
                T instance = result.get(i);
                try{
                    String[] split = set.getString(position).split(", ");
                    List<String> strings = Arrays.asList(split);
                    TypeHandler<?> wrapperByClassName = factory.getWrapperByClassName(allBeanTypes.get(i));
                    List<?> objects = wrapperByClassName.castFromString(strings);
                     T exctract = extract(allBeanTypes.get(i), field, instance);
                    if ( i == 0 && (exctract == null || exctract.equals(0))) {
                        return null;
                    }
                }catch (IllegalArgumentException e) {
                    LOGGER.info("Incorrect types mapping."+ e.getMessage() + "Trying use bean types.", e);
                    extract(set, position, field.getType().getSimpleName(), field, instance);
                }catch (SQLException e){
                    if (e.getSQLState().equals("S1009")){
                        LOGGER.info("Incorrect types mapping lead criteria_to incorrect using statement.getXX. Trying use bean " +
                                "types", e);
                        extract(set, position, field.getType().getSimpleName(), field, instance);
                    }
                }
            }
            Arrays.stream(declaredFields).forEach(s->s.setAccessible(false));
        } catch (IllegalAccessException e) {
            throw new PersistenceCollectorException("Unexpected error in collecting", e);
        }  finally {
            if (declaredFields != null){
                Arrays.stream(declaredFields).forEach(s->s.setAccessible(false));
            }
        }
        return result;
    }

    protected T extract(String typeName, Field field, Object instance)
            throws IllegalAccessException, SQLException {
       return null;
    }
}

