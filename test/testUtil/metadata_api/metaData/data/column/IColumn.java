package testUtil.metadata_api.metaData.data.column;

import testUtil.metadata_api.metaData.data.type.TypeDescription;

/**
 * Interface of all columns
 */
public interface IColumn {

    int getOrder();

    void setOrder(int order);

    TypeDescription getType();

    void setType(TypeDescription type);

    String getName();

    void setName(String name);

    String getDefaultValue();

    void setDefaultValue(String defaultValue);

    boolean isNullable();

    void setNullable(boolean nullable);
}
