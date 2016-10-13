package testUtil.metadata_api.metaData.data.type.impl;


import testUtil.metadata_api.metaData.data.type.TypeDescription;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoubleDescription extends TypeDescription<Double> {

    public final static Double example = 0d;

    @Override
    public Double getExample() {
        return example;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DoubleHandler{");
        sb.append(super.toString()).append(" ");

        sb.append('}');
        return sb.toString();
    }
}
