package by.epam.like_it.filter;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.filter.util.SecurityCode;
import by.epam.like_it.filter.util.StandardSecurityActionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


import static by.epam.like_it.common_util.AuthSecurityConstants.*;

public class PostSecurityFilter implements Filter {

    private static final String POST_METHOD = "POST";
    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static final StandardSecurityActionHandler HANDLER = StandardSecurityActionHandler.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;



        String method = request.getMethod();
        String command = null;
        int indexOfOpt = -1;
        boolean isBroken = false;
        Long current_id = -1L;
        int code= SecurityCode.POST_COMMAND_HAS_NOT_PRIVILEDGE;
        boolean isAdmin = false;

        if (method.equals(POST_METHOD)
                && (command = getCommand(request))!= null
                && (indexOfOpt = command.lastIndexOf(CommandConstants.COMMAND_OPT_DELIMITER)) != -1) {
            LOGGER.debug("Post in process + " + request.getRequestURI());
            HttpSession session = request.getSession(false);
            if (session == null || ((current_id = (Long) session.getAttribute(USER_ID_ATTR)) == null) || current_id < 0L){
                HANDLER.redirectToLoginIllegalAccessToPage(request, response);
                return;
            }
            String option = extractOptValue(command, indexOfOpt);
            String role = (String) session.getAttribute(ROLE_ATTR);
            String owner = request.getParameter(OWNER_ID_PARAM);

            isAdmin = ADMIN_VALUE.equals(role);
            boolean isClient = CLIENT_VALUE.equals(role);

            switch (option) {
                case OPT_ADMIN_VALUE:
                    isBroken = !isAdmin;
                    code = SecurityCode.POST_COMMAND_HAS_NOT_PRIVILEDGE;
                    break;
                case OPT_CLIENT_VALUE:
                    isBroken= !isClient;
                    code = SecurityCode.POST_COMMAND_UNREGISTER;
                    break;
                case OPT_AUTH_VALUE:
                    isBroken= !isAdmin && !isClient;
                    code = SecurityCode.POST_COMMAND_UNREGISTER;
                    break;
                case OPT_OWNER_VALUE:
                    isBroken = owner == null || !owner.equals(current_id.toString());
                    code = SecurityCode.POST_COMMAND_NOT_OWNER;
                    break;
                case OPT_OWNER_AND_ADMIN_VALUE:
                    isBroken =( owner == null || !owner.equals(current_id.toString())) && !isAdmin;
                    code = SecurityCode.POST_COMMAND_NOT_OWNER;
                    break;
            }
        }
        if (isBroken) {
            HANDLER.redirectToLoginIllegalAccessToAction(request, response, code, command);
            HANDLER.redirectToIndex(request, response);
            return;
        } else {
            request.setAttribute(USER_ID_ATTR, current_id);
            request.setAttribute(IS_ADMIN_ATTR, isAdmin);
//            filterChain.doFilter(servletRequest, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String extractOptValue(String commandPar, int indexOfDelimiter){
        return commandPar.substring(indexOfDelimiter + 1, commandPar.length());
    }

    private String getCommand(HttpServletRequest request){
       String command = request.getParameter(CommandConstants.COMMAND_ATTR);
       if (command == null){
           command = (String) request.getAttribute(CommandConstants.COMMAND_ATTR);
       }
       return command;
    }

    @Override
    public void destroy() {

    }
}
