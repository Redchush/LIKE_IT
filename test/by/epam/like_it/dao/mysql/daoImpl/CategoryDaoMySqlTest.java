package by.epam.like_it.dao.mysql.daoImpl;

import by.epam.like_it.dao.CategoryDao;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.dao.mysql.collector.BeanCollectors;
import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.Category;
import by.epam.like_it.model.vo.db_vo.SimpleCategoryVO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;


public class CategoryDaoMySqlTest {
    @Test
    public void getExampleObject() throws Exception {

    }

    private static Category categoryTested;
    private static Category categoryWithBan;

    private static ConnectionFactory factory;
    private static Connection connection;
    private static CategoryDao dao;
    private static int initialSize;

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException, SQLException {
        factory = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        connection  = factory.takeConnectionWithoutCommit();
        dao = MySqlDaoFactory.getInstance().getCategoryDao();
        prepareEntities();
//        loginNormalise();
    }

    /**
     * dump 7_1
     * @throws Exception
     */
    @Test
    public void getShortCategoriesTree() throws Exception {
        List<SimpleCategoryVO> vos = dao.getShortCategoriesTree();
        show(vos, 0);
        Long firestIdExpected = 3L;
        Long secondIdExpected = 2L;
        assertEquals("firestIdExpected ", firestIdExpected, vos.get(0).getId());
        assertEquals("secondIdExpected ",secondIdExpected, vos.get(1).getId());
        int firstCountSubs = 4;
        int secondCountSubs = 3;
        int secondFirstSubCountSubs = 2;
        assertEquals("firstCountSubs", firstCountSubs, vos.get(0).getSubCategories().size());
        assertEquals("secondCountSubs", secondCountSubs, vos.get(1).getSubCategories().size());
        assertEquals("secondFirstSubCountSubs",secondFirstSubCountSubs, vos.get(1).getSubCategories().get(0)
                                                                         .getSubCategories().size());

    }
    private void show(List<SimpleCategoryVO> result, int level){
        for(SimpleCategoryVO ex: result){
            System.out.println();
            for (int i = 0; i < level; i++) {
                System.out.print("   ");
            }

            System.out.print(ex.getId() + " " + ex.getTitle());
            List<SimpleCategoryVO> subCategories = ex.getSubCategories();
            show(subCategories, level + 1);
        }
    }

    private static void prepareEntities(){
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 9, 29, 00,25,06);
        cal.set(Calendar.MILLISECOND, 0);
        Timestamp createdDate = new Timestamp(cal.getTimeInMillis());
        Category category = new Category(1L, "java наше все", null, "паблик о java", null, createdDate, true);
        categoryTested = category;
        categoryWithBan = new Category(1L, "java наше все", null, "паблик о java", null, createdDate, false);
    }

    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
        factory.dispose();
    }

    @Test
    public void findAllTest() throws ConnectionPoolException, PersistenceException, SQLException {
        List<Category> actual = dao.findAll();
        Category user = actual.get(0);

    }

    @Test
    public void findById() throws ConnectionPoolException, PersistenceException, SQLException {
        Category entityById = dao.findEntityById(categoryTested.getId());
        assertEquals(categoryTested, entityById);
    }

    @Test
    public void deleteTestById() throws PersistenceException, SQLException {
        String checkNoReallyDeleta = "SELECT categories.id, categories.title, categories.language_id, categories.description, categories.parent_category, categories.created_date, categories.published " +
                " FROM `categories`\n WHERE id = 1;";
        Long id = categoryTested.getId();
        try {
            boolean isDelated = dao.delete(id);
            assertTrue(isDelated);
        } finally {
            try(Statement statement = connection.createStatement();
                ResultSet execute = statement.executeQuery(checkNoReallyDeleta)){
                if (!execute.next()){
                    connection.rollback();
                } else {
                    assertTrue(true);
                    Category entity = BeanCollectors.getInstance().createEntity(execute, 0, new Category());
                    assertEquals(categoryWithBan, entity);
                    String query = "UPDATE categories\n" +
                            "SET categories.published = TRUE\n" +
                            "WHERE categories.id =1;";
                    statement.executeQuery(checkNoReallyDeleta);
                }
            }
        }
    }

    @Test
    public void deleteByUserTest() {
        // deleteByBanIfPossible.userByBan

    }

    @Test
    public void create() {

    }

    @Test
    public void update() throws PersistenceException {
        String update = QueryMaker.Update.getUpdateByAllFields(Category.class);
        System.out.println(update);
        String select  = QueryMaker.Select.getSelectAll(Category.class);
        System.out.println(select);
    }

}