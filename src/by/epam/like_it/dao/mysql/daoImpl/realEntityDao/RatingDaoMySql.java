package by.epam.like_it.dao.mysql.daoImpl.realEntityDao;

import by.epam.like_it.dao.RatingDao;
import by.epam.like_it.dao.mysql.collector.impl.ReflectionBasedCollector;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemInternalException;
import by.epam.like_it.model.bean.Rating;
import by.epam.like_it.model.criteria_to.core.Criteria;

import java.sql.*;
import java.util.List;
import java.util.function.Supplier;


public class RatingDaoMySql extends RatingDao {


    private final String USER_RATING_ON_ANSWERS;
    private final String CREATE_ON_DUPLICATE_UPDATE;
    private static RatingDaoMySql instance;

    private RatingDaoMySql(){
        USER_RATING_ON_ANSWERS = QueryMaker.Select.getSelectAll(Rating.class) + " WHERE user_id = ?";
        CREATE_ON_DUPLICATE_UPDATE = QueryMaker.Create.getCreateOnDuplicateUpdate(Rating.class, "rating",
                "updatedDate", "banned");
    }

    public static RatingDaoMySql getInstance(){

        if (instance == null)
            synchronized (RatingDaoMySql.class){
                if (instance == null)
                    instance = new RatingDaoMySql();
            }
        return instance;
    }

    private static final FullStackCollector<Rating> USER_RATING_COLLECTOR = new FullStackCollector<Rating>(){

        @Override
        public Rating collectEntity(ResultSet set, int shift, String aliasPrefix, Rating instance)
                throws SQLException, PersistenceCollectorException {
            return COLLECTORS.createEntity(set, shift, instance);
        }

        @Override
        public int fillStatement(PreparedStatement statement, int from, Supplier supplier)
                throws SQLException, PersistenceCollectorException {
            Long id = (Long) supplier.get();
            statement.setLong(1, id);
            return 1;
        }
    };

    private static final FullStackCollector<Rating> USER_RATING_ON_DUPLICATE = new FullStackCollector<Rating>(){

        @Override
        public Rating collectEntity(ResultSet set, int shift, String aliasPrefix, Rating instance)
                throws SQLException, PersistenceCollectorException {
            return COLLECTORS.createEntity(set, shift, instance);
        }

        @Override
        public int fillStatement(PreparedStatement statement, int from, Supplier supplier)
                throws SQLException, PersistenceCollectorException {
            Long id = (Long) supplier.get();
            statement.setLong(1, id);
            return 1;
        }
    };

    @Override
    protected Rating getExampleObject() {
        return new Rating();
    }

    @Override
    public List<Rating> findAllUserRatingsByCriteria(Criteria<Rating> ratingCriteria, Long user_id)
            throws PersistenceSystemException {
        String select = CRITERIA_HANDLER.processCriteria(USER_RATING_ON_ANSWERS, ratingCriteria);
        return UTIL_HOLDER.getUtilMySql().findAll(select, new Rating(),USER_RATING_COLLECTOR, () ->user_id, false);
    }

    @Override
    public boolean toggleRating(Rating entity) throws PersistenceSystemException {
        String query = CREATE_ON_DUPLICATE_UPDATE;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
            statement = connection.prepareStatement(query);
            ReflectionBasedCollector collector = new ReflectionBasedCollector();
            collector.setSkip(1);
            int i = collector.fillStatement(statement, 0, entity);
            statement.setByte(i++, entity.getRating());
            statement.setTimestamp(i++, entity.getUpdatedDate());
            statement.setBoolean(i, entity.getBanned());
            int state = statement.executeUpdate();
            connection.commit();
            LOGGER.debug(this.getClass().getSimpleName() + " successfully executed query " + query
                    + ".The condition is " + entity + " state " + state);
            return state==1;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Can't execute rollback from create by initial query: \n" + query
                        + "with filled statement: \n" + statement +
                        " and entity \n " + entity  + " state :" + e.getSQLState(), e);
                throw new PersistenceSystemInternalException(this.getClass() + " error in rollback after create by id", e);
            }
            LOGGER.error("Can't execute create by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    " and entity \n " + entity  + " state :" + e.getSQLState() + ". The rollback was successful.", e);
            throw new PersistenceSystemException(this.getClass() + " error in create by id", e);
        } catch (ConnectionPoolException e) {
            throw new PersistenceSystemInternalException(this.getClass().getSimpleName() + " : can't take the connection", e);
        } finally {
            closeAllSource(statement, connection);
        }
    }
}


