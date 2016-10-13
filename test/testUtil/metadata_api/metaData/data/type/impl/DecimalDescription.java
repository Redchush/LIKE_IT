package testUtil.metadata_api.metaData.data.type.impl;



import testUtil.metadata_api.metaData.data.type.TypeDescription;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DecimalDescription extends TypeDescription<BigDecimal> {

    public final static BigDecimal decimal = new BigDecimal(0);

    @Override
    public BigDecimal getExample() {
        return decimal;
    }

}
