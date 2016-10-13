package by.epam.like_it.controller.command.impl;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.controller.command.util.MsgHandler;
import by.epam.like_it.exception.command.InvalidDataCommandException;
import by.epam.like_it.exception.service.action.ServiceActionDetectableException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import by.epam.like_it.service.PrevQueryService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.exception.service.action.ServiceDuplicateException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.exception.service.validation.info.ValidatorRequiredFieldIsNullException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.epam.like_it.common_util.AuthSecurityConstants.USER_ID_ATTR;

/**
 * Wrapper for handling post request. Redirect to do real actions to concrete realisation. Handle the result.
 * In case of :
 * ServiceDuplicateException - forwards to previous page with info message
 * ServiceException - allows FrontController define the next step
 * success - sends redirect to the page what return class with realisation or to the previous page in case of
 * absence response from realization class;
 */

public abstract class AbstractPostAuthCommand implements Command{

    protected static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    protected static final ServiceFactory SERVICE_FACTORY = ServiceFactory.getInstance();
    protected static final MsgHandler MSG_HANDLER = MsgHandler.getInstance();

    /**
     * Wrapper for handling post request. Redirect to do real actions to concrete realisation. Handle the result.
     * In case of :
     * ServiceDuplicateException - forwards to previous page with info message
     * ServiceException - allows FrontController define the next step
     * success - sends redirect to the page what return class with realisation or to the previous page in case of
     * absence response from realization class;
     *
     * @param request
     * @param response
     * @return
     * @throws CommandException
     * @see #getPrevUrl
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String parameter = request.getParameter(CommandConstants.COMMAND_ATTR);

        Long who = (Long) request.getAttribute(USER_ID_ATTR);
        String resultUrl = null;
        try {
            resultUrl = reallyExecute(request, response, who);
            resultUrl = handleSuccess(request, resultUrl);
        } catch (InvalidDataCommandException e){
            LOGGER.error("Invalid data from user.");
            resultUrl = e.getNewPath();
        } catch (ValidatorRequiredFieldIsNullException e) {
            LOGGER.error(parameter + " : some important fields is null! Failed fields: " + e.getInvalidInfo());
            throw new CommandException();
        } catch (ValidationInfoException e) {
             /* forward with info message */
            LOGGER.error(parameter + ": some fields isn't valid. Info: " + e.getInvalidInfo());
            MSG_HANDLER.handleFailedValidation(request, e);
        } catch (ServiceActionDetectableException e) {
            /* forward with info message */
            LOGGER.error(parameter + ": action failed. Info: " + e.getErrorInfo());
            MSG_HANDLER.handleFailedAction(request, e);
        } catch (ServiceSystemException e) {
            /* send redirect to error page */
            LOGGER.error("Can't execute " + this.getClass().getSimpleName());
            throw new CommandException(
                    this.getClass().getSimpleName() + ": can't execute" + this.getClass().getSimpleName());
        } catch (ServiceDuplicateException e) {
            LOGGER.error("Duplicate action occured " + this.getClass().getSimpleName());
        }

        LOGGER.debug(request.getAttribute(CommandConstants.MSG_BEAN));
        return (resultUrl != null) ? resultUrl : getPrevUrl(request);
    }

    public abstract String reallyExecute(HttpServletRequest request, HttpServletResponse response, Long who)
            throws ValidationInfoException, ServiceActionDetectableException, ServiceSystemException, CommandException, ServiceDuplicateException;

    protected String getPrevUrl(HttpServletRequest request){
        HttpSession session = request.getSession();
        PrevQuery prev = (PrevQuery) session.getAttribute(CommandConstants.PREV_QUERY_SESS_ATTR);
        PrevQueryService prevQueryService = SERVICE_FACTORY.getPrevQueryService();
        return prevQueryService.getFullPrevQuery(prev);
    }

    /**
     * define behaviour in case of success of action.
     * The child can override this method and change typical behaviour;
     * @param request
     */

    protected String handleSuccess(HttpServletRequest request, String resultUrl){
        request.setAttribute(CommandConstants.ACTION_ATTRIBUTE, CommandConstants.SEND_REDIRECT_VALUE);
        MSG_HANDLER.handleSuccessAction(request);
        String finalUrl = (resultUrl != null) ? resultUrl : getPrevUrl(request);
        LOGGER.debug( this.getClass().getSimpleName() + ": client command successfully executed. Redirect to " + finalUrl);
        return finalUrl;
    }



//        request.setAttribute(CommandConstants.ERROR_FLAG_ATTR, e.getActionKey());
//       LOGGER.error(this.getClass().getSimpleName() + " : duplicate action occured " + this.getClass().getSimpleName());
}
