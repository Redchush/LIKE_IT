package by.epam.like_it.controller.command.impl.command_with_restriction.auth;


import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.PageNavigator;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.controller.command.util.CommandConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout implements Command{

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    private static final PageNavigator NAVIGATOR;
    static {
        NAVIGATOR = PageNavigator.getInstance();
    }

    private Logout(){}

    private static Logout instance;

    public static Logout getInstance(){

        if (instance == null)
            synchronized (Logout.class){
                if (instance == null)
                    instance = new Logout();
            }
        return instance;
    }


    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        LOGGER.debug("user " + session.getAttribute(AuthSecurityConstants.USER_ID_ATTR + " leave the site"));
        session.invalidate();
        request.setAttribute(CommandConstants.ACTION_ATTRIBUTE, CommandConstants.SEND_REDIRECT_VALUE);
        return NAVIGATOR.getPage(PageNavigator.Pages.INDEX);
    }


}
