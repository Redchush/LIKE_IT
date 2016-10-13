package by.epam.like_it.exception.service.action;

import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;

/**
 * Action exception in service layer containing bean field, which existence lead to fail whole action
 */
public class ServiceActionDetectableException extends ServiceActionException {

    protected ErrorInfo errorInfo;

    public ServiceActionDetectableException() {}

    public ServiceActionDetectableException(String message) {
        super(message);
    }

    public ServiceActionDetectableException(String message, Exception e) {
        super(message, e);
    }

    public ServiceActionDetectableException(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public ServiceActionDetectableException(String message, ErrorInfo errorInfo) {
        super(message);
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }
}
