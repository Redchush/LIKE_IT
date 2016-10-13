package by.epam.like_it.listener;


import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    /**
     * Put identification attributes in session: AuthSecurityConstants.ROLE_ATTR(initial value AuthSecurityConstants
     * .ANONYM_VALUE) and
     * AuthSecurityConstants.USER_ID_ATTR(initial value - 0)
     * @param event
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        session.setAttribute(AuthSecurityConstants.ROLE_ATTR, AuthSecurityConstants.ANONYM_VALUE);
        session.setAttribute(AuthSecurityConstants.USER_ID_ATTR, 0L);
        PrevQuery query = new PrevQuery();
        query.setUri("/index");
        session.setAttribute(CommandConstants.PREV_QUERY_SESS_ATTR, query);
        LOGGER.debug("session created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        LOGGER.debug("destroyed");
    }
}
