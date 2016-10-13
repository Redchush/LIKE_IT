package by.epam.like_it.dao.connection_pool.impl.raw;


import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionPoolDefinition;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;


import java.sql.Connection;
import java.sql.SQLException;

public class RawConnection implements ConnectionFactory {

    @Override
    public Connection takeConnectionWithoutCommit() throws ConnectionPoolException {
        ConnectionPoolDefinition poolDefinition = ConnectionPoolDefinition.getInstance();
        Connection connection = poolDefinition.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new  ConnectionPoolException("Database not maintain commit operations");
        }
        return connection;
    }

    @Override
    public void dispose() {
        //NOP
    }

    @Override
    public void initPoolData() throws ConnectionPoolException {
        //NOP
    }
}
