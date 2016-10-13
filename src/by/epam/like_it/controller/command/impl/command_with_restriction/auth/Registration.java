package by.epam.like_it.controller.command.impl.command_with_restriction.auth;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.PageNavigator;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.service.action.ServiceAuthException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Registration extends AbstractAuthenticationCommand implements Command {

    protected Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String CONFIRM ="confirm_pas";

    private static final PageNavigator NAVIGATOR;
    private static final String SAME_PAGE;
    static {
        NAVIGATOR = PageNavigator.getInstance();
        SAME_PAGE = NAVIGATOR.getPage(PageNavigator.Pages.REGISTRATION);
    }
    private static Registration instance;

    private Registration(){}

    public static Registration getInstance(){

        if (instance == null)
            synchronized (Registration.class){
                if (instance == null)
                    instance = new Registration();
            }
        return instance;
    }

    @Override
    public String reallyExecute(HttpServletRequest request, HttpServletResponse response, User user)
            throws ValidationInfoException, ServiceAuthException, ServiceSystemException {
        String confirm = request.getParameter(CONFIRM);

        if (confirm == null || !confirm.equals(user.getPassword())){
            MSG_HANDLER.handleFailedValidation(request, User.class, "confirm");
            return SAME_PAGE;
        }
        user = USER_SERVICE.register(user);
        super.handleValidUser(request, user);
        return NAVIGATOR.getPage(PageNavigator.Pages.SUCCESS);
    }

    @Override
    public String getSamePage() {
        return SAME_PAGE;
    }

    @Override
    public User getEntity(HttpServletRequest request){
        String password = request.getParameter(PASSWORD);
        String login = request.getParameter(LOGIN);
        String email = request.getParameter(EMAIL);
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }


}
