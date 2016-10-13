package by.epam.like_it.listener;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.service.CashService;
import by.epam.like_it.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class CashListener implements ServletContextListener {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServiceFactory instance = ServiceFactory.getInstance();
        CashService cashService = instance.getCashService();
        cashService.invalidateCash();
    }
}
