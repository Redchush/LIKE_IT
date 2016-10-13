package testUtil.metadata_api.metaData.data.type;


import testUtil.metadata_api.metaData.data.type.impl.*;

public class SqlTypeBuilder {

    private static SqlTypeBuilder instance;

    private SqlTypeBuilder(){}

    public static SqlTypeBuilder getInstance(){

        if (instance == null)
            synchronized (SqlTypeBuilder.class){
                if (instance == null)
                    instance = new SqlTypeBuilder();
            }
        return instance;
    }
    public <T> TypeDescription<?> buildType(String mySqlType,
                                            int dataType,
                                            int constraint,
                                            boolean isEmail){
        String type = mySqlType.toUpperCase();
        type = type.replace("UNSIGNED", "");
        boolean isUnsigned = mySqlType.contains("UNSIGNED");
        while (type.contains(" ")){
            type = type.replace(" ", "");
        }
        TypeDescription<?> wrapper = null;
        switch (type) {
            case "CHAR":
            case "VARCHAR":
            case "LONGVARCHAR":
            case "TEXT":
//                int finalConstaint = 100;
//                if (constraint > 200){
//                    constraint = finalConstaint;
//                }
                wrapper = new StringDescription();
                wrapper.setMinValue(1);
                wrapper.setMaxValue(constraint);
                break;
            case "NUMERIC":
            case "DECIMAL":
                wrapper = new DecimalDescription();
            case "BIT":
                wrapper = new BooleanDescription();
                break;
            case "TINYINT":
                wrapper = new ByteDescription();
                wrapper.setMinValue(isUnsigned ? 1 : Byte.MIN_VALUE);
                break;
            case "SMALLINT":
                wrapper = new ShortDescription();
                wrapper.setMinValue(isUnsigned ? 1 : Short.MIN_VALUE);
                break;
            case "INTEGER":
            case "INT":
                wrapper = new IntergerDescription();
                wrapper.setMinValue(isUnsigned ? 1 : Integer.MIN_VALUE);
                break;
            case "BIGINT":
                wrapper = new LongDescription();
                wrapper.setMinValue(isUnsigned ? 1 : Long.MIN_VALUE);
                break;
            case "REAL":
            case "FLOAT":
               /* todo*/
//                setMinMax(wrapper, isUnsigned ? 1 : Float.MIN_VALUE, Float.MAX_VALUE);
                break;
            case "DOUBLE":
                wrapper = new DoubleDescription();
                wrapper.setMinValue(isUnsigned ? 1 : Double.MIN_VALUE);
                break;
            case "BINARY":
            case "VARBINARY":
            case "LONGVARBINARY":
//                todo
//                wrapper.setJavaType(byte[].class);
                break;
            case "DATE":
//                 todo
//                wrapper.setJavaType(java.sql.Date.class);
//                setMinMax(wrapper, offset, end);
                break;
            case "TIME":
//                todo
                break;
            case "TIMESTAMP":
                wrapper = new TimeStampDescription();
                break;
            default:
                return new UnknownType();
        }

        assert wrapper != null;
        wrapper.setSqlDataType(dataType);
        wrapper.setSqlType(mySqlType);
        return wrapper;
    }

}
