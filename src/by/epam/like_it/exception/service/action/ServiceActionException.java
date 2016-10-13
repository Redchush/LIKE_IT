package by.epam.like_it.exception.service.action;

import by.epam.like_it.exception.service.ServiceException;

/**
 * Class represent exceptions on service layer, that is the normal type of process and it's ultimate cause is user's
 * action.
 */
public class ServiceActionException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public ServiceActionException() {}

    public ServiceActionException(String message) {
        super(message);
    }

    public ServiceActionException(String message, Exception e) {
        super(message, e);
    }


}
