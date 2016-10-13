package by.epam.like_it.dao.mysql.collector.type.impl;

import by.epam.like_it.dao.mysql.collector.type.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class FloatHandler extends TypeHandler<Float> {


    private static final Float example = 0f;

    private static FloatHandler instance;

     private FloatHandler(){}

     public static FloatHandler getInstance(){

         if (instance == null)
             synchronized (FloatHandler.class){
                 if (instance == null)
                     instance = new FloatHandler();
             }
         return instance;
     }
    @Override
    public void applySetToStatement(PreparedStatement statement, int pos, Object value) throws SQLException {
        statement.setFloat(pos, (Float) value);
    }

    @Override
    public Float getDataFromResultSet(ResultSet set, int pos) throws SQLException {
        return set.getFloat(pos);
    }

    @Override
    public Float getDataFromResultSet(ResultSet set, String fieldName) throws SQLException {
        return set.getFloat(fieldName);
    }

    @Override
    public Float getExample() {
        return example;
    }


    @Override
    public List<Float> castFromString(List<String> values) {
        return values.stream().map(Float::parseFloat).collect(Collectors.toList());
    }
}
