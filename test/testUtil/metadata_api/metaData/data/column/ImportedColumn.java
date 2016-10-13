package testUtil.metadata_api.metaData.data.column;

import testUtil.metadata_api.metaData.data.type.TypeDescription;

/**
 * example:
 * users language_id - languages id
 * one user has only one language
 * If table columns size equals number of imported keys,
 * that means, than this is relational table, which only represent many-vo-many link
 */

public class ImportedColumn implements IColumn, RelatedColumn {

    private String refTable;
    private String refColumnName;

    private Column realColumn;
    private Column refColumn;

    public ImportedColumn(String refTable, String refColumnName) {
        this.refTable = refTable;
        this.refColumnName = refColumnName;
    }

    public Column getRealColumn() {
        return realColumn;
    }

    public void setRealColumn(Column realColumn) {
        this.realColumn = realColumn;
    }

    @Override
    public Column getRefColumn() {
        return refColumn;
    }

    @Override
    public void setRefColumn(Column refColumn) {
        this.refColumn = refColumn;
    }

    @Override
    public String getRefTable() {
        return refTable;
    }
    @Override
    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }
    @Override
    public String getRefColumnName() {
        return refColumnName;
    }

    @Override
    public void setRefColumnName(String refColumnName) {
        this.refColumnName = refColumnName;
    }


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
    public void setDefaultValue(String defaultValue) {
        realColumn.setDefaultValue(defaultValue);
    }

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
        final StringBuilder sb = new StringBuilder("ImportedColumn{");

        sb.append("refTable='").append(refTable).append('\'');
        sb.append(", refColumnName='").append(refColumnName).append('\'');
        sb.append(", realColumn=").append(realColumn);
        sb.append(", refColumn=").append(refColumn);
        sb.append('}');
        return sb.toString();
    }
}
