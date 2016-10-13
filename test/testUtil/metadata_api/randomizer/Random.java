package testUtil.metadata_api.randomizer;

import testUtil.metadata_api.randomizer.exception.StringRandomizeInitException;

import java.sql.Time;
import java.sql.Timestamp;


public class Random {

    public static double getFloatRandom(double min, double max) {
        return min + (Math.random() * ((max - min) + 1));
    }

    public static long getRealRandom(double min, double max) {
        return (long) (min + (long)(Math.random() * ((max - min) + 1)));
    }

    public static long getDateRandom(double min, double max) {
        long diff = (long) (max - min + 1);
        long random = (long) (min + (Math.random() * diff));
        return random;
    }

    public static Timestamp getTimeStampRandom(double min, double max){
        return new Timestamp(getDateRandom(min, max));
    }

    public static Time getTimeRandom(double min, double max){
        return new Time(getDateRandom(min, max));
    }

   public static String getStringRandom(double min, double max, boolean isEmail, boolean isEnglish){
       StringRandom instance = StringRandom.getInstance();

       if (isEmail){
           return instance.getRandomEmail((int) max);
       }
       if (isEnglish){
           return instance.getEnglishRandom((int) max);
       }
       return  instance.getRussianRandom((int) max) ;
   }

   public static void initDefaultSringRandomiser() throws StringRandomizeInitException {
       StringRandom instance = StringRandom.getInstance();
       if (!instance.isInit()){
           instance.init(null, null, null);
       }
   }
}





