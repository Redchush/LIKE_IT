package by.epam.like_it.dao.connection_pool;


import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;

import java.sql.Connection;

public interface ConnectionFactory {

    /**
     * Ask pool for java.sql.Connection
     * @return java.sql.Connection
     * @throws ConnectionPoolException
     */
    Connection takeConnectionWithoutCommit() throws ConnectionPoolException;

    /**
     * Close whole pool
     */
    void dispose();

    void initPoolData() throws ConnectionPoolException;


}
