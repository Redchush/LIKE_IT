package by.epam.like_it.service.impl;

import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.PostDao;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.dao.mysql.daoImpl.realEntityDao.PostDaoMySql;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.ReadedPost;
import by.epam.like_it.model.bean.util_interface.Entity;
import by.epam.like_it.service.EntityService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import testUtil.missalenious.QueryTestUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;


public class EntityServiceImplTest {

    private static PostDao postDao;
    private static Connection connection;
    private static ConnectionFactory connectionFactory;
    private static EntityService SERVICE;

    private static Long postLastId;

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException, SQLException {
        connectionFactory = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        connection = connectionFactory.takeConnectionWithoutCommit();
        postDao = MySqlDaoFactory.getInstance().getPostDao();

        SERVICE =  EntityServiceImpl.getInstance();
        postLastId = QueryTestUtil.getLastId(PostDaoMySql.getInstance()).longValue();

//        List<Long> testPosts = QueryTestUtil.createTestPosts(connection);

    }

    @AfterClass
    public static void logout() throws SQLException {
        QueryTestUtil.executeAfter(connection, Post.class, postLastId);
        connection.close();
        connectionFactory.dispose();
    }



    @Test
    public void createEntity() throws Exception {
        ReadedPost post = new ReadedPost();
        post.setUserId(1L);
        post.setPostId(51L);
        long entity = SERVICE.createEntity(post);
        System.out.println(entity);

        List<Entity> entityByEntity =  SERVICE.findEntityByEntity(post);
        assertEquals(entityByEntity.get(0), post);
    }

}