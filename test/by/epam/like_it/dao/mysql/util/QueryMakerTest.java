package by.epam.like_it.dao.mysql.util;

import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.TagDao;
import by.epam.like_it.dao.UserDao;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.*;
import org.junit.*;
import testUtil.missalenious.QueryTestUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static testUtil.missalenious.QueryTestUtil.normalize;


public class QueryMakerTest {
    @Test
    public void getCreateList1() throws Exception {
        String createList = QueryMaker.Create.getCreateList(Tag.class, 2, true);
        System.out.println(createList);
        String create = QueryMaker.Create.getCreate(Tag.class, true);
        System.out.println(create);
    }

    @Test
    public void getCreateOnDuplicateUpdate() throws Exception {
        String createOnDuplicateUpdate = QueryMaker.Create
                .getCreateOnDuplicateUpdate(Rating.class, "rating", "updatedDate");
        System.out.println(createOnDuplicateUpdate);
    }


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
    public void getCreateList() throws Exception {
        Long initialSize = QueryTestUtil.getLastId(tagDao).longValue();

        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tags.add(new Tag(null, "test_maker_" + i));
        }
        String createList = QueryMaker.Create.getCreateList(Tag.class, tags.size(), true);
        System.out.println(createList);
        try(PreparedStatement stat = connection.prepareStatement(createList)){
            int counter = 1;
            for(Tag tag : tags){
                stat.setString(counter++, tag.getName());
            }
            int ints = stat.executeUpdate();
            System.out.println(ints);
            connection.commit();
            Long initialAfter = QueryTestUtil.getLastId(tagDao).longValue();
            assertEquals(initialSize + tags.size(), (long) initialAfter);
        } finally {
            QueryTestUtil.executeAfter(connection, Tag.class, initialSize);
        }
     }

    @Test
    public void modifyAndCollectByReplacement() throws Exception {
        Long initialSize = QueryTestUtil.getLastId(tagDao).longValue();
        QueryTestUtil.executeAfter(connection, Tag.class, 109);
    }

    @Test
    public void appendLimit() throws Exception {

    }

    @Test
    public void createSelectByObject() throws Exception {
        FavoriteUserPost post = new FavoriteUserPost();
        post.setPostId(1L);
        post.setUserId(3L);
        String selectByObject = QueryMaker.Select.getSelectByObject(post);

        String selectByObjectExp = normalize(selectByObject);
        String eds = "where user_id=? and post_id=?";
        System.out.println(selectByObjectExp);
        assertTrue(selectByObjectExp.endsWith(eds));
        FavoriteUserPost post1 = new FavoriteUserPost();
        String selectByObject1 = QueryMaker.Select.getSelectByObject(post1);
        System.out.println(selectByObject1);

        assertFalse(selectByObject1.contains("WHERE"));


    }

    @Test
    public void surroundWithParenthesis() throws Exception {

    }

    @Test
    public void surroundWithParenthesis1() throws Exception {

    }



    @Test
    public void getSelectById() throws Exception {
        String expected = "DELETE FROM like_it.tags" +
                " WHERE id = ?";
        String queryActual = QueryMaker.Delete.getDeleteById(Tag.class);
        queryActual = normalize(queryActual);
        boolean isEquals = queryActual.equalsIgnoreCase(expected);
        assertTrue(isEquals);
    }


    @Test
    public void getDeleteByBan() throws Exception {
        String queryExp = "UPDATE like_it.users\nset banned = true\nwhere id = ?";
        String queryActual = QueryMaker.Delete.getDeleteByBan(User.class);

        boolean isEqual = queryActual.equalsIgnoreCase(queryExp);
        assertTrue(isEqual);
    }

    @Test
    public void getUpdate() throws Exception {
        String queryActualTag = QueryMaker.Update.getUpdateByAllFields(Tag.class);
        //    System.out.println(queryActualTag);
        String queryExpTag = "UPDATE tags\n" +
                "SET tags.name = ?\n" +
                "WHERE id = 1";
        assertEquals(normalize(queryExpTag), normalize(queryActualTag));

        String queryActual = QueryMaker.Update.getUpdateByAllFields(User.class);
        PreparedStatement statement = connection.prepareStatement(queryActual);
        ParameterMetaData metaData = statement.getParameterMetaData();
        int countActual = metaData.getParameterCount();
        int countExpected = ResourceNavigator.getAttrCount(User.class);

        String queryExp = "UPDATE users\n" +
                "SET id = DEFAULT,  role_id = ?,  login = ?,  password = ?,  email = ?,  last_name = ?,  first_name = ?,  " +
                "created_date = ?,  updated_date = ?,  banned = ?\n" +
                "where id = 1";
        assertEquals(normalize(queryActual),normalize(queryExp));
    }

    @Test
    public void getCreate() throws Exception {
        String query = QueryMaker.Create.getCreate(User.class, true);
        System.out.println(query);

        PreparedStatement st = connection.prepareStatement(query);
        int countExpected = ResourceNavigator.getAttrCount(User.class) -1;
        for (int i = 1; i < countExpected + 1; i++) {
            st.setString(i, "1");
        }
        ParameterMetaData metaData = st.getParameterMetaData();
        int countActual = metaData.getParameterCount();
        assertEquals(countExpected, countActual);


        String queryTag = QueryMaker.Create.getCreate(Tag.class, true);
        PreparedStatement statementTag = connection.prepareStatement(queryTag);
        int countExpectedTags =  ResourceNavigator.getAttrCount(Tag.class) -1;
        for (int i = 1; i < countExpectedTags + 1; i++) {
            statementTag.setString(i, "1");
        }
        ParameterMetaData metaDataTag = statementTag.getParameterMetaData();
        int countActualTags = metaDataTag.getParameterCount();

        assertEquals(countActualTags, countExpectedTags);
    }


//        @Test
//        public void getSelectAllByCriteriaSimple() throws Exception {
//
//            CriteriaMySql<UserTO> criteriaMySql = new CriteriaMySql<UserTO>(UserTO.class);
//            criteriaMySql.addEq(UserCriteria._$BANNED, "false");
//            criteriaMySql.addEq(UserCriteria._$BANNED, "true");
//
//            CriteriaMySql<UserTO> criteriaMySql2 = new CriteriaMySql<UserTO>(UserTO.class);
//            criteriaMySql2.addEq(UserCriteria._$BANNED, "false");
//
//            assertEquals(criteriaMySql.getString(), criteriaMySql2.getString());
//
////with two tables
//            criteriaMySql.addOrder(UserCriteria._$BANNED, ASC);
//            criteriaMySql.addEq(UserCriteria._$EMAIL, "lara@mail.ru");
//            criteriaMySql.addEq(UserCriteria._$LOGIN, "lara");
//            criteriaMySql.addOrder(UserCriteria._$FIRST_NAME, ASC);
//            criteriaMySql.addEq(UserCriteria.ANSWERS$POST_ID, "3");
//            criteriaMySql.addLimit(20);
//
//            String query = QueryMaker.getSelectAll(UserTO.class, criteriaMySql);
//            Statement statement = connection.createStatement();
////        System.out.println(query);
//
//            ResultSet set = statement.executeQuery(query);
//            set.next();
//            String expected = set.getString("users.login");
//            assertEquals(expected, "lara");
//
//
//        }

//        @Test
//        public void getSelectAllByCriteriaComplex() throws Exception {
//
////with related tables
//            CriteriaMySql<UserTO> criteriaRelatedTables = new CriteriaMySql<UserTO>(UserTO.class);
//            criteriaRelatedTables.addEq(UserCriteria._$LOGIN, "lara");
//            criteriaRelatedTables.addEq(UserCriteria.FAVORITE_USER_TAGS$TAGS_ID, "1");
//            String queryRelated = QueryMaker.getSelectAll(UserTO.class, criteriaRelatedTables);
////        Statement statement = connection.createStatement();
//
//            System.out.println(queryRelated);
////        ResultSet set = statement.executeQuery(queryRelated);
//
//
//        }





//        id	role_id	login	password	email	last_name	first_name	created_date	updated_date	banned
//        1	3	lara	m23kujj	lara@mail.ru	\N	\N	2016-06-14 14:33:04	\N	0
//        2	3	kennni	nskofkjal	kenni@yahoo.com	\N	\N	2016-03-15 14:33:04	\N	0
//        3	3	winni	aljdsoifjoi3	winni@mail.ru	\N	\N	2016-01-16 14:33:04	\N	0
}