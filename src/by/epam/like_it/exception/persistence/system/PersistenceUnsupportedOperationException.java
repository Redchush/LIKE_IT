package by.epam.like_it.exception.persistence.system;

/**
 * Signal that dao method can't execute some operation with data in parameter. Mainly this problem is an client fault
 * and connected with lack of data validation.
 */
public class PersistenceUnsupportedOperationException extends PersistenceSystemException {

    public PersistenceUnsupportedOperationException() {}

    public PersistenceUnsupportedOperationException(String message) {
        super(message);
    }

    public PersistenceUnsupportedOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
