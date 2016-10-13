package by.epam.like_it.exception.persistence.system;




/**
 * Signalize that internal problem in layer occured that not connect with any data sends from it's client
 */
public class PersistenceSystemInternalException extends PersistenceSystemException {

    public PersistenceSystemInternalException() {
    }

    public PersistenceSystemInternalException(String message) {
        super(message);
    }

    public PersistenceSystemInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
