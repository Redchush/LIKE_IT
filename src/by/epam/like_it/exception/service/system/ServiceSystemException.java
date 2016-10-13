package by.epam.like_it.exception.service.system;

import by.epam.like_it.exception.service.ServiceException;

/**
 * Exceptions in service layer, which ultimate cause isn't connected with users action.
 */
public class ServiceSystemException extends ServiceException {

    public ServiceSystemException() {
    }

    public ServiceSystemException(String message) {
        super(message);
    }

    public ServiceSystemException(String message, Exception e) {
        super(message, e);
    }
}
