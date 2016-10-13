package by.epam.like_it.exception.service.validation;


import by.epam.like_it.exception.service.action.ServiceActionException;

public class ValidationException extends ServiceActionException{

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Exception e) {
        super(message, e);
    }
}
