package by.epam.like_it.exception.service;

/**
 * root of hierarchy of service exceptions
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException() {}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Exception e) {
		super(message, e);
	}

}
