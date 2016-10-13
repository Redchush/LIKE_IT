package testUtil.metadata_api.metaData.data.column;

import testUtil.metadata_api.metaData.data.type.TypeDescription;

/** example :
 * users id -- answers user_id
 * one user has many answers
 * default column in current table, that can be exported, is an id column
 */
public class ExportedColumn implements IColumn, RelatedColumn {

    private String refTable;
    private String refColumnName;

    private Column realColumn;
    /**
     * realColumn in other table
     */
    private Column refColumn;

    public ExportedColumn() {}

    public ExportedColumn(String refTable, String refColumnName) {
        this.refTable = refTable;
        this.refColumnName = refColumnName;
    }

    /**
     *
     * @return name of correlated table
     */
    @Override
    public String getRefTable() {
        return refTable;
    }

    @Override
    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    /**
     *
     * @return name of correlated column in other (related) table
     */
    @Override
    public String getRefColumnName() {
        return refColumnName;
    }

    @Override
    public void setRefColumnName(String refColumnName) {
        this.refColumnName = refColumnName;
    }

    /**
     * get realColumn from other table
     */
    @Override
    public Column getRefColumn() {
        return refColumn;
    }

    @Override
    public void setRefColumn(Column refColumn) {
        this.refColumn = refColumn;
    }

    /**
     * get wrapped column of this table
     */
    public Column getRealColumn() {
        return realColumn;
    }

    public void setRealColumn(Column realColumn) {
        this.realColumn = realColumn;
    }

    /**
     *
     * @return order in current table
     */
    @Override
    public int getOrder() {
        return realColumn.getOrder();
    }

    @Override
    public void setOrder(int order) {
        realColumn.setOrder(order);
    }


    @Override
    public TypeDescription getType() {
        return realColumn.getType();
    }

    @Override
    public void setType(TypeDescription type) {
        realColumn.setType(type);
    }

    @Override
    public String getName() {
        return realColumn.getName();
    }

    @Override
    public void setName(String name) {
        realColumn.setName(name);
    }

    @Override
    public String getDefaultValue() {
        return null;
    }

    @Override
    public void setDefaultValue(String defaultValue) {}

    /**
     *
     * @return if this column allow null value
     */
    @Override
    public boolean isNullable() {
        return realColumn.isNullable();
    }

    @Override
    public void setNullable(boolean nullable) {
        realColumn.setNullable(nullable);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExportedColumn{");
        sb.append("refTable='").append(refTable).append('\'');
        sb.append(", refColumnName='").append(refColumnName).append('\'');
        sb.append(", realColumn=").append(realColumn);
        sb.append(", refColumn=").append(refColumn);
        sb.append('}');
        return sb.toString();
    }
}
