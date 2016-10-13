package testUtil.metadata_api.metaData.data.type.impl;


import testUtil.metadata_api.randomizer.Random;
import testUtil.metadata_api.metaData.data.type.TypeDescription;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class IntergerDescription extends TypeDescription<Integer> {

    public final static Integer example = 0;

    public IntergerDescription() {
        super.minValue = Integer.MIN_VALUE;
        super.maxValue = Integer.MAX_VALUE;
    }

    @Override
    public Integer getExample() {
        return example;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IntegerHandler{");
        sb.append(super.toString()).append(" ");

        sb.append("example=").append(example);
        sb.append('}');
        return sb.toString();
    }
}
