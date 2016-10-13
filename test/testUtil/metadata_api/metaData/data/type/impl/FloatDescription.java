package testUtil.metadata_api.metaData.data.type.impl;



import testUtil.metadata_api.randomizer.Random;
import testUtil.metadata_api.metaData.data.type.TypeDescription;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class FloatDescription extends TypeDescription<Float> {
    public static final Float example =  0f;

    @Override
    public Float getExample() {
        super.maxValue = Float.MIN_VALUE;
        super.maxValue = Float.MAX_VALUE;
        return example;
    }

}
