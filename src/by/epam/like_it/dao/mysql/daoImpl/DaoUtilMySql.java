package by.epam.like_it.dao.mysql.daoImpl;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.ReflectionUtil;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.dao.mysql.collector.impl.ReflectionBasedCollector;
import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.dao.mysql.util.query.QueryMediator;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.system.PersistenceUnsupportedOperationException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemInternalException;
import by.epam.like_it.exception.persistence.util.ExceptionParser;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.Entity;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.sql.*;
import java.util.*;
import java.util.function.Supplier;

public class DaoUtilMySql implements DaoUtilInner {

    private static DaoUtilMySql instance;
    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    public static final String CALC_ROWS = "SELECT FOUND_ROWS()";

    private final QueryMediator QUERY_MEDIATOR;
    private final ConnectionFactory CONNECTION_FACTORY;


    private DaoUtilMySql(){
        QUERY_MEDIATOR = QueryMediator.getInstance();
        CONNECTION_FACTORY = ConnectionFactoryFactory.getInstance().getConnectionFactory();
    }

    public static DaoUtilMySql getInstance(){

        if (instance == null)
            synchronized (DaoUtilMySql.class){
                if (instance == null)
                    instance = new DaoUtilMySql();
            }
        return instance;
    }



    /**
     * Find entity in persistence by all fields of argument entity (persistance entity must be the same as argument
     * entity's fields)  that not equals to null
     * @param entity - entity to be find.
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Entity> findEntityByEntity(Entity entity) throws PersistenceSystemException {
        String selectByObject = QUERY_MEDIATOR.getSelectByObject(entity);
        ReflectionBasedCollector<Entity> collector = new ReflectionBasedCollector<>();
        collector.setMode(ReflectionBasedCollector.Mode.SKIP_IF_NULL);
        return findAll(selectByObject, entity, collector, null, false);
    }
    /**
     * Wrapper criteria_to findAll queries, which correctly create and close resources and handle exceptions
     * @param query - query criteria_to be executed in PrepareStatement
     * @param instance - example instance which will be collected
     * @param collector - COLLECTORS specified criteria_to collect entities from ResultSet
     * @param scrollable - if the ResultSet cursor can move backward
     * @param <D> - type of entities criteria_to be collected
     * @return the list of entities criteria_to be found
     * @throws PersistenceSystemInternalException - if internal exception occured
     */
    @Override
    public <D> List<D> findAll(String query, D instance,
                                 FullStackCollector<D> collector, Supplier supplier,
                                 boolean scrollable)
            throws PersistenceSystemException {

        ResultSet set = null;
        PreparedStatement statement = null;
        Connection connection= null;
        try {
            connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
            statement = scrollable ? connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                                   : connection.prepareStatement(query);
            int newFrom = collector.fillStatement(statement, 0, instance);
            collector.fillStatement(statement, newFrom, supplier);
            set = statement.executeQuery();
            LOGGER.debug(this.getClass() + " successfully executed query\n " + statement
                    + "\n.The condition see in method createEntityList.");
            List<D> ds = collector.collectEntityList(set, "", instance);
            connection.commit();
            return ds;
        } catch (SQLException e) {
            LOGGER.error(this.getClass().getSimpleName() + "can't execute findAll. state :" + e.getSQLState()
                    + ", filled statement: \n"  + statement, e);
            throw new PersistenceSystemInternalException(this.getClass().getSimpleName() + " : error during executing findAll", e);
        } catch (ConnectionPoolException e) {
            throw new PersistenceSystemInternalException(this.getClass().getSimpleName() + " : can't take the connection", e);
        } finally {
            closeAllSource(set, statement, connection);
        }
    }


    /**
     * Collect list of entities by specified collector and count found rows;
     * Not applicable for hierarchy structures with one-criteria_to-many links, so in that case return count of the "widest"
     * place of set, not count of requested entities.
     * @param query - query criteria_to be executed
     * @param instance - instance criteria_to be collected
     * @param collector - PROFILE_COLLECTOR responsible for extract entity from result set
     * @param scrollable - if cursor must be scrollable(move in both directions)
     * @param <D> - class of entity requested criteria_to extract
     * @return - object, that holds list of entities and their count regard of limit restriction
     * @throws PersistenceSystemException
     */
    @Override
    public <D> ListCounterResponse<D> findAllAndCountRows(String query, D instance,
                                                          FullStackCollector<D> collector, Supplier supplier,
                                                          boolean scrollable)
            throws PersistenceSystemException {

        Connection connection= null;
        PreparedStatement statement = null;
        ResultSet set = null;
        Statement calcRows = null;
        ResultSet setRows = null;
        try {
            connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
            statement = scrollable ? connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                                   : connection.prepareStatement(query);
            int i = collector.fillStatement(statement, 1, instance);
            collector.fillStatement(statement, i, supplier);
            set = statement.executeQuery();
            LOGGER.debug(this.getClass() + " successfully executed query\n " + query
                    + "\n.The condition see in method createEntityList.");
            List<D> resultList = collector.collectEntityList(set, "", instance);
            calcRows = connection.createStatement();
            setRows =  calcRows.executeQuery(CALC_ROWS);
            Long total = null;
            boolean next = setRows.next();
            if (next){
                total = setRows.getLong(1);
            }
            connection.commit();
            return new ListCounterResponse<D>(resultList, total);
        } catch (SQLException e) {
            LOGGER.error("Can't execute findAll with persistence "
                            + this.getClass() + "\nwith filled statement: \n"  + statement  + "\n state :" + e.getSQLState(),
                    e);
            throw new PersistenceSystemException(this.getClass().getSimpleName() + " : error during executing findAll", e);
        } catch (ConnectionPoolException e) {
            throw new PersistenceSystemException(this.getClass().getSimpleName() + " : can't take the connection", e);
        } finally {
            closeAllSource(setRows, calcRows);
            closeAllSource(set, statement, connection);
        }
    }

    /**
     * Update all fields of in persistence.
     * @param entity - entity to be updated
     * @throws PersistenceSystemException : PersistenceSystemInternalException if some internal problem occured
     * @throws PersistenceNotUniqueException : if updating entity led to break unique or primary violation constraint
     */
    @Override
    public boolean update(RealEntity entity) throws PersistenceSystemException, PersistenceNotUniqueException {
        String query = QUERY_MEDIATOR.getUpdateByAllFields(entity);
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
            statement = connection.prepareStatement(query);
            ReflectionBasedCollector collector = new ReflectionBasedCollector();
            int i = collector.fillStatement(statement, 0, entity);
            long l = entity.getId().longValue();

            statement.setLong(i, l);

            int state = statement.executeUpdate();
            connection.commit();
            LOGGER.debug(this.getClass() + " successfully executed query " + query
                    + ".The condition is " + entity);
            return state == 1;
        } catch (SQLException e) {
            try {
                LOGGER.debug("rollback update " +  entity);
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Can't execute rollback from update by initial query: \n" + query
                        + "with filled statement: \n" + statement +
                        " and entity \n " + entity +  " state :" + e.getSQLState(), e);
                throw new PersistenceSystemInternalException(this.getClass() + " error in rollback after update by entity", e);
            }
            LOGGER.error("Can't execute update by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    " and entity \n "  + entity + " state :" + e.getSQLState(), e);
            ErrorInfo errorInfo = ExceptionParser.getInstance().parseSQLException(e);
            if (errorInfo != null){
                throw new PersistenceNotUniqueException(errorInfo);
            } else {
                throw new PersistenceSystemInternalException("Can't execute update", e);
            }
        } catch (ConnectionPoolException e) {
            throw new PersistenceSystemInternalException(this.getClass().getSimpleName() + " : can't take the connection", e);
        } finally {
            closeAllSource(statement, connection);
        }
    }

    /**
     * Update by PrepareStatement only not null fields(to set NULL to the persistence field use method update
     * (RealEntity entity));
     * Real entity must have not null id field, because on that value base will be executed searching entity need to
     * update. In other case throws PersistenceUnsupportedOperationException
     * @param entity
     * @return true if something updated and false if not
     * @throws PersistenceSystemException  - then internal problem occured
     * @throws PersistenceNotUniqueException - then updating the entity lead to violation of unique integrity
     * constriction of persistence
     * @throws PersistenceUnsupportedOperationException - if argument not have id field
     */
    @Override
    public boolean updateByObject(RealEntity entity) throws PersistenceNotUniqueException, PersistenceSystemException{
        Number id = entity.getId();
        if (entity.getId() == null){
            throw new PersistenceUnsupportedOperationException("Can't execute updateByObject with entity without id");
        }
        String query = QUERY_MEDIATOR.getUpdateByNotNullFields(entity);
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
            statement = connection.prepareStatement(query);
            ReflectionBasedCollector collector = new ReflectionBasedCollector();
            collector.setMode(ReflectionBasedCollector.Mode.SKIP_IF_NULL);
            collector.setSkip(1);
            int i = collector.fillStatement(statement, 0, entity);
            statement.setLong(i, id.longValue());

            int state = statement.executeUpdate();
            connection.commit();
            LOGGER.debug(this.getClass() + " successfully executed query " + query + ".The condition is " + entity);
            return state == 1;
        } catch (SQLException e) {
            try {
                LOGGER.debug("rollback update " +  entity);
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Can't execute rollback from update by initial query: \n" + query
                        + "with filled statement: \n" + statement +
                        " and entity \n " + entity +  " state :" + e.getSQLState(), e);
                throw new PersistenceSystemInternalException(this.getClass() + " error in rollback after update by entity", e);
            }
            LOGGER.error("Can't execute update by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    " and entity \n "  + entity + " state :" + e.getSQLState(), e);
            ErrorInfo errorInfo = ExceptionParser.getInstance().parseSQLException(e);
            if (errorInfo != null){
                throw new PersistenceNotUniqueException(errorInfo);
            } else {
                throw new PersistenceSystemInternalException("Can't execute update", e);
            }

        } catch (ConnectionPoolException e) {
            throw new PersistenceSystemInternalException(this.getClass().getSimpleName() + " : can't take the connection", e);
        } finally {
            closeAllSource(statement, connection);
        }

    }

    /**
     * @param entity - entity that mentioned to be add to persistence.
     *               Because of id fields is always generated automatically,  NOTICE that if entity has id field this
     *               field MUST BE EVAL TO NULL! In other case the the method throws an exception.
     *               Entity can't be null, in other case behavior is unpredictable.
     * @return the id under which the entity record saved in persistence;
     * @throws PersistenceNotUniqueException - if the reason of decline entity is the not unique attribute of entity
     * @throws PersistenceSystemException - if internal problem occured
     * @see PersistenceNotUniqueException
     */
    @Override
    public long create(Entity entity) throws PersistenceNotUniqueException, PersistenceSystemException {
        Class<?> classInRequest = entity.getClass();
        boolean hasId = QUERY_MEDIATOR.hasClassId(classInRequest);
        String query = QUERY_MEDIATOR.getCreate(classInRequest, 1, hasId);
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet set = null;
        try {
            connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ReflectionBasedCollector collector = new ReflectionBasedCollector();
            if (hasId) {
                collector.setSkip(1);
            }
            collector.fillStatement(statement, 0, entity);
            int state = statement.executeUpdate();
            set = statement.getGeneratedKeys();
            long last_inserted_id = 0;
            if(set.next()){
                last_inserted_id = set.getLong(1);
            }
            connection.commit();
            LOGGER.debug(this.getClass().getSimpleName() + " successfully executed query " + query
                    + ".The condition is " + entity + " state " + state + " last id " + last_inserted_id);
            return last_inserted_id;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Can't execute rollback from create by initial query: \n" + query
                        + "with filled statement: \n" + statement +
                        " and entity \n " + entity  + " state :" + e.getSQLState(), e);
                throw new PersistenceSystemInternalException(this.getClass() + " error in rollback after create by id", e);
            }
            ErrorInfo errorInfo = ExceptionParser.getInstance().parseSQLException(e);
            if (errorInfo != null){
                throw new PersistenceNotUniqueException(errorInfo);
            }
            LOGGER.error("Can't execute create by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    " and entity \n " + entity  + " state :" + e.getSQLState() + ". The rollback was successful.", e);

            throw new PersistenceSystemInternalException(this.getClass() + " error in create by id", e);
        } catch (ConnectionPoolException e) {
            throw new PersistenceSystemInternalException(this.getClass().getSimpleName() + " : can't take the connection", e);
        } finally {
            closeAllSource(set, statement, connection);
        }
    }


    @Override
    public boolean deleteReally(RealEntity entity) throws PersistenceSystemException {
        String query = QUERY_MEDIATOR.getDelete(entity, false);
        Long id = entity.getId().longValue();
        return delete(query, Collections.singletonList(id));
    }

    @Override
    public boolean deleteByBanIfPossible(RealEntity entity) throws PersistenceSystemException {
        String query = QUERY_MEDIATOR.getDelete(entity, true);
        Long id = entity.getId().longValue();
        return delete(query, Collections.singletonList(id));
    }

    /**
     * Ban entity and add constriction on ban that entity has predefined owner (typically corresponding field of
     * userId property) if it's possible. So if value of this fields equals to null throws
     * PersistenceUnsupportedOperationException;
     * @param entity - entity to be deleted
     * @return whether something be deleted
     * @throws PersistenceUnsupportedOperationException
     */
    @Override
    public boolean deleteByOwner(DeletableByBan entity) throws PersistenceSystemException{
       if (entity.getId() == null || entity.getUserId() == null){
           throw new PersistenceUnsupportedOperationException();
       }
        String query = QueryMaker.Delete.getDeleteResponsible(entity.getClass());
        List<Long> values = Arrays.asList(entity.getId(), entity.getUserId());
        return delete(query, values);
    }


    @Override
    public boolean deleteByMainAndDependant(Long main, Collection<Long> dependant,
                                            String queryWithIn) throws PersistenceSystemException {
        int size = dependant.size();
        String preperadPart = QUERY_MEDIATOR.getPreperadPart(size);
        String query = String.format(queryWithIn, preperadPart);
        List<Long> allLongs = new ArrayList<>(size+1);
        allLongs.add(main);
        allLongs.addAll(dependant);
        return delete(query, allLongs);
    }

    @Override
    public boolean delete(String query, List<Long> values) throws PersistenceSystemInternalException {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
            statement = connection.prepareStatement(query);
            int counter = 1;
            for(Long value: values) {
                statement.setLong(counter++, value);
            }
            int state = statement.executeUpdate();
            connection.commit();
            LOGGER.debug("Success with query " + statement );
            return state == 1;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Can't execute rollback from delete by initial query: \n" + query
                        + "\nwith filled statement: \n" + statement + " state :" + e.getSQLState(), e);
                throw new PersistenceSystemInternalException(this.getClass() + " error in rollback after deleteByBanIfPossible by id");
            }
            LOGGER.error("Can't execute update by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    " state :" + e.getSQLState(), e);
            throw new PersistenceSystemInternalException(this.getClass() + " can't deleteByBanIfPossible by id");
        } catch (ConnectionPoolException e) {
            throw new PersistenceSystemInternalException(this.getClass().getSimpleName() + " : can't take the connection", e);
        } finally {
            closeAllSource(statement, connection);
        }
    }


    @Override
    public void closeAllSource(ResultSet set, PreparedStatement statement, Connection connection) {
        try{
            if (set != null){
                set.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Can't close the result set");
        }
        closeAllSource(statement, connection);
    }

    @Override
    public void closeAllSource(PreparedStatement statement, Connection connection){
        try{
            if (statement != null){
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Can't close the statement");
        }
        try{
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Can't close the connection");
        }
    }

    @Override
    public void closeAllSource(ResultSet set, Statement statement){
        try{
            if (set != null){
                set.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Can't close the condition set");
        }

        try{
            if (statement != null){
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Can't close the statement");
        }
    }
}
