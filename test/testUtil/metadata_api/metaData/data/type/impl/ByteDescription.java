package testUtil.metadata_api.metaData.data.type.impl;


import testUtil.metadata_api.randomizer.Random;
import testUtil.metadata_api.metaData.data.type.TypeDescription;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ByteDescription extends TypeDescription<Byte> {

    public final static Byte example = (byte) 0;

    public ByteDescription() {
        super.minValue = Byte.MIN_VALUE;
        super.maxValue = Byte.MAX_VALUE;
    }

    @Override
    public Byte getExample() {
        return example;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ByteHandler{");
        sb.append(super.toString()).append(" ");

        sb.append("example=").append(example);
        sb.append('}');
        return sb.toString();
    }
}
