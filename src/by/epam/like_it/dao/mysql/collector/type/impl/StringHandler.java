package by.epam.like_it.dao.mysql.collector.type.impl;



import by.epam.like_it.dao.mysql.collector.type.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StringHandler extends TypeHandler<String> {

    private final static String example = "";

    private static StringHandler instance;

    private StringHandler(){}

    public static StringHandler getInstance(){

        if (instance == null)
            synchronized (StringHandler.class){
                if (instance == null)
                    instance = new StringHandler();
            }
        return instance;
    }

    @Override
    public void applySetToStatement(PreparedStatement statement, int pos, Object value) throws SQLException {
        String result = (String) value;
        statement.setString(pos, (String) value);
    }

    @Override
    public String getDataFromResultSet(ResultSet set, int pos) throws SQLException {
        return set.getString(pos);
    }

    @Override
    public String getDataFromResultSet(ResultSet set, String fieldName) throws SQLException {
        return set.getString(fieldName);
    }

    @Override
    public String getExample() {
        return example;
    }

    @Override
    public List<String> castFromString(List<String> values) {
        return values;
    }
}
