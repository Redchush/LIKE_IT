package testUtil.metadata_api.metaData.data.type.impl;


import testUtil.metadata_api.randomizer.Random;
import testUtil.metadata_api.metaData.data.type.TypeDescription;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LongDescription extends TypeDescription<Long> {

    public final static Long example = 0L;

    public LongDescription() {
        super.maxValue = Long.MAX_VALUE;
        super.minValue = Long.MIN_VALUE;
    }

    @Override
    public Long getExample() {
        return example;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LongHandler{");
        sb.append(super.toString()).append(" ");

        sb.append("example=").append(example);
        sb.append('}');
        return sb.toString();
    }
}
