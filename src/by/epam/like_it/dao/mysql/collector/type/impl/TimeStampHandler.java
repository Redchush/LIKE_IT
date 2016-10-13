package by.epam.like_it.dao.mysql.collector.type.impl;





import by.epam.like_it.dao.mysql.collector.type.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class TimeStampHandler extends TypeHandler<Timestamp> {

    public final static Timestamp example = Timestamp.valueOf(LocalDateTime.now());

    private static TimeStampHandler instance;

    private TimeStampHandler(){}

    public static TimeStampHandler getInstance(){

        if (instance == null)
            synchronized (TimeStampHandler.class){
                if (instance == null)
                    instance = new TimeStampHandler();
            }
        return instance;
    }

    @Override
    public void applySetToStatement(PreparedStatement statement, int pos, Object value) throws SQLException {
        statement.setTimestamp(pos, (Timestamp) value);
    }

    @Override
    public Timestamp getDataFromResultSet(ResultSet set, int pos) throws SQLException {
        return set.getTimestamp(pos);
    }

    @Override
    public Timestamp getDataFromResultSet(ResultSet set, String fieldName) throws SQLException {
        return set.getTimestamp(fieldName);
    }

    @Override
    public Timestamp getExample() {
        return example;
    }

    @Override
    public List<Timestamp> castFromString(List<String> values) {
        return null;
    }


}
