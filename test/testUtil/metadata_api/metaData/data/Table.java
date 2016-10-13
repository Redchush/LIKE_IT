
package testUtil.metadata_api.metaData.data;

import org.jetbrains.annotations.NotNull;
import testUtil.metadata_api.metaData.data.column.Column;
import testUtil.metadata_api.metaData.data.column.ExportedColumn;
import testUtil.metadata_api.metaData.data.column.ImportedColumn;

import java.util.*;
import java.util.stream.Collectors;


public class Table implements Comparable<Table> {

    private static int INITIAL_ORDER_PLACE = 10000;
    private String tableName;
    private TreeMap<String, Column> columns;
    private List<Column> columnsInOrder;

    private int orderToFill;

    /**
     * key -  refTable.refFiled, so this field is identical and refFields can be identical
     */
    private Map<String, ExportedColumn> exportedColumns;
    /**
     * key - name of this column
     */
    private Map<String, ImportedColumn> importedColumns;

    public Table(String tableName) {
        this.tableName = tableName;
        exportedColumns = new HashMap<>();
        importedColumns = new HashMap<>();
        columnsInOrder = new ArrayList<>();
        columns = new TreeMap<>();
        orderToFill = INITIAL_ORDER_PLACE;

    }

    public int getOrderToFill() {
        return orderToFill;
    }

    public void setOrderToFill(int orderToFill) {
        this.orderToFill = orderToFill;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public TreeMap<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(TreeMap<String, Column> columns){
         this.columns = columns;
         columnsInOrder.addAll(columns.values());
         columnsInOrder.sort(Comparator.naturalOrder());
    }

    public int getSize(){
        return columns.size();
    }

    @Override
    public int compareTo(@NotNull Table o) {
        int result = this.getOrderToFill() - o.orderToFill;
        if (result == 0){
            result = this.getTableName().compareTo(o.getTableName());
        }
        return result;
    }
    /**
     * key - refTable.refFiled (table and column in other table).
     * So real column, wrapped as exported, is typically is id or other primary key
     */
    public Map<String, ExportedColumn> getExportedColumns() {
        return exportedColumns;
    }
    /**
     * key - refTable.refFiled (table and column in other table), so this field is identical and refFields can be
     * identical
     * So real column, wrapped as exported, is typically is id or other primary key
     */
    public void setExportedColumns(Map<String, ExportedColumn> exportedColumns) {
        this.exportedColumns = exportedColumns;
    }
    /**
     * key - name of this column
     */
    public Map<String, ImportedColumn> getImportedColumns() {
        return importedColumns;
    }

    public void setImportedColumns(
            Map<String, ImportedColumn> importedColumns) {
        this.importedColumns = importedColumns;
    }

    public List<Column> getColumnsInOrder() {
        return columnsInOrder;
    }

    public void setColumnsInOrder(List<Column> columnsInOrder) {
        this.columnsInOrder = columnsInOrder;
    }

    public Column getColumnByOrder(int order){
        return columnsInOrder.get(order-1);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Table{");
columns.entrySet();
        sb.append("tableName='").append(tableName).append('\n');
        sb.append(", orderToFill=").append(orderToFill).append('\n');
        sb.append(", columns=").append(columns.entrySet().stream()
                                              .map(s->s.getKey() + "=" + s.getValue())
                                              .collect(Collectors.joining("\n","\n","\n")));

        sb.append(", exportedColumns=").append(exportedColumns.entrySet().stream()
                                                              .map(s->s.getKey() + "=" + s.getValue())
                                                              .collect(Collectors.joining("\n","\n","")))
                                       .append("\n");
        sb.append(", importedColumns=").append(importedColumns.entrySet().stream()
                                                              .map(s->s.getKey() + "=" + s.getValue())
                                                              .collect(Collectors.joining("\n","\n","")));
        sb.append('}');


        return sb.toString();
    }




}
