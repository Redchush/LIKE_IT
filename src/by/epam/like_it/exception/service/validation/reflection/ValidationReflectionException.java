package by.epam.like_it.exception.service.validation.reflection;


import by.epam.like_it.exception.service.ServiceRuntimeException;


public class ValidationReflectionException extends ServiceRuntimeException {

    public ValidationReflectionException() {}


    public ValidationReflectionException(String message) {
        super(message);
    }

    public ValidationReflectionException(String message, Exception e) {
        super(message, e);
    }
}
