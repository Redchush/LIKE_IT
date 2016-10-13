package by.epam.like_it.filter.util;


import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.PageNavigator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class StandardSecurityActionHandler {

    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static final PageNavigator NAVIGATOR = PageNavigator.getInstance();

    private static StandardSecurityActionHandler instance;
    private StandardSecurityActionHandler(){}
    public static StandardSecurityActionHandler getInstance(){
        if (instance == null)
            synchronized (StandardSecurityActionHandler.class){
                if (instance == null)
                    instance = new StandardSecurityActionHandler();
            }
        return instance;
    }



    /**
     * Message with illegal access to page
     * @param request - current HttpServletRequest
     * @param response - current HttpServletResponse
     * @throws IOException - produced by sendRedirect method execution
     */
    public void redirectToLoginIllegalAccessToPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();
        String page = null;
        if (session != null) {
            String pagePattern = NAVIGATOR.getPage(PageNavigator.Pages.BREAKAGE_PAT_TO);
            page = String.format(pagePattern, 2, requestURI);
            LOGGER.info("Illegal access to page. Breakage info: role "
                    + session.getAttribute(AuthSecurityConstants.USER_ID_ATTR) + ".Redirected to " + page);
        } else {
           page = NAVIGATOR.getPage(PageNavigator.Pages.SESSION_EXPIRED);
        }
        response.sendRedirect(page);
    }

    /**
     * Message with illegal access to action
     * @param request current HttpServletRequest
     * @param response current HttpServletResponse
     * @throws IOException  - produced by sendRedirect method execution
     */
    public void redirectToLoginIllegalAccessToAction(HttpServletRequest request, HttpServletResponse response,
                                                     int code, String command)
            throws IOException {

        HttpSession session = request.getSession(false);
        String page;
        if (session != null) {
            String pagePattern = NAVIGATOR.getPage(PageNavigator.Pages.BREAKAGE_PAT);
            page = request.getServletContext().getContextPath() + String.format(pagePattern, code);
            LOGGER.info("Illegal access to action. Command : " + command + " Breakage info: role " + session
                        .getAttribute(AuthSecurityConstants.USER_ID_ATTR)+ ".Redirected to: " + page);
        } else {
            page = request.getServletContext().getContextPath() + NAVIGATOR.getPage(PageNavigator.Pages
                    .SESSION_EXPIRED);
            LOGGER.info(" User session expired. Redirected to: " + page);
        }
        response.setStatus(303);
        response.resetBuffer();
        try {
            response.sendRedirect(page);
        } catch (IOException e){
            LOGGER.debug(e);
        }

//        LOGGER.debug(response.getStatus());
//        LOGGER.debug(response.getHeaderNames());
//        Collection<String> headerNames = response.getHeaderNames();
//        ArrayList<String> list = new ArrayList<>();
//        headerNames.forEach(s-> list.add(response.getHeader(s)));
//        LOGGER.debug(list);
    }

    public void redirectToIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String INDEX_PAGE = PageNavigator.getInstance().getPage(PageNavigator.Pages.INDEX);
        response.sendRedirect(INDEX_PAGE);
    }


}
