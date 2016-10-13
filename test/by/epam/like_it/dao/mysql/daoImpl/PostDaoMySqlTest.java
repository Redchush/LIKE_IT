package by.epam.like_it.dao.mysql.daoImpl;

import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.PostDao;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.dao.mysql.daoImpl.realEntityDao.PostDaoMySql;
import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.Category;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.PostTag;
import by.epam.like_it.model.bean.Tag;

import by.epam.like_it.model.criteria_to.core.Limit;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.model.vo.db_vo.PostVO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import testUtil.missalenious.QueryTestUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class PostDaoMySqlTest {


    private static Category categoryTested;
    private static Category categoryWithBan;

    private static ConnectionFactory FACTORY;
    private static Connection CONNECTION;
    private static PostDao DAO;
    private static long initialSize;

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException, SQLException {
        FACTORY = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        CONNECTION = FACTORY.takeConnectionWithoutCommit();
        DAO = MySqlDaoFactory.getInstance().getPostDao();
        initialSize = QueryTestUtil.getLastId(DAO).longValue();
        prepareEntities();
    }

    private static void prepareEntities(){
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 9, 29, 0,25, 6);
        cal.set(Calendar.MILLISECOND, 0);
        Timestamp createdDate = new Timestamp(cal.getTimeInMillis());
        Category category = new Category(1L, "java наше все", null, "паблик о java", null, createdDate, true);
        categoryTested = category;
        categoryWithBan = new Category(1L, "java наше все", null, "паблик о java", null, createdDate, false);
    }

    @AfterClass
    public static void logOut() throws SQLException {
        CONNECTION.close();
        FACTORY.dispose();
    }

    /**
     * use dump_v7_1
     * @throws Exception
     */
    @Test
    public void getPostsToWithInfo() throws Exception {
        InitialPostCriteria definition = new InitialPostCriteria();
        definition.setLimit(new Limit(0, 10));
        List<PostVO> postsToWithInfo = DAO.getPostsVo(definition);
        assertEquals(10, postsToWithInfo.size());
        assertTrue(postsToWithInfo.get(0).getInfo().getReadedCount() >= postsToWithInfo.get(1).getInfo().getReadedCount());
        System.out.println(postsToWithInfo);
        System.out.println(postsToWithInfo.stream().map(PostVO::getCategory).collect(Collectors.toList()));
    }

    @Test
    public void testCounters() throws Exception {
        Field fields = PostDaoMySql.class.getDeclaredField("INITIAL_SELECT");
        fields.setAccessible(true);
        String query = (String) fields.get(DAO);
        System.out.println(query);
         final int postCount = ResourceNavigator.getAttrCount(Post.class);
         final int catCount = ResourceNavigator.getAttrCount(Category.class);
         final int postTagCount = ResourceNavigator.getAttrCount(PostTag.class);
         final int tagCount = ResourceNavigator.getAttrCount(Tag.class);
         final int tagPosition = postCount + catCount;
         try(Statement statement = CONNECTION.createStatement()){
            try(ResultSet set = statement.executeQuery(query)){
                ResultSetMetaData metaData = set.getMetaData();
                int columnCount = metaData.getColumnCount();
                assertEquals(25, columnCount);
                int i = tagPosition + tagCount + 3;//2 from counts columns
                assertEquals(20, i);
                set.next();
            }
        }
    }

    /**
     * use dump_v7_1
     * @throws Exception
     */
    @Test
    public void getPostsToWithCountAndInfo() throws Exception {
        InitialPostCriteria definition = new InitialPostCriteria();
        definition.setLimit(new Limit(0, 10));
        ListCounterResponse<PostVO> postsToWithCountAndInfo = DAO.getPostsVoListCounter(definition);
        List<PostVO> items = postsToWithCountAndInfo.getItems();
        Long total = postsToWithCountAndInfo.getTotal();
        assertEquals(10, items.size());
        assertEquals(80L, (long) total);
    }

    @Test
    public void getSinglePostWithInfo() throws Exception {
        Long testedId = 4L;
        Long authorExpected = 3L;
        PostVO vo = DAO.getSinglePostVo(testedId);
        Long id = vo.getPost().getId();
        System.out.println(vo);
        assertEquals(testedId, id);
        assertEquals(authorExpected, vo.getAuthor().getId());

        PostVO vo2 = DAO.getSinglePostVo(3L);
        System.out.println(vo2);
    }

    @Test
    public void getSinglePostVo() throws Exception {
        ListCounterResponse<PostVO> result = DAO.getFavoritePostVoListCounter(2L);
        List<PostVO> items = result.getItems();
        for (PostVO vo:items){
            System.out.println(vo);
        }

    }
}