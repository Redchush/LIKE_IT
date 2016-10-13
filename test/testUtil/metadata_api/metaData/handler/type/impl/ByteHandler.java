package testUtil.metadata_api.metaData.handler.type.impl;


import testUtil.metadata_api.metaData.handler.type.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ByteHandler extends TypeHandler<Byte> {

    public static final Byte example = (byte) 0;
    private static ByteHandler instance;

    private ByteHandler(){}

    public static ByteHandler getInstance(){

        if (instance == null)
            synchronized (ByteHandler.class){
                if (instance == null)
                    instance = new ByteHandler();
            }
        return instance;
    }


    @Override
    public void applySetToStatement(PreparedStatement statement, int pos, Object value) throws SQLException {
        statement.setByte(pos, (Byte) value);
    }

    @Override
    public Byte getDataFromResultSet(ResultSet set, int pos) throws SQLException {
        return set.getByte(pos);
    }

    @Override
    public Byte getDataFromResultSet(ResultSet set, String fieldName) throws SQLException {
        return set.getByte(fieldName);
    }

    @Override
    public Byte getExample() {
        return example;
    }

    @Override
    public List<Byte> castFromString(List<String> values) {
        return values.stream().map(Byte::parseByte).collect(Collectors.toList());
    }
}
