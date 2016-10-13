package by.epam.like_it.dao;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.ReflectionUtil;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.dao.mysql.collector.BeanCollectors;
import by.epam.like_it.dao.mysql.collector.impl.ReflectionBasedCollector;
import by.epam.like_it.dao.mysql.daoImpl.DaoUtilHolder;
import by.epam.like_it.dao.mysql.util.CriteriaHandler;
import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.dao.mysql.util.query.QueryMediator;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemInternalException;
import by.epam.like_it.exception.persistence.util.ExceptionParser;
import by.epam.like_it.model.bean.util_interface.Entity;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains operation for interaction that available for all Entity.
 * NOTICE that if method accept entity as parameter, it is not validate it on null value, so
 * it is responsible of client to check it. In other case behavior of methods is unpredictable;
 * @param <T>
 */

public abstract class AbstractEntityDao<T extends Entity>  {

    private final static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    protected final static BeanCollectors COLLECTORS = BeanCollectors.getInstance();
    protected final static CriteriaHandler CRITERIA_HANDLER = CriteriaHandler.getInstance();
    protected final static ConnectionFactory CONNECTION_FACTORY = ConnectionFactoryFactory.getInstance()
                                                                                    .getConnectionFactory();
    protected final static DaoUtilHolder UTIL_HOLDER = DaoUtilHolder.getInstance();
    protected final static QueryMediator QUERY_MEDIATOR = QueryMediator.getInstance();

    protected static final String CALC_ROWS = "SELECT FOUND_ROWS()";


    /**
     * Generally redirect criteria_to protected findAll with NameBasedCollector. Handling exceptions specified in
     * that method.
     * @return the list of entities criteria_to be found
     * @throws PersistenceSystemException
     */
    public List<T> findAll() throws PersistenceSystemException {
       String query = QUERY_MEDIATOR.getSelectAll(this.getExampleObject().getClass());
       return UTIL_HOLDER.getUtilMySql().findAll(query, this.getExampleObject(),
                new ReflectionBasedCollector<>(), null, false);
    }

    /**
     * @param entity - entity that mentioned to be add to persistence.
     *               Because of id fields is always generated automatically,  NOTICE that if entity has id field this
     *               field MUST BE EVAL TO NULL! In other case the the method throws an exception.
     *               Entity can't be null, in other case behavior is unpredictable.
     * @return the id under which the entity record saved in persistence;
     * @throws PersistenceNotUniqueException - if the reason of decline entity is the not unique attribute of entity
     * @throws PersistenceSystemException
     * @see PersistenceNotUniqueException

     */
    public long create(T entity) throws PersistenceNotUniqueException, PersistenceSystemException {
       return UTIL_HOLDER.getUtilMySql().create(entity);
    }


    /**
     * The method add records of all entities in the list parameter to persistence. NOTICE that entities will add in
     * one transaction and fail of saving one entity will lead to throwing exception and rollback to initial persistence
     * state. So even correct entities will not be saved if one failed.
     * Entities can't be null and the each entity in list can't be null, in other case behavior is unpredictable.
     * @return the id of last inserted entity
     * @throws PersistenceNotUniqueException - if the reason of decline entity is the not unique attribute of entity
     * @throws PersistenceSystemException
     * @see PersistenceNotUniqueException
     */
    public List<Long> create(List<T> entities) throws PersistenceNotUniqueException, PersistenceSystemException {
        Class<?> classInRequest = this.getExampleObject().getClass();
        boolean hasId = QUERY_MEDIATOR.hasClassId(classInRequest);
        String query = QUERY_MEDIATOR.getCreate(classInRequest, entities.size(), hasId);
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet set;
        try {
            connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
            statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ReflectionBasedCollector collector = new ReflectionBasedCollector();
            if (hasId) {
                collector.setSkip(1);
            }
            int currentPosition = 0;
            for (int i = 0; i < entities.size(); i++) {
                T entity = entities.get(i);
                currentPosition = collector.fillStatement(statement, currentPosition, entity) -1;
            }
            int state = statement.executeUpdate();
            List<Long> result = getGeneratedKeys(statement);
            connection.commit();
            LOGGER.debug(this.getClass() + " successfully executed \n" + statement
                    + "\n.The condition is " + entities + " state " + state + " last id " + result);
            return result;
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    LOGGER.debug("rollback create " +  entities);
                    connection.rollback();
                }
            } catch (SQLException e1) {
                LOGGER.error("Can't execute rollback from create by initial query: \n" + query
                        + "with filled statement: \n" + statement +
                        " and entity \n " + entities  + " state :" + e.getSQLState(), e);
                throw new PersistenceSystemInternalException(this.getClass() + " error in rollback after create by id", e);
            }
            ErrorInfo errorInfo = ExceptionParser.getInstance().parseSQLException(e);
            if (errorInfo != null){
                throw new PersistenceNotUniqueException(errorInfo);
            }
            LOGGER.error("Can't execute create by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    " and entity \n " + entities  + " state :" + e.getSQLState(), e);
            throw new PersistenceSystemInternalException(this.getClass() + " error in create by id", e);
        } catch (ConnectionPoolException e) {
            throw new PersistenceSystemInternalException(this.getClass().getSimpleName() + " : can't take the connection", e);
        } finally {
            closeAllSource(statement, connection);
        }
    }
    /**
     *
     * @return the object for representation in db persistence is bear responsibility for
     */
    protected abstract T getExampleObject();

    protected void closeAllSource(ResultSet set, PreparedStatement statement, Connection connection) {
        UTIL_HOLDER.getUtilMySql().closeAllSource(set, statement, connection);
    }

    protected void closeAllSource(PreparedStatement statement, Connection connection){
        UTIL_HOLDER.getUtilMySql().closeAllSource(statement, connection);
    }

    protected void closeAllSource(ResultSet set, Statement statement){
       UTIL_HOLDER.getUtilMySql().closeAllSource(set, statement);
    }

    /**
     * collect generated keys. Close after Result set but not close statement
     * @param statement - PreparedStatement , which recent execution  produced auto generated key
     * @return List<Long> , that contains recently generated ids
     * @throws SQLException - skipped exception after statement.getGeneratedKeys();
     */
    protected List<Long> getGeneratedKeys(PreparedStatement statement) throws SQLException {
        ArrayList<Long> result = new ArrayList<>();
        ResultSet set = statement.getGeneratedKeys();
        try {
            Long last_inserted_id = 0L;
            while (set.next()) {
                last_inserted_id = set.getLong(1);
                result.add(last_inserted_id);
            }
        } finally {
            if (set != null){
                set.close();
            }
        }
        return result;
    }

    protected boolean isPositiveState(int state){
        return state == 1;
    }
}
