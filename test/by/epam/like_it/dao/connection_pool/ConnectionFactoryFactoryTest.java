package by.epam.like_it.dao.connection_pool;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;


public class ConnectionFactoryFactoryTest {
    @Test
    public void setConnectionType() throws Exception {

    }

    @Test
    public void getConnectionFactory() throws Exception {
          ConnectionFactoryFactory factory = ConnectionFactoryFactory.getInstance();
        ConnectionFactory connectionFactory = factory.getConnectionFactory();
        connectionFactory.initPoolData();
        Connection connection = connectionFactory.takeConnectionWithoutCommit();
    }

}