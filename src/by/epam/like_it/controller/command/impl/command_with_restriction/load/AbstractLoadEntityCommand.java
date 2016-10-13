package by.epam.like_it.controller.command.impl.command_with_restriction.load;


import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import by.epam.like_it.service.PrevQueryService;
import by.epam.like_it.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractLoadEntityCommand implements Command {

    protected static final ServiceFactory SERVICE_FACTORY = ServiceFactory.getInstance();

    protected String getPrevQuery(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return getPrevQuery(session);
    }

    protected String getPrevQuery(HttpSession session) {
        PrevQuery prevQuery = (PrevQuery) session.getAttribute(CommandConstants.PREV_QUERY_SESS_ATTR);
        return getPrevQuery(prevQuery);
    }

    protected String getPrevQuery(PrevQuery prevQuery){
        PrevQueryService prevQueryService = SERVICE_FACTORY.getPrevQueryService();
        return prevQueryService.getFullPrevQuery(prevQuery);
    }
}

