package testUtil.metadata_api.metaData.handler.type.impl;


import testUtil.metadata_api.metaData.handler.type.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class LongHandler extends TypeHandler<Long> {

    private final static Long example = 0L;

    private static LongHandler instance;

    private LongHandler(){}

    public static LongHandler getInstance(){

        if (instance == null)
            synchronized (LongHandler.class){
                if (instance == null)
                    instance = new LongHandler();
            }
        return instance;
    }
    @Override
    public void applySetToStatement(PreparedStatement statement, int pos, Object value) throws SQLException {
        statement.setLong(pos, (Long) value);
    }

    @Override
    public Long getDataFromResultSet(ResultSet set, int pos) throws SQLException {
        return set.getLong(pos);
    }

    @Override
    public Long getDataFromResultSet(ResultSet set, String fieldName) throws SQLException {
        return set.getLong(fieldName);
    }

    @Override
    public Long getExample() {
        return example;
    }

    @Override
    public List<Long> castFromString(List<String> values) {
        return values.stream().map(Long::parseLong).collect(Collectors.toList());
    }
}
