package testUtil.metadata_api.metaData.handler.type.impl;


import testUtil.metadata_api.metaData.handler.type.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DoubleHandler extends TypeHandler<Double> {

    public final static Double example = 0d;

    private static DoubleHandler instance;

        private DoubleHandler(){}

        public static DoubleHandler getInstance(){

            if (instance == null)
                synchronized (DoubleHandler.class){
                    if (instance == null)
                        instance = new DoubleHandler();
                }
            return instance;
        }

    @Override
    public void applySetToStatement(PreparedStatement statement, int pos, Object value) throws SQLException {
        statement.setDouble(pos, (Double) value);
    }

    @Override
    public Double getDataFromResultSet(ResultSet set, int pos) throws SQLException {
        return set.getDouble(pos);
    }

    @Override
    public Double getDataFromResultSet(ResultSet set, String fieldName) throws SQLException {
        return set.getDouble(fieldName);
    }

    @Override
    public Double getExample() {
        return example;
    }

    @Override
    public List<Double> castFromString(List<String> values) {
        return values.stream().map(Double::parseDouble).collect(Collectors.toList());
    }


}
