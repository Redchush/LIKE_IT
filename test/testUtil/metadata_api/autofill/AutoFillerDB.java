package testUtil.metadata_api.autofill;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;
import testUtil.metadata_api.metaData.data.Table;
import testUtil.metadata_api.metaData.data.type.TypeDescription;
import testUtil.metadata_api.metaData.handler.TableManager;
import testUtil.metadata_api.metaData.data.column.Column;
import testUtil.metadata_api.metaData.handler.type.TypeHandler;
import testUtil.metadata_api.metaData.handler.type.TypeHandlerFactory;
import testUtil.metadata_api.randomizer.RandomHandler;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.stream.Collectors;


public class AutoFillerDB {

    private static Logger LOGGER = LogManager.getLogger("AutoFillerDB");

    private static final String INSERT = "INSERT INTO ";
    private static final String VALUES = "\nVALUES ";
    private static final String DEFAULT = "DEFAULT";

    private TableManager manager;
    private Connection connection;
    private final AutoFillerConfigurator configurator;
    private TypeHandlerFactory HANDLER_FACTORY = TypeHandlerFactory.getInstance();

    public AutoFillerDB(AutoFillerConfigurator configurator){
        this.configurator = configurator;
    }

    public void init(Connection connection, TableManager manager) throws MetaDataException {
        this.manager = manager;

        try{
            if (connection == null){
                throw new MetaDataException("AutoFillerDB can'be initialized by null connection");
            }
            this.connection = connection;
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.debug("Problem with connection pool",e);
            throw new MetaDataException("Problem with connection" , e);
        }
    }


//    public void updateByRandom(Table table, Column column, int count){
//
//        String pattern = "UPDATE %s SET %s = ? WHERE id = ?";
//        String total = String.format(pattern, table.getTableName(), column.getName());
//
//        for (int i = 0; i < count; i++) {
//            try(PreparedStatement preparedStatement = connection.prepareStatement(total);){
//                preparedStatement.setLong(2, (long) i);
//                String simpleName = column.getType().getExample().getClass().getSimpleName();
//                applyRandomToStatement(null, null, 1, column, preparedStatement);
//                preparedStatement.executeUpdate();
//                connection.commit();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

    public void fill() throws MetaDataException {
        try{
            NavigableSet<Table> tables = manager.getCurrentDatabase().getOrderedTables();
            for (Table table : tables) {
                fillOneTable(table);
            }
        } catch (SQLException e) {
            LOGGER.debug("Some sql exeption during takeing Connection" , e);
            throw new MetaDataException("Problem with Connection" , e);
        }
    }

    public void fillOneTable(Table table) throws SQLException, MetaDataException {
        String tableName = table.getTableName();
        boolean isFullFilled =  checkSqriptMap(connection, tableName);
        if (isFullFilled){
            return;
        }
        boolean isId = manager.hasIdColumn(table);
        String create = getCreate(table);

        LOGGER.trace("Create statement " + create);
        int exactRowCount = getRowCountConstraint(tableName); // change if it has constrain on rows meant criteria_to be inserted
        long last_inserted_id = 0;
        for (int i = 0; i < exactRowCount; i++) {
            int duplicateCounter = 0;
            String statementToDebug = null;
            try(PreparedStatement statement = connection.prepareStatement(create, Statement.RETURN_GENERATED_KEYS)){
                fillStatement(table, statement, isId, i);
                statementToDebug = statement.toString();
                statement.executeUpdate();
                ResultSet set = statement.getGeneratedKeys();
                last_inserted_id = 0;
                if(set.next()){
                    last_inserted_id = set.getLong(1);
                }

                connection.commit();
            }catch (SQLException e) {
                if (! (e.getSQLState().equalsIgnoreCase("23000"))){ // 23000 - not Unique value,  Example (Duplicate entry 'перекисать' for key 'name_UNIQUE' at 22001 -- : Out of range value
                    LOGGER.debug("Some sql exception occured" + " create: " + create +
                            "with filled statement: \n"  + statementToDebug +"\n" + e.getSQLState(), e);
                    throw new MetaDataException("Exception in update ", e);
                } else {
                    LOGGER.debug("Occured duplicate key"  + " create: " + create + "with filled statement: " +
                            "\n"  + statementToDebug + "\n" + e.getSQLState());
                    duplicateCounter++;
                    if (duplicateCounter > 5){
                        throw new MetaDataException("More than 5 attemps vo enter the data", e);
                    }
                    if (e.getMessage().contains("foreign key constraint fails")&&(duplicateCounter > 5)){
                        throw new MetaDataException("Foreign key constraint fails ", e);
                    }
                    if (manager.hasIdColumn(table)) {
                        try (Statement statement = connection.createStatement()) {
                            String pattern = "ALTER TABLE `LIKE_IT`.`%s`  AUTO_INCREMENT = %d";
                            String filled = String.format(pattern, tableName, last_inserted_id);
                            statement.executeUpdate(filled);
                            connection.commit();
                        }
                        i--;
                    }
                }
            }
        }
    }


    private boolean checkSqriptMap(Connection connection, String tableName) throws SQLException {
        Map<String, String> scriptMap = configurator.getScriptMap();
        String script = null;
        if (scriptMap.isEmpty() || ( (script = (scriptMap.get(tableName))) == null)){
            return false;
        }
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(script);
            connection.commit();
            scriptMap.remove(tableName);
        } catch (SQLException e){
            switch (configurator.getScriptOption()){
                case END_RUNNING:
                    throw e;
                case USE_AUTOFULL:
                   configurator.getConstraintTableMap().remove(tableName);
                    break;
                case IGNORE:
                    LOGGER.info("Outer script of table " + tableName + " is broken, therefore ignored.");
                    /*NOP*/
                    break;

            }
        }
        return true;
    }

    private void fillStatement(Table table,
                                   PreparedStatement statement,
                                   boolean isId, int i) throws SQLException {
        List<Column> columnsInOrder = table.getColumnsInOrder();
        if (isId) {
            columnsInOrder = columnsInOrder.subList(1, columnsInOrder.size());
        }
        for (Column column : columnsInOrder) {
            int order = column.getOrder();

            if (isId){
                order = order - 1;
            }
            boolean isImported = manager.isImportedColumn(table, column);
            Double min = null;
            Double max = null;

            if (column.getType().getExample().equals("")){
                double maxValue = column.getType().getMaxValue();
                int maxStringLength = configurator.getMaxStringLength();

                if (maxValue > maxStringLength*2){
                    max = (double) maxStringLength;
                }
            }

            if (isImported){
                String refTableName = manager.getImportedColumnByColumn(table, column).getRefTable();
                max = (double) getRowCountConstraint(refTableName);
                int setNullInInterrelated = configurator.getSetNullInInterrelated();

                if (refTableName.equals(table.getTableName())){
                    if (i < setNullInInterrelated){
                        statement.setNull(order, column.getType().getSqlDataType());
                        continue;
                    }
                    max = (double) (i+1);
                }
                min = 1d;
                LOGGER.trace("Imported constraint: max vo" + max + " min " + min);
            }
            applyRandomToStatement(min, max, order, column, statement);

        }
    }

    private void applyRandomToStatement(Double min, Double max, int order, Column column, PreparedStatement statement)
            throws SQLException {
        TypeDescription type = column.getType();
        Object randomByType = RandomHandler.getInstance().getRandomByType(min, max, type);
        HANDLER_FACTORY.getWrapperByClassName(type.getExample().getClass().getSimpleName())
                       .applySetToStatement(statement, order, randomByType);
    }

    /**
     *
     * @param tableName
     * @return constraint laying in constraint map
     */
    private int getRowCountConstraint(String tableName){
        Map<String, Integer> constraintTableMap = configurator.getConstraintTableMap();
        int result = configurator.getRowCount();

        if (constraintTableMap.size() > 0){
            Integer temp = constraintTableMap.get(tableName);
            if (temp != null){
                result = temp;
            }
        }
        return result;
    }

    /**
     *
     * @param table criteria_to be created
     * @return String containing insert expression
     */
    public String getCreate(Table table) {

        List<String> collected = manager.getListOfColumnNames(table, false);
        String fieldsString = collectInParenthises(collected);
        List<String> sings = Collections.nCopies(collected.size(), "?");
        String setPart = collectInParenthises(sings);

        boolean hasId = manager.hasIdColumn(table);
        setPart = hasId ? setPart.replaceFirst("\\?", DEFAULT) : setPart;

        StringBuilder query = new StringBuilder(INSERT);
        query.append(table.getTableName())
             .append(" ")
             .append(fieldsString)
             .append(VALUES)
             .append(setPart);
        return query.toString();
    }

    private String collectInParenthises(List<String> target){
        return target.stream()
                     .collect(Collectors.joining(", ","(", ")"));
    }


}
