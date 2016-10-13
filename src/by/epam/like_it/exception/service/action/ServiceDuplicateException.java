package by.epam.like_it.exception.service.action;


public class ServiceDuplicateException extends ServiceActionException {

    private static final long serialVersionUID = 1L;
    private String actionKey;

    public ServiceDuplicateException() {}

    public String getActionKey() {
        return actionKey;
    }

    public void setActionKey(String actionKey) {
        this.actionKey = actionKey;
    }
}
