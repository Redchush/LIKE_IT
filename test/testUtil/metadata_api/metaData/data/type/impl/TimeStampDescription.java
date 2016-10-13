package testUtil.metadata_api.metaData.data.type.impl;


import testUtil.metadata_api.metaData.data.type.TypeDescription;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimeStampDescription extends TypeDescription<Timestamp> {

    public final static Timestamp example = Timestamp.valueOf(LocalDateTime.now());

    public TimeStampDescription() {
        super.minValue = Timestamp.valueOf(LocalDateTime.now().minusYears(10)).getTime();
        super.maxValue = Timestamp.valueOf(LocalDateTime.now()).getTime();
    }

    @Override
    public Timestamp getExample() {
        return example;
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeStampHandler{");
        sb.append(super.toString()).append(" ");

        sb.append("example=").append(example);
        sb.append('}');
        return sb.toString();
    }
}
