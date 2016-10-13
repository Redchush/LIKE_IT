package testUtil.metadata_api.mapper;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testUtil.metadata_api.autofill.MetaDataException;
import testUtil.metadata_api.metaData.data.Database;
import testUtil.metadata_api.metaData.data.Table;
import testUtil.metadata_api.metaData.handler.TableManager;
import testUtil.metadata_api.metaData.data.column.Column;
import testUtil.metadata_api.metaData.data.column.ExportedColumn;
import testUtil.metadata_api.metaData.data.column.ImportedColumn;
import testUtil.metadata_api.metaData.data.type.SqlTypeBuilder;
import testUtil.metadata_api.metaData.data.type.TypeDescription;

import java.sql.*;
import java.util.*;


public class MetaDataMapper {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    private Connection connection;
    private DatabaseMetaData metaData;
    private List<String> tableNames;
    private ConnectionFactory connectionFactory;

    private static MetaDataMapper instance;

    private MetaDataMapper(){}

    public static MetaDataMapper getInstance(){

        if (instance == null)
            synchronized (MetaDataMapper.class){
                if (instance == null)
                    instance = new MetaDataMapper();
            }
        return instance;
    }


    public MetaDataMapper init(Connection connection) throws MetaDataException {

        if (connection == null){
            connectionFactory = ConnectionFactoryFactory.getInstance().getConnectionFactory();
            try {
                this.connection = connectionFactory.takeConnectionWithoutCommit();
            } catch (ConnectionPoolException e) {
                throw new MetaDataException("Something wrong with connection");
            }
        } else {
            this.connection = connection;
        }

        try {
            metaData = this.connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet tables = metaData.getTables(null, null, null, types);
            tableNames = collectTableNames(tables);
        } catch (SQLException e) {
            throw new MetaDataException("Can't collect tables");
        }

        return this;
    }

    public void close() throws SQLException {
        if (connection != null){
            connection.close();
        }
        if (connectionFactory != null){
            connectionFactory.dispose();
        }

    }

    private  List<String> collectTableNames(ResultSet set) throws SQLException {
        List<String> names = new ArrayList<>();
        final ResultSetMetaData metaData1 = set.getMetaData();
        while (set.next()){
            names.add(set.getString(3));
        }
        return names;
    }

    public TableManager collectTables() throws MetaDataException {
        TableManager tableManager = TableManager.getInstance();
        List<Table> tables = new ArrayList<>();

        tableNames.sort(Comparator.naturalOrder());
        for (int k = 0; k < tableNames.size(); k++) {
            String tableName = tableNames.get(k);
            Table table = new Table(tableName);

            try (final ResultSet columns = metaData.getColumns(null, null, tableName, null)) {
                TreeMap<String, Column> tableColumns = new TreeMap<>();
                ResultSetMetaData metaData = columns.getMetaData();
                columns.last();
                int columnCount = columns.getRow();
                columns.beforeFirst();
                ResultSetMetaData rowSetMeta = columns.getMetaData();

                while (columns.next()) {
                    String fieldName = columns.getString(4);
                    String fieldType = columns.getString(6);
                    int dataType = columns.getInt(5);
                    int constraint = columns.getInt(7);

                    String defaultValue = columns.getString(13);
                    String isNullableInt = columns.getString(18);

                    boolean isEmail = fieldName.matches(".*[eE][mM][aA][iI][lL]");

                    TypeDescription typeDescription = SqlTypeBuilder.getInstance()
                                                                    .buildType(fieldType, dataType,
                                                                             constraint, isEmail);
                    int order = columns.getRow();
                    boolean isNullable = (!isNullableInt.equals("NO"));
                    Column column = new Column(order, fieldName, typeDescription,
                                               defaultValue, isNullable);
                    tableColumns.put(fieldName, column);

//                    for (int i = 1; i <= 24 ; i++) {
//                        System.out.print("(" + i + ")" + columns.getString(i) + " ");
//                    }
//                    System.out.println();
                }
                table.setColumns(tableColumns);
//                System.out.println(tableColumns);
            } catch (SQLException e) {
                throw new MetaDataException("Can't collect columns metadata", e);
            }
            /**
             * always id in our case
             */

            try(final ResultSet exportedKeys = metaData.getExportedKeys(null, null, tableName)) {
                Map<String, ExportedColumn> exportedColumns = new HashMap<>();
                while (exportedKeys.next()){
                    String thisTable = exportedKeys.getString(3);
                    String field = exportedKeys.getString(4);

                    String refTable = exportedKeys.getString(7);
                    String refField = exportedKeys.getString(8);

//                    for (int i = 1; i <= 14 ; i++) {
//                        System.out.print(exportedKeys.getString(i) + " ");
//                    }
                    ExportedColumn column = new ExportedColumn(refTable, refField);
                    Column protptype = tableManager.getColumnByName(table, field);
                    column.setRealColumn(protptype);
                    exportedColumns.put(refTable + "." +refField, column); // key - refTable.refFiled, so this field is identical and refFields can be identical
//                    System.out.println();
                }
                table.setExportedColumns(exportedColumns);

            } catch (SQLException e) {
                throw new MetaDataException("Can't collect exported keys metadata", e);
            }

//            System.out.println();
            try(final ResultSet exportedKeys = metaData.getImportedKeys(null, null, tableName)) {
                Map<String, ImportedColumn> importedColumns = new HashMap<>();
                while (exportedKeys.next()){
                    String thisTable = exportedKeys.getString(3);  // thisTable - that refTable
                    String field = exportedKeys.getString(4);       // thisfiedl = that refField

                    String refTable = exportedKeys.getString(7);
                    String refField = exportedKeys.getString(8);

//                    for (int i = 1; i <= 14 ; i++) {
//                        System.out.print(exportedKeys.getString(i) + " ");
//                    }
//                    System.out.println();

                    ImportedColumn column = new ImportedColumn(thisTable, field); //  ImportedColumn(String refTable, String refColumnName)
                    Column tableColumnByName = tableManager.getColumnByName(table, refField);
                    column.setRealColumn(tableColumnByName);

                    importedColumns.put(refField, column);   // key - field, so field(id in refTable) name is identical
                }
                table.setImportedColumns(importedColumns);
//                System.out.println(table);
            } catch (SQLException e) {
                throw  new MetaDataException("Can't collect imported keys metadata", e);
            }
            System.out.println("__________");
            tables.add(table);
        }

        Database.DBBuilder builder = new Database.DBBuilder();
        Database database = builder.buildDatabase(tables);
        tableManager.init(database);
        return tableManager;
    }


}
