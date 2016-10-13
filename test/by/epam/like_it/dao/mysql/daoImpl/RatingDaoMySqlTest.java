package by.epam.like_it.dao.mysql.daoImpl;

import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.RatingDao;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.model.bean.Rating;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import testUtil.missalenious.QueryTestUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;


public class RatingDaoMySqlTest {

    private static Connection connection;
    private static MySqlDaoFactory FACTORY;
    private static ConnectionFactory POOL;
    private static Number lastUserId;
    private static Number lastRatingId;
    private static List<Long> testUsers;

    private static RatingDao DAO;

    @BeforeClass
    public static void logIn() throws ConnectionPoolException, SQLException, PersistenceException {

        FACTORY = MySqlDaoFactory.getInstance();
        POOL = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        connection = POOL.takeConnectionWithoutCommit();
        DAO = MySqlDaoFactory.getInstance().getRatingDao();

        lastUserId = QueryTestUtil.getLastId(MySqlDaoFactory.getInstance().getUserDao());
        lastRatingId = QueryTestUtil.getLastId(DAO);

        testUsers = QueryTestUtil.createTestUsers(connection);

    }
    @AfterClass
    public static void logOut() throws SQLException {
        QueryTestUtil.executeAfter(connection, Rating.class, lastRatingId.longValue());
        QueryTestUtil.executeAfter(connection, User.class, lastUserId.longValue());
        POOL.dispose();
        connection.close();
    }

    @Test
    public void toggleRating() throws Exception {

        Rating rating = new Rating();
        rating.setUserId(testUsers.get(0));
        rating.setAnswerId(1L);
        rating.setBanned(false);
        rating.setRating((byte) 4);
        boolean b = DAO.toggleRating(rating);
        List<Rating> entityByEntity = DAO.findEntityByEntity(rating);
        Rating rating1 = entityByEntity.get(0);
        Long id = rating1.getId();
        assertEquals(rating1.getUserId(), rating.getUserId());
        assertEquals(rating1.getAnswerId(), rating.getAnswerId());
        assertEquals(rating1.getRating(), rating.getRating());

        byte newRating = (byte) 2;
        rating.setRating(newRating);
        boolean b1 = DAO.toggleRating(rating);
        Rating entityById = DAO.findEntityById(id);
        assertEquals((byte) entityById.getRating(), newRating);

    }



    //        private Long id;
//        private Long userId;
//        private Long answerId;
//        private Integer rating;
//        private Timestamp createdDate;
//        private Timestamp updatedDate;
//        private Boolean banned;

    @Test
    public void findAllUserRatingsByCriteria() throws Exception {

        Criteria<Rating> criteria = new Criteria<>();

        Long userTestedId = 2L;

        List<String> answerIds = Arrays.asList("1", "2");
        Set<String> ids = new HashSet<>(answerIds);

        EqConstriction<Rating, String> constriction = new EqConstriction<>(Rating.class, "answerId", ids);
        criteria.putConstriction(constriction);

        List<Rating> allUserRatingsByCriteria = DAO.findAllUserRatingsByCriteria(criteria, userTestedId);
        System.out.println(allUserRatingsByCriteria);
    }

}