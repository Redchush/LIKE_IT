package by.epam.like_it.common_util;


import java.sql.Timestamp;
import java.util.Date;

public class TimeUtil {

    public Timestamp getCurrentTimestamp(){
        return TimeUtil.getCurrentTimestampSt();
    }

    public static Timestamp getCurrentTimestampSt() {
        long time = new Date().getTime();
        Timestamp timestamp = new Timestamp(time);
        timestamp.setNanos(0);
        return timestamp;
    }
}
