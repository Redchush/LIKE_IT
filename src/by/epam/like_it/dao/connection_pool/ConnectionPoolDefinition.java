package by.epam.like_it.dao.connection_pool;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.ResourceManager;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static by.epam.like_it.common_util.DBNavigator.*;

public final class ConnectionPoolDefinition {

    private final String url;
    private final String driver;
    private final String user;
    private final String password;

    private final int poolSize;
    private final int defaultPoolSize =5;

    private static ConnectionPoolDefinition instance;

    private static Logger logger = LogManager.getLogger(ClassName.getClassName());

    /**
     * load driver and
     * @throws ConnectionPoolException
     */

    private ConnectionPoolDefinition() throws ConnectionPoolException {
        int tempPoolSize;
        url = ResourceManager.DB.getString(DB_URL);
        user =  ResourceManager.DB.getString(DB_USER);
        password =  ResourceManager.DB.getString(DB_PASSWORD);
        try{
            String size = ResourceManager.DB.getString(DB_POOL_SIZE);
            tempPoolSize = Integer.parseInt(size);
        } catch (NumberFormatException e){
            tempPoolSize = defaultPoolSize;
        }
        poolSize = tempPoolSize;
        driver =  ResourceManager.DB.getString(DB_DRIVER);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can't load the driver by path " + driver, e);
        }
        logger.info("Definitor created " + this);
    }
   
    public static ConnectionPoolDefinition getInstance() throws ConnectionPoolException {
        if (instance == null)
            synchronized (ConnectionPoolDefinition.class){
                if (instance == null)
                    instance = new ConnectionPoolDefinition();
            }
        return instance;
    }

    public String getUrl() {
        return url;
    }

    public String getDriver() {
        return driver;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error("ConnectionPoolDefinition can't create connection with " +
                    "url = " + url + ", user = " + user + ", password = " + password, e);
            throw new ConnectionPoolException("ConnectionPoolDefinition can't create connection with this data", e);
        }
        return connection;
    }

    @Override
    public String toString() {
        return "ConnectionPoolDefinition{" +
                "url='" + url + '\'' +
                ", driver='" + driver + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", poolSize=" + poolSize +
                ", defaultPoolSize=" + defaultPoolSize +
                '}';
    }
}
