package by.epam.like_it.model.bean.util_interface;

/**
 * The marker interface, that define whether the entity in database deleted or updated by
 * set banned property
 */
public interface DeletableByBan extends NotDeletable{

    Boolean getBanned();
    void setBanned(Boolean banned);

    @Override
    default Boolean getDefaultCreateBan() {
        return false;
    }

    @Override
    default Boolean getDefaultDelete() {
        return true;
    }

    Long getUserId();

    void setUserId(Long id);
}
