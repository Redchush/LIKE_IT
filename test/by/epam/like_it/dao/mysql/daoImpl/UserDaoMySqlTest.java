package by.epam.like_it.dao.mysql.daoImpl;

import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.UserDao;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;

import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.UserVO;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testUtil.missalenious.PredefinedEntity;
import testUtil.missalenious.QueryTestUtil;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;


public class UserDaoMySqlTest {

    private static ConnectionFactory CONNECTION_FACTORY;
    private static User userTestedWithBan;
    private static User userTestedWithoutBan;
    private static User userToUpdate;
    private static int initialSizeOftable;
    private static int lastInitialId;

    private static Connection CONNECTION;
    private static UserDao DAO;
    private static Number lastUserId;

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException, SQLException {
        CONNECTION_FACTORY = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        CONNECTION = CONNECTION_FACTORY.takeConnectionWithoutCommit();
        DAO = MySqlDaoFactory.getInstance().getUserDao();

        userTestedWithBan = PredefinedEntity.userTestedWithBan;
        userTestedWithoutBan = PredefinedEntity.userTestedWithoutBan;
        userToUpdate = PredefinedEntity.userToUpdate;

        lastUserId = QueryTestUtil.getLastId(DAO);

    }

    @AfterClass
    public static void logOut() throws SQLException {
        CONNECTION.close();
        CONNECTION_FACTORY.dispose();
    }

    @Before
    public void setUp() throws Exception {
//        lastInitialId = Helper
    }


    @Test
    public void getUserVoWithInfo() throws Exception {
        UserVO userVoWithInfo = DAO.getUserVoWithInfo(2L);
        System.out.println(userVoWithInfo);
    }

    @Test
    public void findAllTest() throws ConnectionPoolException, PersistenceException, SQLException {
        List<User> users = DAO.findAll();
        User user = users.get(0);
        assertEquals(userTestedWithBan, user);
    }

    @Test
    public void findById() throws ConnectionPoolException, PersistenceException, SQLException {
        User entityById = DAO.findEntityById(userTestedWithBan.getId());
        System.out.println(userTestedWithBan);
        System.out.println(entityById.getFotoPath() == null);
        assertEquals(userTestedWithBan, entityById);
    }

    @Test
    public void deleteTestById() throws PersistenceException {
        Long id = userTestedWithoutBan.getId();
        boolean isDelated = DAO.delete(id);

        User userActual = DAO.findEntityById(id);
        assertTrue(isDelated);
        assertEquals(userTestedWithBan, userActual);
    }

    @Test
    public void deleteByUserTest() {
        // deleteByBanIfPossible.userByBan
        Long id = userTestedWithoutBan.getId();
    }

    @Test
    public void create() throws PersistenceException, SQLException {
        String testCreateLogin = "_testLogin";
        try{
            User user = new User();
            user.setLogin(testCreateLogin);
            user.setPassword("1");
            user.setEmail("email");
            user.initRequiredDefault();
            long b = DAO.create(user);

            User entityById = DAO.findEntityById(b);
            assertEquals(entityById.getLogin(), testCreateLogin);

        } catch (PersistenceNotUniqueException e){
            System.out.println(e);
        }finally {
            try(Statement statement = CONNECTION.createStatement()){
                statement.executeUpdate(" DELETE FROM users WHERE login = '" + testCreateLogin + "'");
                statement.executeUpdate("ALTER TABLE `LIKE_IT`.`tags` " +
                        " AUTO_INCREMENT = " + (lastInitialId + 1));
            }
        }

    }

    @Test
    public void update() throws PersistenceException {
        User user = DAO.findEntityById(2);
        assertEquals(userToUpdate, user);
        user.setFirstName("a");
        DAO.update(user);
        User userActual = DAO.findEntityById(2);
        assertEquals(user, userActual);
    }

}