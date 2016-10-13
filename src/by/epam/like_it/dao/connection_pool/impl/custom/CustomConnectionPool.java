package by.epam.like_it.dao.connection_pool.impl.custom;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionPoolDefinition;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

/**
 default autocommit is false!!
 */
public final class CustomConnectionPool implements ConnectionFactory {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    private BlockingQueue<Connection> freeConnections;
    private BlockingQueue<Connection> givenConnections;

    private static CustomConnectionPool instance;

    private CustomConnectionPool(){}

    public static CustomConnectionPool getInstance() {
        if (instance == null)
            synchronized (CustomConnectionPool.class){
                if (instance == null)
                    instance = new CustomConnectionPool();
            }
        return instance;
    }

    @Override
    public void initPoolData() throws ConnectionPoolException{
        ConnectionPoolDefinition definitor = ConnectionPoolDefinition.getInstance();
        int size = definitor.getPoolSize();
        givenConnections = new ArrayBlockingQueue<Connection>(size);
        freeConnections = new ArrayBlockingQueue<Connection>(size);
        Connection connection = null;
        try {
            for (int i = 0; i < size; i++) {
                connection = definitor.getConnection();
                PooledConnection pooledConnection = new PooledConnection(connection);
                freeConnections.add(pooledConnection);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't create PooledConnection by " + definitor);
            throw new ConnectionPoolException("Can't create PooledConnection");
        }
        LOGGER.info("New connection pool created " + instance);
    }

    public void dispose(){
        emptyConnectionQueues();
    }

    private void emptyConnectionQueues() {
        try {
            closeConnectionQueue(freeConnections);
            closeConnectionQueue(givenConnections);
        } catch (SQLException e) {
            LOGGER.error("Error closing connection pool", e);
        }
    }

    public Connection takeConnectionWithoutCommit() throws ConnectionPoolException{
        Connection connection = null;
        try {
            connection = freeConnections.take();
            givenConnections.add(connection);
        } catch (InterruptedException e) {
            LOGGER.error("Error during taking Connection in connection pool", e);
            throw new ConnectionPoolException("Can't take connection from in connection pool", e);
        }
        return connection;
    }

    public void closeConnection(Connection connection, Statement statement, ResultSet set){
        try {
            if (set != null) {
                set.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Error during closing ResultSet of connection " + connection, e);
        }
        closeConnection(connection, statement);
    }

    public void closeConnection(Connection connection, Statement statement){
        try {
            if (statement != null){
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Error during closing Statement of connection " + statement, e);
        }
        try{
            connection.close();
        } catch (SQLException e){
            LOGGER.error("Error during closing Connection of connection " + connection, e);
        }
    }

    private void closeConnectionQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null){
            if (!connection.getAutoCommit()){
                connection.commit();
            }
            ((PooledConnection) connection).closeNaturally();
        }
    }


    private class PooledConnection implements Connection {

        private Connection connection;

        public PooledConnection(Connection connection) throws SQLException {
            if (connection != null) {
                this.connection = connection;
                this.connection.setAutoCommit(false);
                this.connection.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            } else
                throw new SQLException("Can't create connection wrapper on null reference ");
        }


        private void closeNaturally() throws SQLException {
            connection.close();
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()){
                LOGGER.debug("Attempt vo close closed connection.");
                throw new SQLException("Attempt vo close closed connection. ");
            }
            if (connection.isReadOnly()){
                connection.setReadOnly(false);
            }
            if (!givenConnections.remove(this)){
                LOGGER.debug("Can't deleteByBanIfPossible connection from givenConnections " + givenConnections);
                throw new SQLException("Can't deleteByBanIfPossible connection from givenConnections ");
            }

            if (!freeConnections.offer(this)){
                LOGGER.debug("Can't deleteByBanIfPossible connection from freeConnections " + freeConnections +
                        "connection " + connection);
                throw new SQLException("Can't allocate connection in pool.");
            }
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
                throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
                throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                                  int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                                             int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }
    }


}
