package by.epam.like_it.controller.command.impl.command_with_restriction.auth;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.controller.command.util.MsgHandler;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.exception.service.action.ServiceAuthException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.epam.like_it.common_util.AuthSecurityConstants.*;

public abstract class AbstractAuthenticationCommand implements Command {

    private Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    protected static final UserService USER_SERVICE = ServiceFactory.getInstance().getUserService();
    protected static final MsgHandler MSG_HANDLER = MsgHandler.getInstance();

    protected void handleValidUser(HttpServletRequest request, User user){
        request.setAttribute(CommandConstants.ACTION_ATTRIBUTE, CommandConstants.SEND_REDIRECT_VALUE);
        HttpSession session = request.getSession(true);
        session.setAttribute(ROLE_ATTR, CLIENT_VALUE);
        session.setAttribute(USER_ID_ATTR, user.getId());
        session.setAttribute(PHOTO_PATH, user.getFotoPath());
        session.setAttribute(LOGIN, user.getLogin());
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException {
        String result = null;
        User user = null;
        try{
            user = getEntity(request);
            result = reallyExecute(request, response, user);
        } catch (ValidationInfoException e){
            this.LOGGER.error("Someone fail validation. Info : " + e.getInvalidInfo());
            MSG_HANDLER.handleFailedValidation(request, e);
            request.setAttribute(CommandConstants.PREV_ENTITY_BEAN, user);
        } catch (ServiceAuthException e) {
            this.LOGGER.error("Someone fail action : " + e.getErrorInfo());
            MSG_HANDLER.handleFailedAction(request, e);
            request.setAttribute(CommandConstants.PREV_ENTITY_BEAN, user);
        }  catch (ServiceSystemException e) {
            LOGGER.error("Internal exception", e);
            throw new CommandException("Command Exception", e);
        }
        if (result == null){
            result = getSamePage();
        }
        return result;
    }

    protected abstract User getEntity(HttpServletRequest request);

    protected abstract String reallyExecute(HttpServletRequest request, HttpServletResponse response, User user)
            throws ValidationInfoException, ServiceAuthException, ServiceSystemException;

    protected abstract String getSamePage();
}
