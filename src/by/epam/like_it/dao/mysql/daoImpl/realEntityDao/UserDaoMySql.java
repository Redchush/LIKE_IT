package by.epam.like_it.dao.mysql.daoImpl.realEntityDao;


import by.epam.like_it.dao.UserDao;

import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.UserVO;
import by.epam.like_it.model.vo.util.EmptyVoManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;

public class UserDaoMySql extends UserDao {

    private static UserDaoMySql instance;

    private UserDaoMySql(){}

    public static UserDaoMySql getInstance(){

        if (instance == null)
            synchronized (UserDaoMySql.class){
                if (instance == null)
                    instance = new UserDaoMySql();
            }
        return instance;
    }

    private final static String AUTHORIZE_SELECT = QueryMaker.Select.getSelectAll(User.class)
            + "WHERE login = ?";

    private final static String PROFILE_SELECT = QueryMaker.Select.getSelectPartOfQuery(User.class, true) +
            "\n, posts.posts_counter\n" +
            ", COUNT(DISTINCT answers.id) AS answer\n" +
            ", AVG(rating.rating) AS AVG\n" +
            ", SUM(rating.rating) AS total\n" +
            ", tags.id\n" +
            ", tags.name\n" +
            "FROM users \n" +
            "LEFT JOIN answers\n" +
            "ON users.id = answers.user_id AND answers.banned = FALSE\n" +
            "LEFT JOIN rating\n" +
            "ON answers.id = rating.answer_id AND rating.banned = FALSE\n" +
            "LEFT JOIN (SELECT COUNT(DISTINCT id) AS posts_counter, posts.user_id\n" +
            "           FROM posts\n" +
            "           WHERE user_id = ?\n" +
            "           GROUP BY posts.user_id) AS posts\t\n" +
            "ON users.id = posts.user_id  \n" +
            "LEFT JOIN (SELECT GROUP_CONCAT(tags.id) AS id, GROUP_CONCAT(tags.name) AS name, user_id\n" +
            "           FROM favorite_user_tags\n" +
            "           LEFT JOIN tags\n" +
            "\t   ON favorite_user_tags.tags_id = tags.id \n" +
            "\t   WHERE user_id = ?\n" +
            "\t   GROUP BY favorite_user_tags.user_id\t\t\n" +
            "\t   ) AS tags\n" +
            "ON tags.user_id = users.id\t\n" +
            "WHERE users.id = ?   \n" +
            "GROUP BY users.id";

    public static final FullStackCollector<UserVO> PROFILE_COLLECTOR = new FullStackCollector<UserVO>() {
        private final int countUsersField = ResourceNavigator.getAttrCount(User.class);

        @Override
        public UserVO collectEntity(ResultSet set, int shift, String aliasPrefix, UserVO instance)
                throws SQLException, PersistenceCollectorException {
            UserVO vo = new UserVO();
            User entity = COLLECTORS.createEntity(set, 0, new User());
            vo.setUser(entity);
            int counter = countUsersField;

            UserVO.Info info = new UserVO.Info();
            Long countAnswers = set.getLong(++counter);
            info.setAnswersCount(countAnswers);
            Long countPosts = set.getLong(++counter);
            info.setPostsCount(countPosts);
            Double avg = set.getDouble(++counter);
            info.setAvgRating(avg);
            Long totale = set.getLong(++counter);
            info.setTotalRating(totale);
            vo.setInfo(info);

            List<Tag> entityByGroupConcat = COLLECTORS.createEntityByGroupConcat(set, counter, new Tag());
            vo.setFavoriteTags(entityByGroupConcat);
            return vo;
        }

        @Override
        public int fillStatement(PreparedStatement statement, int from, Supplier supplier)
                throws SQLException, PersistenceCollectorException {
            int counter = 1;
            Long id = (Long) supplier.get();
            statement.setLong(counter++, id);
            statement.setLong(counter++, id);
            statement.setLong(counter, id);
            return counter;
        }
    };

    @Override
    protected User getExampleObject() {
        return new User();}



    @Override
    public User authorize(User user) throws PersistenceSystemException {
        User result = null;
        ResultSet set = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
            statement = connection.prepareStatement(AUTHORIZE_SELECT);
            statement.setString(1, user.getLogin());

            set = statement.executeQuery();
            boolean next = set.next();
            if (!next){
                return result;
            } else {
                result = COLLECTORS.createEntity(set, 0, new User());
            }
            connection.commit();
            return result;
        } catch (SQLException e) {
            LOGGER.error("Can't execute authorize by query:"  + statement);
            throw new PersistenceSystemException(this.getClass() + " : error during executing findEntityById", e);
        } catch (ConnectionPoolException e) {
            throw new PersistenceSystemException(this.getClass().getSimpleName() + " : can't take the connection", e);
        } finally {
            closeAllSource(set, statement, connection);
        }
    }

    @Override
    public UserVO getUserVoWithInfo(Long id) throws PersistenceSystemException {
        List<UserVO> all = UTIL_HOLDER.getUtilMySql().findAll(PROFILE_SELECT, new UserVO(), PROFILE_COLLECTOR, () -> id, false);
        if (all.isEmpty()){
            return EmptyVoManager.EMPTY_USER_VO;
        }
        return all.get(0);
    }
}

