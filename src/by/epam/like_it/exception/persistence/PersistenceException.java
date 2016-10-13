package by.epam.like_it.exception.persistence;


/**
 * root of hierarchy of exceptions in persistence layer
 */
public class PersistenceException extends Exception {

    public PersistenceException() {
        super();
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
