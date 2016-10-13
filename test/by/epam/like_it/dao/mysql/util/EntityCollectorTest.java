package by.epam.like_it.dao.mysql.util;

import by.epam.like_it.dao.TagDao;
import by.epam.like_it.dao.UserDao;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntityCollectorTest {


    private static Connection connection;
    private static UserDao userDao;
    private static TagDao tagDao;
    private static Statement statement;
    private static ResultSet set;

    @BeforeClass
    public static void login() throws PersistenceException, ConnectionPoolException {

        connection = ConnectionFactoryFactory.getInstance().getConnectionFactory().takeConnectionWithoutCommit();
//        pool = null;
//        connection = null;
//        userDao = MySqlDaoFactory.getInstance().getDaoByClass(UserTO.class);
//        tagDao = MySqlDaoFactory.getInstance().getDaoByClass(TagTO.class);
    }

    @After
    public void tearDown() throws Exception {
        if (set != null) {
            set.close();
        }
        if (statement != null) {
            statement.close();
        }

    }


    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
    }

//    @Test
//    public void ivoke() throws Exception {
//
//        CriteriaMySql<UserTO> criteriaMySql = new CriteriaMySql<UserTO>(UserTO.class);
//        criteriaMySql.addEq(UserCriteria._$BANNED, "false");
//        criteriaMySql.addEq(UserCriteria._$BANNED, "true");
//        criteriaMySql.addEq(UserCriteria._$EMAIL, "lara@mail.ru");
//        criteriaMySql.addEq(UserCriteria._$LOGIN, "lara");
//        criteriaMySql.addEq(UserCriteria._$FIRST_NAME, "");
//        criteriaMySql.addEq(UserCriteria.ANSWERS$USER_ID, "1");
//        criteriaMySql.addLimit(20);
//        String query = QueryMaker.getSelectAll(UserTO.class, criteriaMySql);
//
//        Statement statement = connection.createStatement();
//        ResultSet set = statement.executeQuery(query);
//        System.out.println(query);
//
//        List<UserTO> list = new ArrayList<>();
//        BeanCollectors<UserTO> COLLECTORS = new BeanCollectors<>();
//        list = COLLECTORS.ivoke(set, criteriaMySql);
//        int answerIdExpected = 2;
//        assertEquals(answerIdExpected, list.get(0).getAnswers().get(0).getId());
//        //     list.stream().forEach(System.out::println);
//        //    System.out.println(ivoke);
//    }


//    @Test
//    public void ivokeSecond() throws Exception {
//
//        CriteriaMySql criteriaMySql = new CriteriaMySql<TagTO>(TagTO.class);
//        criteriaMySql.addEq(TagCriteria.CATEGORIES_TAGS$TAG_ID, "1");
//        String query = QueryMaker.getSelectAll(TagTO.class, criteriaMySql);
//        Statement statement = connection.createStatement();
//        ResultSet set = statement.executeQuery(query);
//        //  System.out.println(query);
//        BeanCollectors COLLECTORS = new BeanCollectors<>();
//        List<TagTO> list = new ArrayList<>();
//        String title = "java наше все";
//        int id = 1;
//        list = COLLECTORS.ivoke(set, criteriaMySql);
////        list.stream().forEach(System.out::println);
////        System.out.println(list);
//        assertEquals(title, list.get(0).getCategoryTag().get(0).getTitle());
//        assertEquals(id, list.get(0).getCategoryTag().get(0).getId());
//    }


//    @Test
//    public void attachDependant() throws Exception {
//        BeanCollectors<CategoryContent> COLLECTORS = new BeanCollectors<>();
//        Method attachDependant =
//                COLLECTORS.getClass().getDeclaredMethod("attachDependant", Object.class, Object.class, String.class,
//                        Class.class);
//        //   System.out.println(attachDependant);
//        attachDependant.setAccessible(true);
//        CategoryContent cat = new CategoryContent(1);
//        TagTO tag = new TagTO(2, "java");
//        List<TagTO> tags = new ArrayList<>();
//        tags.createList(tag);
//        attachDependant.invoke(COLLECTORS, cat, tag, "categories_tags", TagTO.class);
//
//        assertEquals(cat.getTags(), tags);
//        tags.clear();
//
//        //on objects
//        Object catObj = new CategoryContent(1);
//        Object tagObj = new TagTO(2, "java");
//        attachDependant.invoke(COLLECTORS, catObj, tagObj, "categories_tags", TagTO.class);
//
//        tags.createList((TagTO) tagObj);
//        assertEquals(((CategoryContent) catObj).getTags(), tags);
//        attachDependant.setAccessible(false);
//
//    }

//    @Test
//    public void methodSeach() throws Exception {
//        CriteriaMySql<AnswerVO> criteriaMySql = new CriteriaMySql<>(AnswerVO.class);
//        System.out.println("createAnswerList".equals("createAnswerList"));
//        System.out.println(Arrays.toString(BeanCollectors.class.getMethods()));
//
//        criteriaMySql.createList(Clause.limit(AnswerCriteria.POST_SEARCH, 20));
//        criteriaMySql.getString();
//
//        //   System.out.println(BeanCollectors.class.getMethod("root.persistence.mysql.testUtil.BeanCollectors.createRoleList"));
//
//        new BeanCollectors().ivoke(set, criteriaMySql);
//
//    }


}