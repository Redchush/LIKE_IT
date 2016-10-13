package testUtil.metadata_api.metaData.data.column;

import org.jetbrains.annotations.NotNull;
import testUtil.metadata_api.metaData.data.type.TypeDescription;



public class Column implements IColumn, Comparable<Column>{

    private int order;
    private String name;
    private TypeDescription type;
    private String defaultValue;
    private boolean isNullable;

    public Column() {}

    public Column(int order, String name,
                  TypeDescription type, String defaultValue, boolean isNullable) {

        this.order = order;
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
        this.isNullable = isNullable;
    }



    @Override
    public int compareTo(@NotNull Column o) {
        int result = this.getOrder() - o.getOrder();
        if (result == 0){
            result = this.hashCode() - o.hashCode();
        }
        return result;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }


    public TypeDescription getType() {
        return type;
    }


    public void setType(TypeDescription type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean isNullable() {
        return isNullable;
    }

    @Override
    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Column{");

        sb.append("order=").append(order);
        sb.append(", name='").append(name).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Column column = (Column) o;

        if (order != column.order) {
            return false;
        }
        if (isNullable != column.isNullable) {
            return false;
        }
        if (name != null ? !name.equals(column.name) : column.name != null) {
            return false;
        }
        if (type != null ? !type.equals(column.type) : column.type != null) {
            return false;
        }
        return defaultValue != null ? defaultValue.equals(column.defaultValue) : column.defaultValue == null;

    }

    @Override
    public int hashCode() {
        int result = order;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
        result = 31 * result + (isNullable ? 1 : 0);
        return result;
    }
}
