package testUtil.metadata_api.metaData.data.type.impl;


import testUtil.metadata_api.metaData.data.type.TypeDescription;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ShortDescription extends TypeDescription<Short> {

    public final static Short example = (short) 0;

    @Override
    public Short getExample() {
        return example;
    }


}
