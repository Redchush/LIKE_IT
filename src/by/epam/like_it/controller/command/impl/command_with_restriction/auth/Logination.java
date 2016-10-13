package by.epam.like_it.controller.command.impl.command_with_restriction.auth;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.PageNavigator;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.exception.service.action.ServiceAuthException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.epam.like_it.controller.command.util.CommandConstants.*;


public class Logination extends AbstractAuthenticationCommand  {

	private Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";

	private static final PageNavigator NAVIGATOR;
	private static final String SAME_PAGE;
	static {
		NAVIGATOR = PageNavigator.getInstance();
		SAME_PAGE = NAVIGATOR.getPage(PageNavigator.Pages.LOGINATION);
	}

	private static Logination instance;

	private Logination(){}

	public static Logination getInstance(){

		if (instance == null)
			synchronized (Logination.class){
				if (instance == null)
					instance = new Logination();
			}
		return instance;
	}

	@Override
	public String reallyExecute(HttpServletRequest request, HttpServletResponse response, User user)
			throws ValidationInfoException, ServiceAuthException, ServiceSystemException {
		String resultPage="";
		user = USER_SERVICE.authorise(user);
		if (user == null) {
			request.setAttribute(ERROR_FLAG_ATTR, ACTION_FAIL_VAL);
			resultPage = SAME_PAGE;
		} else {
			super.handleValidUser(request, user);
			resultPage = PageNavigator.getInstance().getPage(PageNavigator.Pages.INDEX);
		}
		return resultPage;
	}

	@Override
	public User getEntity(HttpServletRequest request){
		String login = request.getParameter(LOGIN);
		String password = request.getParameter(PASSWORD);
		LOGGER.debug("Somebody try to log in by login=" + login + " and password=" + password);
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		return user;
	}

	@Override
	public String getSamePage() {
		return SAME_PAGE;
	}
}
