package by.epam.like_it.dao.mysql.daoImpl;

import by.epam.like_it.dao.AnswerDao;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.AnswerVO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import testUtil.missalenious.QueryTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class AnswerDaoMySqlTest {


    private static User userTested;
    private static AnswerDao DAO;
    private static Connection CONNECTION;
    private static ConnectionFactory CONNECTION_FACTORY;
    private static long  lastId;

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException {
        CONNECTION_FACTORY = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        CONNECTION = CONNECTION_FACTORY.takeConnectionWithoutCommit();
        DAO = MySqlDaoFactory.getInstance().getAnswerDao();
        lastId  = QueryTestUtil.getLastId(DAO).longValue();
        long id = 1L;
        String login = "lara";
        String password = "m23kujj";
        String email = "lara@mail.ru";

        String lastName = null;
        String firstName = null;
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 5, 14, 14, 33, 4);
        cal.set(Calendar.MILLISECOND, 0);
        Timestamp createdDate = new Timestamp(cal.getTimeInMillis());

        Timestamp udatedDate = null;
        boolean isBanned = false;
        userTested = new User(id, (byte)3l,  (byte)2, login, password, email, lastName,
                firstName, null, null, createdDate, udatedDate, isBanned);
    }

    @AfterClass
    public static void logOut() throws SQLException {
        CONNECTION.close();
    }


    @Test
    public void fillLastParameterWithId() throws SQLException, ConnectionPoolException {

        String query = " UPDATE id, login, password, email, role_id, last_name, banned, first_name, created_date, " +
                "updated_date  SET " +
                "id = DEFAULT,  login = ?,  password = ?,  email = ?,  role_id = ?,  last_name = ?,  banned = ?,  " +
                "first_name = ?,  created_date = ?,  updated_date  = ?  where id = ? ";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
//        daoTestedProtected.fillLastParameterWithId(statement, userTested);
        assertTrue(statement.toString().contains("where id = 1"));
    }

    @Test
    public void fillStatementWithFullAttributesSet() throws SQLException {
        String querty = " UPDATE id, login, password, email, role_id, last_name, banned, first_name, created_date, " +
                "updated_date  SET " +
                "id = DEFAULT,  login = ?,  password = ?,  email = ?,  role_id = ?,  last_name = ?,  banned = ?,  " +
                "first_name = ?,  created_date = ?,  updated_date  = ?  where id = ? ";
        PreparedStatement statement = CONNECTION.prepareStatement(querty);
//        daoTestedProtected.fillLastParameterWithId(statement, userTested);
//        System.out.println(statement);
        assertTrue(statement.toString().contains("where id = 1"));
        String email = userTested.getEmail();
//        statement.setString(3, email);
        System.out.println(querty);
    }

    @Test
    public void getAnswerVOByPostId() throws Exception {
        Long testedID = 4L;
        List<AnswerVO> answerVOByPostId = DAO.getAnswerVOByPostId(testedID);
        for (AnswerVO answerVO : answerVOByPostId ){
            System.out.println(answerVO);
        }
        Long testedID_2 = 3L;
        List<AnswerVO> actual = DAO.getAnswerVOByPostId(testedID_2);
        for (AnswerVO answerVO : actual ){
            System.out.println(answerVO);
        }

        Long testedUnexisting = -1L;
        List<AnswerVO> emptyExpected = DAO.getAnswerVOByPostId(testedUnexisting);
        assertNotNull(emptyExpected);
        assertTrue(emptyExpected.isEmpty());
    }




}