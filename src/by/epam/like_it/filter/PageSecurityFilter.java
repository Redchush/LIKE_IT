package by.epam.like_it.filter;

import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.common_util.ClassName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;


public class PageSecurityFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    private static final String INDEX_PAGE = "/index";
    private static final String REFERER_PARAMETER = "referer";

    private static final String EMPTY_STRING = "";

    private static final String ADMIN_PAGE_PREFIX = "admin_";
    private static final String CLIENT_PAGE_PREFIX = "client_";
    private static final String AUTH_CLIENT_PAGE_PREFIX = "auth_";
    private static final String OWN_CLIENT_PREFIX = "client_own_";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        HttpSession session = httpRequest.getSession();
        String url = httpRequest.getHeader(REFERER_PARAMETER);
//        String queryString = httpRequest.getQueryString();
        System.out.println("_______________");
//        LOGGER.debug("doing method" + httpRequest.getMethod());
//        LOGGER.debug("header names" + httpRequest.getHeaderNames());
//        LOGGER.debug("referrer " + url);
//        LOGGER.debug("queryString " + queryString);
//        LOGGER.debug("pathinfo" + httpRequest.getPathInfo());
//        LOGGER.debug("requestedURL " + httpRequest.getRequestURL());
//        LOGGER.debug("requestedURI " + httpRequest.getRequestURI());
//
//        LOGGER.debug("parameters" + httpRequest.getParameterMap());
//        LOGGER.debug("attributes" + Collections.list(httpRequest.getAttributeNames()));
//
//        LOGGER.debug("IN SESSION");
//        ArrayList<String> list = Collections.list(httpRequest.getSession(true).getAttributeNames());
//        LOGGER.debug("attributes" + list);


        ///////
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        ////////
        if (url != null) {
            Object admin = session.getAttribute(AuthSecurityConstants.ADMIN_VALUE);
            Object client = session.getAttribute(AuthSecurityConstants.CLIENT_VALUE);

            if ( (url.startsWith(ADMIN_PAGE_PREFIX) && admin == null) ||
                 (url.startsWith(CLIENT_PAGE_PREFIX) && client == null)  ||
                 (url.startsWith(AUTH_CLIENT_PAGE_PREFIX) && client == null && admin == null)) {
                LOGGER.info("Unauthorized access from page " + url + " criteria_to specified for " + httpRequest
                        .getParameterMap());
                httpResponse.sendRedirect(httpRequest.getContextPath() + INDEX_PAGE);
            }
            if ((url.startsWith(OWN_CLIENT_PREFIX)) ){
                String realId = (String) session.getAttribute(AuthSecurityConstants.USER_ID_ATTR);
                String authorizedFor = httpRequest.getParameter(AuthSecurityConstants.OWNER_ID_PARAM);
                if (!realId.equals(authorizedFor)){
                    LOGGER.info("Unauthorized access from page " + url + " criteria_to " + httpRequest.getParameterMap());
                    httpResponse.sendRedirect(httpRequest.getContextPath() + INDEX_PAGE);
                }
            }
        }
        LOGGER.debug("Else ");
        filterChain.doFilter(servletRequest, servletResponse);

    }

    private void redirectToDefaultPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + INDEX_PAGE);
    }

    @Override
    public void destroy() {}
}
