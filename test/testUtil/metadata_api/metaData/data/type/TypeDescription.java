package testUtil.metadata_api.metaData.data.type;

public abstract class TypeDescription<T> implements Cloneable {

    protected String sqlType;
    protected int sqlDataType;

    protected double minValue;
    protected double maxValue;
    protected String pattern;

    public TypeDescription() {
        sqlType = "";
        minValue= Double.NaN;
        maxValue = Double.NaN;
    }

    public abstract T getExample();

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public int getSqlDataType() {
        return sqlDataType;
    }
    public void setSqlDataType(int sqlDataType) {
        this.sqlDataType = sqlDataType;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    protected TypeDescription<T> clone() throws CloneNotSupportedException {
        return (TypeDescription<T>) super.clone();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TypeHandler{");

        sb.append("sqlType='").append(sqlType).append('\'');
        sb.append(", sqlDataType=").append(sqlDataType);
        sb.append(", minValue=").append(minValue);
        sb.append(", maxValue=").append(maxValue);
        sb.append('}');
        return sb.toString();
    }
}
