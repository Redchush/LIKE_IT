package by.epam.like_it.dao;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.ReflectionUtil;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.dao.mysql.collector.impl.NameBasedCollector;
import by.epam.like_it.dao.mysql.collector.impl.ReflectionBasedCollector;
import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.dao.mysql.util.query.QueryMediator;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemInternalException;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractRealEntityDao<T extends RealEntity> extends AbstractEntityDao<T> {

    protected final static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    /**
     * @param id of entity
     * @return entity found in persistence
     * @throws PersistenceSystemException
     */
    public T findEntityById(long id) throws PersistenceSystemException {

        String query = QUERY_MEDIATOR.getSelectById(this.getExampleObject().getClass());
        ResultSet set = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            set = statement.executeQuery();
            boolean next = set.next();
            if (!next){
                return null;
            }
            T result = NameBasedCollector.getCollectorByEntity(this.getExampleObject())
                                    .collectEntity(set, 0, "", this.getExampleObject());
            connection.commit();
            return result;
        } catch (SQLException e) {
            LOGGER.error("Can't execute findEntityById by initial query: \n" + query
                    + "\nwith filled statement: \n" + statement +
                    "\nand entity id: " + id  + " state :" + e.getSQLState(), e);
            throw new PersistenceSystemInternalException(this.getClass() + " : error during executing findEntityById", e);
        } catch (ConnectionPoolException e) {
            throw new PersistenceSystemInternalException(this.getClass().getSimpleName() + " : can't take the connection", e);
        } finally {
           closeAllSource(set, statement, connection);
        }
    }

    /**
     * Find all entities in persistence, which fields equals the entity parameter fields
     * @param entity - example by which entity will be searched
     * @return list of all entities which fields match fields of example
     * @throws PersistenceSystemException
     */

    public List<T> findEntityByEntity(T entity) throws PersistenceSystemException {
        String selectByObject = QUERY_MEDIATOR.getSelectByObject(entity);
        ReflectionBasedCollector<T> collector = new ReflectionBasedCollector<>();
        collector.setMode(ReflectionBasedCollector.Mode.SKIP_IF_NULL);
        return UTIL_HOLDER.getUtilMySql().findAll(selectByObject, entity, collector, null, false);
    }

    public boolean update(T entity) throws PersistenceSystemException, PersistenceNotUniqueException {
        return UTIL_HOLDER.getUtilMySql().update(entity);
    }


    /**
     * @param id - id of entity to be deleted
     * @return value that mark wether the entity was deleted or not
     * @throws PersistenceSystemException
     */
    public boolean delete(long id) throws PersistenceSystemException {
        String query = QUERY_MEDIATOR.getDelete(this.getExampleObject(), true);
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            int state = statement.executeUpdate();
            connection.commit();
            LOGGER.debug("Success with query " + statement );
            return isPositiveState(state);
        } catch (SQLException e) {
            try {
               connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Can't execute rollback from deleteByBanIfPossible by initial query: \n" + query
                        + "\nwith filled statement: \n" + statement +
                        "\n and entity  id \n " + id  + " state :" + e.getSQLState(), e);
                throw new PersistenceSystemInternalException(this.getClass() + " error in rollback after deleteByBanIfPossible by id");
            }
            LOGGER.error("Can't execute update by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    "\n and entity id: " + id  + " state :" + e.getSQLState(), e);
            throw new PersistenceSystemInternalException(this.getClass() + " can't deleteByBanIfPossible by id");
        } catch (ConnectionPoolException e) {
            throw new PersistenceSystemInternalException(this.getClass().getSimpleName() + " : can't take the connection", e);
        } finally {
            closeAllSource(statement, connection);
        }
    }
}
// state :23000 not unique