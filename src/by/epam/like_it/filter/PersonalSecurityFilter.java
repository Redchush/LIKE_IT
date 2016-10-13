package by.epam.like_it.filter;


import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.filter.util.StandardSecurityActionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PersonalSecurityFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static final StandardSecurityActionHandler HANDLER = StandardSecurityActionHandler.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String attribute = null;
        if ( session == null ||
            (attribute = (String) session.getAttribute(AuthSecurityConstants.ROLE_ATTR)) == null ||
            !attribute.equals(AuthSecurityConstants.CLIENT_VALUE)){
            HANDLER.redirectToLoginIllegalAccessToPage(request, response);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
