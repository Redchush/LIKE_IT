package by.epam.like_it.listener;


import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.service.MessageService;
import by.epam.like_it.service.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServiceInitializeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ServiceFactory instance = ServiceFactory.getInstance();
        MessageService messageService = instance.getMessageService();
        servletContext.setAttribute(CommandConstants.MSG_SERVICE, messageService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.removeAttribute(CommandConstants.MSG_SERVICE);
    }
}
