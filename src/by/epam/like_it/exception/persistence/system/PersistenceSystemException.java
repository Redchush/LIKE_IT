package by.epam.like_it.exception.persistence.system;


import by.epam.like_it.exception.persistence.PersistenceException;

/**
 * Signalize that problem in layer occured that is not in a normal process of handling user data;
 */
public class PersistenceSystemException extends PersistenceException {

    public PersistenceSystemException() {}

    public PersistenceSystemException(String message) {
        super(message);
    }

    public PersistenceSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
