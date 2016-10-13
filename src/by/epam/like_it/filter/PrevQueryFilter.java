package by.epam.like_it.filter;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import by.epam.like_it.service.PrevQueryService;
import by.epam.like_it.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;


public class PrevQueryFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static final String FRONT_CONTROLLER_NAME = "FrontController";
    private static final ServiceFactory FACTORY = ServiceFactory.getInstance();
    private int contextLength;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        contextLength = filterConfig.getServletContext().getContextPath().length();
                /*NOP*/
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String requestURI = request.getRequestURI();
        if (request.getMethod().equals("GET") && !requestURI.contains("resources") && !requestURI.contains
                (FRONT_CONTROLLER_NAME) && !requestURI.contains("favicon")) {
            HttpSession session = request.getSession(true);
            PrevQuery prevQuery = definePrevQuery(session, request, requestURI);
            session.setAttribute(CommandConstants.PREV_QUERY_SESS_ATTR, prevQuery);
            LOGGER.debug("prevQuery saved !!!!: " + prevQuery);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private PrevQuery definePrevQuery(HttpSession session, HttpServletRequest request, String requestURI){
        PrevQueryService prevQueryService = FACTORY.getPrevQueryService();

        PrevQuery oldPrevQuery = (PrevQuery) session.getAttribute(CommandConstants.PREV_QUERY_SESS_ATTR);

        String oldBackQuery = oldPrevQuery.getBackQuery();

        String oldUri = oldPrevQuery.getUri();
        String oldFullQuery = prevQueryService.getFullPrevQuery(oldPrevQuery);

        HashMap<String, String[]> copyMap = new HashMap<String, String[]>(request.getParameterMap());
        String currentUri = requestURI.substring(contextLength);
        PrevQuery result = new PrevQuery(currentUri, copyMap);
            /*in case of reload page*/
        if (oldUri.equals(currentUri)){
            oldFullQuery = oldBackQuery;
        }
        result.setBackQuery(oldFullQuery);
        return result;
    }


    @Override
    public void destroy() {
        /*NOP*/
    }
}
