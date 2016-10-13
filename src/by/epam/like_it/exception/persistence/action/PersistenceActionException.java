package by.epam.like_it.exception.persistence.action;

import by.epam.like_it.exception.persistence.PersistenceException;

/**
 * Class represent exceptions on persistence layer, that is the normal type of process and it's ultimate cause is user's
 * action.
 */
public class PersistenceActionException extends PersistenceException {

    public PersistenceActionException() {}

    public PersistenceActionException(String message) {
        super(message);
    }

    public PersistenceActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
