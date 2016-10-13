package by.epam.like_it.service.impl;

import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.PostDao;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.dao.mysql.daoImpl.realEntityDao.PostDaoMySql;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.service.ServiceException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.adapter.PostContent;
import by.epam.like_it.model.bean.Comment;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import testUtil.missalenious.QueryTestUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;

//*refactored: all methods was sent to EntityService*/
public class ContentServiceImplTest {

    private static PostDao postDao;
    private static Connection connection;
    private static ConnectionFactory connectionFactory;
    private static EntityServiceImpl SERVICE;

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
    public void createContent() throws Exception {

        Post postTested = new Post();
        postTested.setUserId(1L);
        List<String> list100chars = Collections.nCopies(20, "fives");
        String title = list100chars.stream().collect(Collectors.joining(" "));
        postTested.setTitle(title);

        List<String> list = Collections.nCopies(40, "fives");
        String content = list.stream().collect(Collectors.joining(" "));
        postTested.setContent(content);

        long content1 = SERVICE.createContent(new PostContent(postTested));
        Post entityById = postDao.findEntityById(content1);

        postTested.setId(content1);
        postTested.initRequiredDefault();
        postTested.setCategoryId(0L);
        assertEquals(postTested, entityById);

    }


    @Test
    public void updateRealEntity() throws ServiceException {
        Comment comment = new Comment();
        comment.setId(116L);
        comment.setUserId(2L);
        comment.setContent("EditEditEditEditEditEditEditEditEditEditEditEditEditEditEditEditEditEdit");
        SERVICE.updateRealEntity(comment);

    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void expectBrockenValidate() throws ServiceException {
        InvalidInfo info = new InvalidInfo("Comment", Collections.singletonList("content"));
        thrown.expect(ValidationInfoException.class);
        thrown.expect(hasProperty("invalidInfo", equalTo(info)));
        Comment comment = new Comment();
        comment.setId(116L);
        comment.setUserId(2L);
        /*exception expected*/
        comment.setContent("Edi");
        SERVICE.updateRealEntity(comment);
    }


}