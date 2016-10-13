package testUtil.metadata_api.metaData.handler.type.impl;


import testUtil.metadata_api.metaData.handler.type.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ShortHandler extends TypeHandler<Short> {

    public final static Short example = (short) 0;

    private static ShortHandler instance;

    private ShortHandler(){}

    public static ShortHandler getInstance(){

        if (instance == null)
            synchronized (ShortHandler.class){
                if (instance == null)
                    instance = new ShortHandler();
            }
        return instance;
    }

    @Override
    public void applySetToStatement(PreparedStatement statement, int pos, Object value) throws SQLException {
        statement.setShort(0, (Short) value);
    }

    @Override
    public Short getDataFromResultSet(ResultSet set, int pos) throws SQLException {
        return set.getShort(pos);
    }

    @Override
    public Short getDataFromResultSet(ResultSet set, String fieldName) throws SQLException {
        return set.getShort(fieldName);
    }

    @Override
    public Short getExample() {
        return example;
    }

    @Override
    public List<Short> castFromString(List<String> values) {
        return values.stream().map(Short::parseShort).collect(Collectors.toList());
    }
}
