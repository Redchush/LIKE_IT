package by.epam.like_it.dao.mysql.daoImpl;

import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.TagDao;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.Tag;

import by.epam.like_it.model.criteria_to.core.Order;
import by.epam.like_it.model.criteria_to.facade.PostTagCriteria;
import by.epam.like_it.model.vo.db_vo.TagVO;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class TagDaoMySqlTest {



    private static Connection connection;
    private static  TagDao dao;
    private static int initialSizeOftable = 110;

    private Tag tagToCreate;
    private static Tag tagTested;

    @BeforeClass
    public static void login()
            throws PersistenceException, SQLException, ConnectionPoolException {

        connection =
                ConnectionFactoryFactory.getInstance().getConnectionFactory().takeConnectionWithoutCommit();

        dao = MySqlDaoFactory.getInstance().getTagDao();
        tagTested = new Tag(1L, "java");
    }

    @AfterClass
    public static void logOut() throws SQLException {
        connection.close();
    }

    /**
     * use dump_v7_1
     * @throws Exception
     */
    @Test
    public void findTagWithInfo() throws Exception {
        TagVO leaderInitial = new TagVO();
        leaderInitial.setTag(new Tag(3L, "mysql"));
        TagVO.TagInfo tagInfo = leaderInitial.new TagInfo();
        tagInfo.setCountPostTag(4L);
        leaderInitial.setInfo(tagInfo);

        TagVO leaderAutoComplete = new TagVO();
        leaderAutoComplete.setTag(new Tag(63L, "darnel"));

        TagVO.TagInfo tagInfo1 = leaderAutoComplete.new TagInfo();
        tagInfo.setCountPostTag(4L);
        leaderAutoComplete.setInfo(tagInfo);

        PostTagCriteria definition = new PostTagCriteria();
//        definition.setStart(0);
//        definition.setCount(20);
        definition.setOrder(new Order());
        List<TagVO> tagWithInfo = dao.findTagWithInfo(definition);

        boolean b = leaderAutoComplete.equals(tagWithInfo.get(0)) || leaderInitial.equals(tagWithInfo.get(0));
        assertTrue(b);
        assertEquals(20, tagWithInfo.size());
    }

    @Test
    public void findEntityById() throws ConnectionPoolException, PersistenceException, SQLException {
        Tag tag = dao.findEntityById(1);
        assertEquals(tagTested, tag);
    }

    @Test
    public void findAllTest() throws ConnectionPoolException, PersistenceException, SQLException {
        List<Tag> tags = dao.findAll();
        assertEquals(tags.size(), initialSizeOftable);
    }

    @Test
    public void deleteTestById() throws PersistenceException, SQLException {
        int id = 3;
        boolean boo = dao.delete(id);
        assertTrue(boo);

    }

    @Test
    public void createTest() throws PersistenceException, SQLException {
        try{

        Tag tagNotExcpected = new Tag(10L, "tagToCreate");
        Tag tagToCreate = new Tag();
        tagToCreate.setName("tagToCreate");

        long f = dao.create(tagToCreate);
        Tag tagNotActual =  dao.findEntityById(10);
        assertNotEquals(tagNotExcpected, tagNotActual);
        assertNotEquals(f, 0);
        //    showTableState();
        Tag tagActual = dao.findEntityById(initialSizeOftable + 1);
        Tag tagExcpected = new Tag((long) initialSizeOftable + 1, "tagToCreate");
        assertEquals(tagExcpected, tagActual);
        } finally {
            try(Statement statement = connection.createStatement()){
                statement.executeUpdate(" DELETE FROM tags WHERE NAME = 'tagToCreate';");
                statement.executeUpdate("ALTER TABLE `LIKE_IT`.`tags` " +
                        " AUTO_INCREMENT = " + (initialSizeOftable + 1));
            }
        }
    }


    @Test
    public void update() throws PersistenceException {
        Tag tagExpected = new Tag(2L, "tagToUpdate");
        dao.update(tagExpected);
        Tag tagActual = dao.findEntityById(2);
        assertEquals(tagExpected, tagActual);
    }

    @Test
    public void getUserTagsResponse() throws Exception {
        List<Long> expected = Arrays.asList(1L, 2L, 3L);

        ListCounterResponse<Tag> userTagsResponse = dao.getUserTagsResponse(1L, null);
        List<Tag> items = userTagsResponse.getItems();

        List<Long> collect = items.stream().map(Tag::getId).collect(Collectors.toList());
        for(Long idExp : expected){
            assertTrue(collect.contains(idExp));
        }
    }

    @Test
    public void getUserTagsResponseWithInfo() throws Exception {
        List<Long> expected = Arrays.asList(1L, 2L, 3L);

        ListCounterResponse<TagVO> userTagsResponse = dao.getUserTagsResponseWithInfo(1L, null);
        List<Tag> items = userTagsResponse.getItems().stream().map(TagVO::getTag).collect(Collectors.toList());
        List<Long> collect = items.stream().map(Tag::getId).collect(Collectors.toList());
        for(Long idExp : expected){
            assertTrue(collect.contains(idExp));
        }
        ListCounterResponse<Tag> userTags = dao.getUserTagsResponse(1L, null);
        List<Tag> itemsTag = userTags.getItems();
        assertEquals(itemsTag, items);
    }


    /*
     * not sure that it is "legal" method because of sytemTO.out.printl presents
     */
//    public void showTableState() {
//        String query = "SELECT id, NAME  FROM tags";
//        try (Statement statement = connection.createStatement();
//            ResultSet set = statement.executeQuery(query)) {
//            ResultSetMetaData meta = set.getMetaData();
//            while (set.next()) {
//                int length = meta.getColumnCount();
//                System.out.print("{");
//                for (int i = 1; i <= length; i++) {
//                    System.out.print( set.getObject(i) + " ");
//                }
//                System.out.print("}");
//            }
//        } catch (SQLException e) {
//            System.err.println("exception in show table" );
//        }
//    }

}