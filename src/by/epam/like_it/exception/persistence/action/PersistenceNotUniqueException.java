package by.epam.like_it.exception.persistence.action;

import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;

/**
 * Exception that caused by failed constriction rules of persistence presume existence some unique records
 */
public class PersistenceNotUniqueException extends PersistenceActionException {

    private ErrorInfo errorInfo;


    public PersistenceNotUniqueException() {}

    public PersistenceNotUniqueException(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public PersistenceNotUniqueException(String message) {
        super(message);
    }

    public PersistenceNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }
}

