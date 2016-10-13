package testUtil.metadata_api.metaData.data.type.impl;


import testUtil.metadata_api.metaData.data.type.TypeDescription;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;


public class BooleanDescription extends TypeDescription<Boolean> {

    private final Class<Boolean> javaType = Boolean.class;
    public final static Boolean example = false;

    public BooleanDescription() {
        super.minValue = 0;
        super.maxValue = 1;
    }

    @Override
    public Boolean getExample() {
        return example;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BooleanHandler{");
        sb.append(super.toString()).append(" ");

        sb.append("javaType=").append(javaType);
        sb.append(", example=").append(example);
        sb.append('}');
        return sb.toString();
    }
}
