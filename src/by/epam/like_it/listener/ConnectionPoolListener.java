package by.epam.like_it.listener;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConnectionPoolListener implements ServletContextListener{

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static final ConnectionFactoryFactory factory = ConnectionFactoryFactory.getInstance();

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            factory.setConnectionType(ConnectionFactoryFactory.FactoryType.CUSTOM);
            ConnectionFactory connectionFactory = factory.getConnectionFactory();
            connectionFactory.initPoolData();
        } catch (ConnectionPoolException e){
            LOGGER.error("Can't create connection pool. Use raw connection type", e);
            factory.setConnectionType(ConnectionFactoryFactory.FactoryType.RAW);
        }
        LOGGER.debug("Connection pool created");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
          factory.getConnectionFactory().dispose();
          LOGGER.debug("Connection pool disposed");
    }
}
