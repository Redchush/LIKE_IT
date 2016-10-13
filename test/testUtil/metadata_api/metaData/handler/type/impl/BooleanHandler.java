package testUtil.metadata_api.metaData.handler.type.impl;


import testUtil.metadata_api.metaData.handler.type.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class BooleanHandler extends TypeHandler<Boolean> {

    public static final Boolean example = false;

    private static BooleanHandler instance;

    private BooleanHandler(){}

    public static BooleanHandler getInstance(){

        if (instance == null)
            synchronized (BooleanHandler.class){
                if (instance == null)
                    instance = new BooleanHandler();
            }
        return instance;
    }

    @Override
    public void applySetToStatement(PreparedStatement statement, int pos, Object value) throws SQLException {
        statement.setBoolean(pos, (Boolean) value);
    }

    @Override
    public Boolean getDataFromResultSet(ResultSet set, int pos) throws SQLException {
        return set.getBoolean(pos);
    }

    @Override
    public Boolean getDataFromResultSet(ResultSet set, String fieldName) throws SQLException {
        return set.getBoolean(fieldName);
    }

    @Override
    public Boolean getExample() {
        return example;
    }

    @Override
    public List<Boolean> castFromString(List<String> values) {
       return values.stream().map(Boolean::parseBoolean).collect(Collectors.toList());
    }


}
