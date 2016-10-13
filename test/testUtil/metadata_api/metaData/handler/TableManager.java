
package testUtil.metadata_api.metaData.handler;



import testUtil.metadata_api.metaData.data.column.Column;
import testUtil.metadata_api.metaData.data.column.ImportedColumn;
import testUtil.metadata_api.metaData.data.Database;
import testUtil.metadata_api.metaData.data.Table;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableManager {

    private Database currentDatabase;

    private TableManager() {}

    private static TableManager instance;

    public static TableManager getInstance(){

        if (instance == null)
            synchronized (TableManager.class){
                if (instance == null)
                    instance = new TableManager();
            }
        return instance;
    }

    public void init(Database database){
        this.currentDatabase = database;
    }

    public Column getColumnByName(Table table, String name){
         if (table != null){
             return table.getColumns().get(name);
         }
         return null;
    }

    public Database getCurrentDatabase() {
        return currentDatabase;
    }

    public Table getTableByName(String name){
        return currentDatabase.getTables().get(name);
    }


    /**
     *
     * @param table
     * @param order - order by MySql (and in this representation too)
     *                so in real List uses formula order = order-1;
     * @return
     */
    public Column getColumnByOrder(Table table, int order){
        return table.getColumnsInOrder().get(order - 1);
    }

    public Column getIdColumn(Table table){
        Column column = table.getColumnsInOrder().get(0);
        if (!column.getName().equalsIgnoreCase("id")){
            return null;
        }
        return column;
    }

    public boolean hasIdColumn(Table table){
        return table.getColumnsInOrder().get(0)
                    .getName()
                    .equalsIgnoreCase("id");
    }

    public boolean isImportedColumn(Table table, Column column){
        boolean result = false;
        ImportedColumn importedColumn = table.getImportedColumns()
                                             .get(column.getName());
        if (importedColumn != null){
            result = true;
        }
        return result;
    }

    public ImportedColumn getImportedColumnByColumn(Table table, Column column){
        return table.getImportedColumns().get(column.getName());
    }

    public NavigableSet<Column> getTableColumns(Table table){
        NavigableSet<Column> set =  new TreeSet<>();
        set.addAll(table.getColumns().values());
        return set;
    }


    public List<String> getListOfColumnNames(Table table, boolean isQualified){

        final String tableName= table.getTableName();
        Stream<Column> columnStream = table.getColumnsInOrder().parallelStream();
        Stream<String> stringStream = isQualified ? columnStream.map(s -> tableName + "." + s.getName())
                                                  : columnStream.map(Column::getName);

        return stringStream.collect(Collectors.toList());
    }

    public String getStringOfColumnNames(Table table, String separator){
        return table.getColumnsInOrder().parallelStream()
                    .map(Column::getName).collect(Collectors.joining(separator));
    }
}
