package testUtil.metadata_api.metaData.handler.type.impl;

import testUtil.metadata_api.metaData.handler.type.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class IntegerHandler extends TypeHandler<Integer> {

    private final static Integer example = 0;
    private static IntegerHandler instance;

    private IntegerHandler(){}

    public static IntegerHandler getInstance(){

        if (instance == null)
            synchronized (IntegerHandler.class){
                if (instance == null)
                    instance = new IntegerHandler();
            }
        return instance;
    }

    @Override
    public void applySetToStatement(PreparedStatement statement, int pos, Object value) throws SQLException {
        statement.setInt(pos, (Integer) value);
    }

    @Override
    public Integer getDataFromResultSet(ResultSet set, int pos) throws SQLException {
        return set.getInt(pos);
    }

    @Override
    public Integer getDataFromResultSet(ResultSet set, String fieldName) throws SQLException {
        return set.getInt(fieldName);
    }

    @Override
    public Integer getExample() {
        return example;
    }

    @Override
    public List<Integer> castFromString(List<String> values) {
        return values.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
