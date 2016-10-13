package by.epam.like_it.service.impl.client;

import by.epam.like_it.dao.FavoritePostDao;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.Answer;
import by.epam.like_it.model.bean.FavoriteUserPost;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.vo.db_vo.AnswerVO;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.service.ClientService;
import by.epam.like_it.service.ServiceFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;


public class ClientServiceImplTest {


    public static ClientService SERVICE = ServiceFactory.getInstance().getClientService();
    private static FavoritePostDao favPostDao;
    private static Connection connection;
    private static ConnectionFactory connectionFactory;


    private static Long lastId;

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException {
        connectionFactory = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        connection = connectionFactory.takeConnectionWithoutCommit();
        favPostDao = MySqlDaoFactory.getInstance().getFavoritePostDao();

        List<FavoriteUserPost> all = favPostDao.findAll();
        all.sort(new Comparator<FavoriteUserPost>() {
            @Override
            public int compare(FavoriteUserPost o1, FavoriteUserPost o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        int size = all.size();
        lastId = all.get(size - 1).getId();
    }

    @AfterClass
    public static void logout() throws SQLException {
        String refTable = ResourceNavigator.getRefTable(FavoriteUserPost.class);
        String s = "DELETE FROM " + refTable + " WHERE id > " + lastId;
        String autoIncr = "ALTER TABLE " + refTable + " AUTO_INCREMENT " + lastId;
        try (Statement statement = connection.createStatement();){
            statement.executeUpdate(s);
            statement.executeUpdate(autoIncr);
            connection.commit();
        }
        connection.close();
        connectionFactory.dispose();
    }

    @Test
    public void createFavorite() throws Exception {
        FavoriteUserPost post = new FavoriteUserPost();
        post.setUserId(2L);
        post.setPostId(3L);
        post.setId(109L);
        boolean b = SERVICE.deleteFavorite(post);
        System.out.println(b);
    }

    @Test
    public void deleteFavorite() throws Exception {

    }


    @Test
    public void attachFavoriteUserPostToPostVO() throws Exception {
        Post post = new Post();
        post.setId(1L);

        PostVO example = new PostVO();
        example.setPost(post);
        Long user_id = 3L;

        boolean b = SERVICE.attachFavoriteUserPostToPostVO(example, user_id);
        System.out.println(example);

        FavoriteUserPost postExpected = new FavoriteUserPost();
        postExpected.setPostId(1L);
        postExpected.setUserId(3L);
        postExpected.setId(3L);
        assertEquals(postExpected, example.getCurrentUserInfo().getFavoriteUserPost());

        Post post1 = new Post();
        post.setId(3L);
        PostVO hard = new PostVO();

        Long user_id_hard = 2L;

        boolean res = SERVICE.attachFavoriteUserPostToPostVO(hard, user_id_hard);
        PostVO.CurrentUserInfo currentUserInfo = hard.getCurrentUserInfo();
        assertNotNull(currentUserInfo);


    }

    @Test
    public void attachUserMarksToAnswers() throws Exception {
        LinkedHashMap<Long, AnswerVO> map =new LinkedHashMap<>();
        AnswerVO vo = new AnswerVO();

        Answer answer = new Answer();
        answer.setId(28L);
        answer.setPostId(4L);
        vo.setAnswer(answer);
        map.put(28L, vo);

        SERVICE.attachUserMarksToAnswers(map, 2L);
        System.out.println(vo);

    }

}