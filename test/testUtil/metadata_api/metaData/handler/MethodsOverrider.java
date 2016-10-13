package testUtil.metadata_api.metaData.handler;


import java.sql.*;

public class MethodsOverrider {
    private static MethodsOverrider instance;

    private MethodsOverrider(){}

    public static MethodsOverrider getInstance(){

        if (instance == null)
            synchronized (MethodsOverrider.class){
                if (instance == null)
                    instance = new MethodsOverrider();
            }
        return instance;
    }

    public void applyStatement(PreparedStatement statement, int pos, Boolean value) throws SQLException {
        statement.setBoolean(pos, value);
    }

    public void applyStatement(PreparedStatement statement, int pos, String value) throws SQLException {
        statement.setString(pos, value);
    }

    public void applyStatement(PreparedStatement statement, int pos, Byte value) throws SQLException {
        statement.setByte(pos, value);
    }
    public void applyStatement(PreparedStatement statement, int pos, Short value) throws SQLException {
        statement.setShort(pos, value);
    }
    public void applyStatement(PreparedStatement statement, int pos, Integer value) throws SQLException {
        statement.setInt(pos, value);
    }
    public void applyStatement(PreparedStatement statement, int pos, Long value) throws SQLException {
        statement.setLong(pos, value);
    }
    public void applyStatement(PreparedStatement statement, int pos, Float value) throws SQLException {
        statement.setFloat(pos, value);
    }
    public void applyStatement(PreparedStatement statement, int pos, Double value) throws SQLException {
        statement.setDouble(pos, value);
    }
    public void applyStatement(PreparedStatement statement, int pos, Timestamp value) throws SQLException {
        statement.setTimestamp(pos, value);
    }
    public void applyStatement(PreparedStatement statement, int pos, Time value) throws SQLException {
        statement.setTime(pos, value);
    }
    public void applyStatement(PreparedStatement statement, int pos, Date value) throws SQLException {
        statement.setDate(pos, value);
    }
    public<T> void applyStatement(PreparedStatement statement, int pos, T value) throws SQLException {
       throw new UnsupportedOperationException("This type isn't managed " + value.getClass() + " " + value );
    }

    public String applySet(ResultSet set, int pos, String example) throws SQLException {
        return set.getString(pos);
    }
    public Boolean applySet(ResultSet set, int pos, Boolean example) throws SQLException {
        return set.getBoolean(pos);
    }
    public Byte applySet(ResultSet set, int pos, Byte example) throws SQLException {
        return set.getByte(pos);
    }
    public Short applySet(ResultSet set, int pos, Short example) throws SQLException {
        return set.getShort(pos);
    }
    public Integer applySet(ResultSet set, int pos, Integer example) throws SQLException {
        return set.getInt(pos);
    }
    public Long applySet(ResultSet set, int pos, Long example) throws SQLException {
        return set.getLong(pos);
    }
    public Double applySet(ResultSet set, int pos, Double example) throws SQLException {
        return set.getDouble(pos);
    }
    public Float applySet(ResultSet set, int pos, Float example) throws SQLException {
        return set.getFloat(pos);
    }

    public Time applySet(ResultSet set, int pos, Time example) throws SQLException {
        return set.getTime(pos);
    }
    public Timestamp applySet(ResultSet set, int pos, Timestamp example) throws SQLException {
        return set.getTimestamp(pos);
    }
    public Date applySet(ResultSet set, int pos, Date example) throws SQLException {
        return set.getDate(pos);
    }

    public <T> T applySet(ResultSet set, int pos, T example) throws SQLException {
        throw new UnsupportedOperationException("This type isn't managed");
    }

}
