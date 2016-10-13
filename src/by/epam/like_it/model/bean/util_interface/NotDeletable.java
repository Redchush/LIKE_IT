package by.epam.like_it.model.bean.util_interface;

/**
 * The marker interface, that define whether the entity in database deleted or updated by
 * set some boolean property
 */
public interface NotDeletable extends RealEntity {

    default Boolean getDefaultCreateBan(){
        return true;
    }

    default Boolean getDefaultDelete(){
        return false;
    }

    Long getId();

    void setId(Long id);


}
