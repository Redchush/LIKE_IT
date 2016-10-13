package by.epam.like_it.model.bean.util_interface;


import by.epam.like_it.common_util.TimeUtil;

import java.sql.Timestamp;

/**
 * Interface that marked entity as time-dependent, which means what entity can exist only in time environment and it
 * lifecycle depends on time created and time updated, expressed in Timestamp units
 */
public interface TimeDependent {

    Timestamp getCreatedDate();

    void setCreatedDate(Timestamp createdDate);

    Timestamp getUpdatedDate();

    void setUpdatedDate(Timestamp updatedDate);

    default Timestamp getDefaultCreatedDate(){
        return TimeUtil.getCurrentTimestampSt();
    }

    default Timestamp getDefaultUpdatedDate(){
        return getCreatedDate();
    }


}
