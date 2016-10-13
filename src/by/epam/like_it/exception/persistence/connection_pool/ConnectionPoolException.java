package by.epam.like_it.exception.persistence.connection_pool;


public class ConnectionPoolException extends Exception {

    private static final long serialVersionID = 1L;

    public ConnectionPoolException() {}

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
