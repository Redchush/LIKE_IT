package testUtil.metadata_api.randomizer;


import testUtil.metadata_api.metaData.data.type.Patterns;
import testUtil.metadata_api.metaData.data.type.TypeDescription;
import testUtil.metadata_api.randomizer.exception.StringRandomizeInitException;

import java.sql.Timestamp;

public class RandomHandler {

    private static RandomHandler instance;

    private RandomHandler(){}

    public static RandomHandler getInstance(){

        if (instance == null)
            synchronized (RandomHandler.class){
                if (instance == null)
                    instance = new RandomHandler();
            }
        return instance;
    }

    public Object getRandomByType(Double min, Double max, String simpleTypeName){
        switch (simpleTypeName){
            case "String" :
                return  getStringRandom(min, max, false, false);
            case "Byte":
                return  getByteRandom(min, max);
            case "Short" :
                return  getSortRandom(min, max);
            case "Integer" :
                return  getIntegerRandom(min, max);
            case "Long" :
                return  getLongRandom(min, max);
            case "Float" :
                return  getFloatRandom(min, max);
            case "Boolean":
                return  getBooleanRandom(min, max);
            default:
                return null;
        }
    }

    public Object getRandomByType(Double min, Double max, TypeDescription description){
        Double realMax = description.getMaxValue();
        Double realMin = description.getMinValue();
        if (max != null){
            realMax = max;
        }
        if (min != null){
            realMin = min;
        }
        Object example = description.getExample();
        String simpleName = example.getClass().getSimpleName();
        switch (simpleName){
            case "String" :
                return  getStringRandom(realMin, realMax, (TypeDescription<String>) description);
            default:
                return getRandomByType(realMin, realMax, simpleName);
        }
    }

    public String getStringRandom(Double min, Double max, TypeDescription<String> description){
        boolean isEmail = description.getPattern().equals(Patterns.EMAIL);
        boolean isEnglish = description.getPattern().equals(Patterns.ENGLISH_WITH_SIGNS);
        return Random.getStringRandom(min, max, isEmail, isEnglish);
    }

    public String getStringRandom(Double min, Double max, boolean isEmail, boolean isEnglish){
        return Random.getStringRandom(min, max, isEmail, isEnglish);
    }

    public Byte getByteRandom(double min, double max) {
        return (byte) Random.getRealRandom(min, max);
    }

    public Short getSortRandom(double min, double max) {
        return (short) Random.getRealRandom(min, max);
    }

    public Float getFloatRandom(double min, double max) {
        return (float) Random.getFloatRandom(min, max);
    }

    public Integer getIntegerRandom(double min, double max) {
        return (int) Random.getRealRandom(min, max);
    }

    public Long getLongRandom(double min, double max) {
        return Random.getRealRandom(min, max);
    }

    public Timestamp getTimestampRandom(double min, double max) {
        return Random.getTimeStampRandom(min, max);
    }

    public Boolean getBooleanRandom(double min, double max) {
        return new java.util.Random().nextBoolean();
    }

    public void initDefaultStringRandomizer() throws StringRandomizeInitException {
        Random.initDefaultSringRandomiser();
    }


}
