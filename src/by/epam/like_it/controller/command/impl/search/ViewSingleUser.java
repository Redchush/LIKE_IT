package by.epam.like_it.controller.command.impl.search;


import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.controller.command.util.MsgHandler;
import by.epam.like_it.exception.service.action.ServiceEntityBannedException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.UserVO;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import by.epam.like_it.service.PrevQueryService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.UserService;
import by.epam.like_it.exception.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewSingleUser implements Command {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static ViewSingleUser instance;


    private static final ServiceFactory FACTORY;
    static {
        FACTORY = ServiceFactory.getInstance();
    }



    private ViewSingleUser(){}

    public static ViewSingleUser getInstance(){

        if (instance == null)
            synchronized (ViewSingleUser.class){
                if (instance == null)
                    instance = new ViewSingleUser();
            }
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession(false);
        Long idRequested = 0L;
        String parameter = request.getParameter(CommandConstants.ID_FROM_GET_PARAM);
        if (parameter == null){
            if (session != null) {
                idRequested = (Long) session.getAttribute(AuthSecurityConstants.USER_ID_ATTR);
            }
        } else {
            idRequested = Long.parseLong(parameter);
        }

        PrevQueryService prevQueryService = FACTORY.getPrevQueryService();
        PrevQuery prevQuery = (PrevQuery) session.getAttribute(CommandConstants.PREV_QUERY_SESS_ATTR);
        String prevQueryURI = prevQueryService.getFullPrevQuery(prevQuery);

        if (idRequested < 0){
            MsgHandler.getInstance().handleEntityBanned(request, User.class);
            return prevQueryURI;
        }
        UserService userService = FACTORY.getUserService();
        try {
            UserVO vo = userService.getUserProfile(idRequested);
            request.setAttribute(CommandConstants.USER_VO, vo);
            LOGGER.debug("User extracted : " + vo);
        } catch (ServiceEntityBannedException e){
            LOGGER.error("Somebody try to get banned user");
            MsgHandler.getInstance().handleEntityBanned(request, User.class);
        } catch (ServiceException e) {
            throw new CommandException("There are no such user");
        }
        return prevQueryURI;
    }
}
