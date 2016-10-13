package by.epam.like_it.dao;

import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.Tag;
import org.junit.*;
import testUtil.missalenious.QueryTestUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class AbstractEntityDaoTest {

    private static Connection connection;
    private static MySqlDaoFactory FACTORY;
    private static ConnectionFactory POOL;
    private static UserDao userDao;
    private static TagDao tagDao;
    private Statement statement;
    private ResultSet set;

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException {
        POOL = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        FACTORY = MySqlDaoFactory.getInstance();
        connection = POOL.takeConnectionWithoutCommit();
        userDao =  FACTORY.getUserDao();
        tagDao = FACTORY.getTagDao();
    }


    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
    }

    @Before
    public void setUp() throws Exception {
        statement = connection.createStatement();

    }

    @After
    public void tearDown() throws Exception {
        if (set != null){
            set.close();
        }
        if (statement != null){
            statement.close();
        }

    }



    @Test
    public void createList() throws Exception {
        Long initialSize = QueryTestUtil.getLastId(tagDao).longValue();
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tags.add(new Tag(null, "test_maker_" + i));
        }
        String createList = QueryMaker.Create.getCreateList(Tag.class, tags.size(), true);
        try{
            List<Long> result = tagDao.create(tags);
            System.out.println(result);
            assertEquals(initialSize + tags.size(), (long) result.get(result.size() - 1));
        } finally {
            QueryTestUtil.executeAfter(connection, Tag.class, initialSize);
        }
    }
}