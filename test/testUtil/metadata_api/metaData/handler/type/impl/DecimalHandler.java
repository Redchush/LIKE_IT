package testUtil.metadata_api.metaData.handler.type.impl;

import testUtil.metadata_api.metaData.handler.type.TypeHandler;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class DecimalHandler extends TypeHandler<BigDecimal> {

    public final static BigDecimal example = new BigDecimal(0);

    private static DecimalHandler instance;

    private DecimalHandler(){}

    public static DecimalHandler getInstance(){

        if (instance == null)
            synchronized (DecimalHandler.class){
                if (instance == null)
                    instance = new DecimalHandler();
            }
        return instance;
    }

    @Override
    public void applySetToStatement(PreparedStatement statement, int pos, Object value) throws SQLException {
        statement.setBigDecimal(pos, (BigDecimal) value);
    }

    @Override
    public BigDecimal getDataFromResultSet(ResultSet set, int pos) throws SQLException {
        return set.getBigDecimal(pos);
    }

    @Override
    public BigDecimal getDataFromResultSet(ResultSet set, String fieldName) throws SQLException {
        return set.getBigDecimal(fieldName);
    }

    @Override
    public BigDecimal getExample() {
        return example;
    }

    @Override
    public List<BigDecimal> castFromString(List<String> values) {
        return null;
    }
}
