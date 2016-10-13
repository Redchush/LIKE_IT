package by.epam.like_it.dao.mysql.daoImpl;

import by.epam.like_it.dao.FavoritePostDao;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.FavoriteUserPost;
import org.junit.*;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;


public class FavoritePostDaoMySqlTest {

    private static FavoritePostDao favoritePostDao;
    private static ConnectionFactory CONNECTION_FACTORY;
    private static Connection connection;
    private int lastInitialId;

    @BeforeClass
    public static void logIn() throws ConnectionPoolException {
        favoritePostDao = MySqlDaoFactory.getInstance().getFavoritePostDao();
        CONNECTION_FACTORY = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
    }

    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
    }

    @Before
    public void setUp() throws Exception {
        String initial = "SELECT * from users";
        try(Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(initial)){
            set.last();
            int anInt = set.getInt(1);
            lastInitialId = anInt;
        }
    }



    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void deleteTest() throws SQLException {
        FavoriteUserPost fav_test = new FavoriteUserPost();
        Long postId = 1L;
        Long userId = 100L;

        long last_inserted_id = 0;
        String selectById = QueryMaker.Delete.getDeleteById(FavoriteUserPost.class);
        String create = QueryMaker.Create.getCreate(FavoriteUserPost.class, true);

        try(PreparedStatement statement = connection.prepareStatement(create, Statement.RETURN_GENERATED_KEYS)){
            statement.setLong(1, userId);
            statement.setLong(2, postId);
            statement.setNull(3, Types.VARCHAR);
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            if(set.next()){
                last_inserted_id = set.getLong(1);
            }
            connection.commit();
        }
        try{
            boolean delete = favoritePostDao.delete(last_inserted_id);
            assertTrue(delete);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Test
    public void createTest() throws PersistenceException, SQLException {
        FavoriteUserPost fav_test = new FavoriteUserPost();
        Long postId = 1L;
        Long userId = 100L;
        fav_test.setUserId(userId);
        fav_test.setPostId(postId);
        Long id_result = 0L;
        try {
            id_result = favoritePostDao.create(fav_test);
            fav_test.setId(id_result);

            FavoriteUserPost entityById = favoritePostDao.findEntityById(id_result);
            assertEquals(fav_test, entityById);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } finally {
            deleteTested(id_result);
        }
    }

    private void deleteTested(Long id) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(QueryMaker.Delete.getDeleteById(FavoriteUserPost
                .class))){
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.executeUpdate("ALTER TABLE `LIKE_IT`.`favorite_users_posts` " +
                    " AUTO_INCREMENT = " + (lastInitialId + 1));
        }
    }

    @Test
    public void findTest() throws PersistenceException {
        FavoriteUserPost post = new FavoriteUserPost();
        Long post_id_tested = 1L;
        post.setPostId(post_id_tested);
        post.setUserId(3L);


        List<FavoriteUserPost> entityByEntity = favoritePostDao.findEntityByEntity(post);
        FavoriteUserPost fav_post = entityByEntity.get(0);

        assertEquals(fav_post.getPostId(), post_id_tested);
        System.out.println(entityByEntity);

        post.setUserId(null);
        List<FavoriteUserPost> entityByEntity1 = favoritePostDao.findEntityByEntity(post);
        assertEquals(entityByEntity1.size(), 2);

    }

}