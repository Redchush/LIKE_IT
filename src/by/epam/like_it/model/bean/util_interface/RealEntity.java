package by.epam.like_it.model.bean.util_interface;


/**
 * Marked that this bean has default values for some fields and without some of this bean can't be
 * saved in persistence storage
 */

public interface RealEntity extends Entity {

    /**
     * Assign values criteria_to fields, that can't be null in persistence model and in current bean evaluates as null.
     */
    void initRequiredDefault();

    /**
     * Assign values criteria_to all fields that has default values according both  object and persistence model
     */
    void initAllDefault();

    Number getId();
}
