package by.epam.like_it.dao;

import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import testUtil.missalenious.QueryTestUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class AbstractRealEntityDaoTest {

    private static Connection connection;
    private static UserDao userDao;
    private static DaoUtil util;
    private static Long lastUserId;

    private static PostDao postDao;
    private static Long lastPostId;
    private static Post post;
    private static MySqlDaoFactory FACTORY = MySqlDaoFactory.getInstance();

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException, SQLException {
        connection = ConnectionFactoryFactory.getInstance().getConnectionFactory().takeConnectionWithoutCommit();

        userDao = FACTORY.getUserDao();
        postDao =  FACTORY.getPostDao();
        util = FACTORY.getDaoUtil();
        lastUserId = QueryTestUtil.getLastId(userDao).longValue();
        lastPostId =  QueryTestUtil.getLastId(postDao).longValue();
        List<Long> testPostsId = QueryTestUtil.createTestPosts(connection);
        post = postDao.findEntityById(testPostsId.get(0));


    }

    @AfterClass
    public static void logOut() throws SQLException {
        QueryTestUtil.executeAfter(connection, User.class, lastUserId);
        QueryTestUtil.executeAfter(connection, Post.class, lastPostId);
        connection.close();
    }

    @Test
    public void deleteByOwner() throws Exception {
        assertFalse(post.getBanned());
        boolean b = util.deleteByOwner(post);
        assertTrue(b);
        Post entityById = postDao.findEntityById(post.getId());
        assertTrue(entityById.getBanned());

    }



}