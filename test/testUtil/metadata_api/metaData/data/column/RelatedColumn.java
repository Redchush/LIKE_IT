package testUtil.metadata_api.metaData.data.column;

/**
 * Facade for both imported and exported columns
 */

public interface RelatedColumn {

    String getRefTable();

    void setRefTable(String refTable);

    String getRefColumnName();

    void setRefColumnName(String refColumnName);

    Column getRefColumn();

    void setRefColumn(Column refColumn);
}
