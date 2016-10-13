package by.epam.like_it.exception.service.action;

import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;

public class ServiceAuthException extends ServiceActionDetectableException {

	private static final long serialVersionUID = 1L;

	public static final String LOGIN_FAIL = "login";
	public static final String PASSWORD_FAIL ="password";
	public static final String BANNED_FAIL ="banned";

	public ServiceAuthException() {}

	public ServiceAuthException(ErrorInfo errorInfo) {
		super(errorInfo);
	}

	public ServiceAuthException(String message) {
		super(message);
	}

	public ServiceAuthException(String message, Exception e) {
		super(message, e);
	}
}
