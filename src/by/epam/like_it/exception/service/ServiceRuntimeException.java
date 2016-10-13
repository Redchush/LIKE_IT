package by.epam.like_it.exception.service;


import org.jetbrains.annotations.NonNls;

public class ServiceRuntimeException extends RuntimeException {

    public ServiceRuntimeException() {
        super();
    }

    public ServiceRuntimeException(@NonNls String message) {
        super(message);
    }

    public ServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceRuntimeException(Throwable cause) {
        super(cause);
    }
}
