package testUtil.metadata_api.metaData.data.type.impl;


import testUtil.metadata_api.metaData.data.type.Patterns;
import testUtil.metadata_api.randomizer.Random;
import testUtil.metadata_api.metaData.data.type.TypeDescription;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class StringDescription extends TypeDescription<String> {

    public final static String example = "";

    public StringDescription() {
        super.minValue = 0;
        pattern = "";
    }

    @Override
    public String getExample() {
        return example;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StringHandler{");
        sb.append(super.toString()).append(" ");
        sb.append("example='").append(example).append('\'');
        sb.append(", pattern='").append(pattern).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
