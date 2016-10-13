package by.epam.like_it.exception.persistence.system;

/**
 * Exception, that can be called by broken Collector, which extract and build some bean entity from ResultSet
 */
public class PersistenceCollectorException extends PersistenceSystemException {

    public PersistenceCollectorException() {}

    public PersistenceCollectorException(String message) {
        super(message);
    }

    public PersistenceCollectorException(String message, Throwable cause) {
        super(message, cause);
    }
}
