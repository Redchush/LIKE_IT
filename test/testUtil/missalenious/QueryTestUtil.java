package testUtil.missalenious;

import by.epam.like_it.dao.AbstractRealEntityDao;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.model.bean.util_interface.RealEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QueryTestUtil {

    public static String DELETE_PATTERN = "DELETE FROM %s WHERE id > %d";
    public static String ALTER_PATTERN = "ALTER TABLE %s AUTO_INCREMENT %d";
    public static String INSERT_TESTED_USERS = "INSERT INTO `LIKE_IT`.`users` (`id`, `role_id`, `language_id`, `login`, `password`, `email`, `last_name`, `first_name`, `about_me`,`foto_path`, `created_date`, `updated_date`, `banned`) \n" +
            "\t\tVALUES " +
            "\n(DEFAULT, 3, DEFAULT, 'testAdmin', '123456', 'testAdmin@mail.ru', NULL, NULL, NULL, NULL, " +
            "DEFAULT, NULL, DEFAULT)," +
            "(DEFAULT, 3, DEFAULT, 'testUser', '123456', 'testUser@mail.ru', NULL, NULL, NULL, NULL,  " +
            "DEFAULT, NULL, DEFAULT);";


    public static String getTestedUser = "SELECT * FROM users WHERE login = 'testUser'";
    public static String getTestedAdmin = "SELECT * FROM users WHERE login = 'testAdmin'";

    public static String getTestedUser_ids = "SELECT id FROM users WHERE login = 'testUser' or login = 'testAdmin' " +
            "ORDER BY login";
    public static String getTestedPost_ids = "SELECT id FROM posts WHERE title LIKE %test";

    /**
     * replace all "\n" on " "
     * replace all doubled " "
     * convert to lower case
     * @param string
     * @return
     */
    public static String normalize(String string){
        String s = string.replaceAll("\n", " ").trim();
        while (s.contains("  ")){
            s = s.replace("  ", " ");
        }
        return s.toLowerCase();
    }

    /**
     * Method for defining the last id in table
     * @param dao - persistence responsible for entity
     * @param <T> - entity for which persistence responsible
     * @return - last id find by findAll() method of persistence
     * @throws PersistenceException
     */
    public static <T extends RealEntity> Number getLastId(AbstractRealEntityDao<T> dao) throws
                                                                                        PersistenceException {
        List<T> all = dao.findAll();
        all.sort(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return (int) (o1.getId().doubleValue() -  o2.getId().doubleValue());
            }
        });
        int size = all.size();
        return all.get(size - 1).getId();
    }

    public static void executeAfter(Connection connection, Class classRequested, long lastId) throws SQLException {

        String refTable = ResourceNavigator.getRefTable(classRequested);
        String deleteQuery= String.format(DELETE_PATTERN, refTable, lastId);
        String alterQuery = String.format(ALTER_PATTERN, refTable, lastId);
        try (Statement statement = connection.createStatement()){
            int i = statement.executeUpdate(deleteQuery);
            int i1 = statement.executeUpdate(alterQuery);
            System.out.println(i + " " + i1);
            connection.commit();
        }
    }

    public static void getGeneratedKeys(List<Long> list, Statement statement) throws SQLException {
        try(ResultSet set = statement.getGeneratedKeys()) {
            Long last_inserted_id = 0L;
            while (set.next()) {
                last_inserted_id = set.getLong(1);
                list.add(last_inserted_id);
            }
        }
    }

    public static void getSelectedIds(List<Long> result, Statement statement) throws SQLException {
        try(ResultSet resultSet = statement.getResultSet()) {
            while (resultSet.next()){
                long aLong = resultSet.getLong(1);
                result.add(aLong);
            }
        }
    }

    public static void executeSearchId(List<Long> result, Connection connection, String query) throws SQLException {
        result.clear();
        try(PreparedStatement statement = connection.prepareStatement(getTestedUser_ids)) {
            statement.execute();
            getSelectedIds(result, statement);
            connection.commit();
        }
    }


    /**
     * return list in which first user id - id of admin, second - id of user;
     * @param connection
     * @return
     * @throws SQLException
     */

    public static List<Long> createTestUsers(Connection connection) throws SQLException {
        ArrayList<Long> result = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(INSERT_TESTED_USERS, Statement.RETURN_GENERATED_KEYS)){
            statement.executeUpdate();
            getGeneratedKeys(result, statement);
            connection.commit();
            return result;
        } catch (SQLException e){
            if (!e.getSQLState().equals("23000")){
                throw e;
            } else {
                System.out.println("This users exist yet");
                executeSearchId(result, connection, getTestedUser_ids);
                return result;
            }
        }
    }

    public static List<Long> createTestPosts(Connection connection) throws SQLException{
        List<Long> testUsers = createTestUsers(connection);
        String insert = "INSERT INTO `LIKE_IT`.`posts` " +
                "(`id`, `user_id`, `category_id`, `title`, `content`, `created_date`, `updated_date`, `banned`) " +
                "VALUES (DEFAULT, ?, 1, 'test user title about key word', " +
                "'test user content about key word'," +
                "DEFAULT, NULL, DEFAULT);";
        ArrayList<Long> result = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            statement.setLong(1, testUsers.get(1));
            statement.executeUpdate();
            getGeneratedKeys(result, statement);
            connection.commit();
            return result;
        } catch (SQLException e){
            if (!e.getSQLState().equals("23000")){
                throw e;
            } else {
                System.out.println("This posts exist yet");
                executeSearchId(result, connection, getTestedPost_ids);
                return result;
            }
        }
    }

    public static List<Long> createTestsComments(Connection connection) throws SQLException{
        List<Long> testUsers = createTestUsers(connection);
        List<Long> testPosts = createTestPosts(connection);

        String insert = "INSERT INTO `LIKE_IT`.`comments` (`id`, `user_id`, `answers_id`, `content`, " +
                "`created_date`, `updated_date`,    `banned`) " +
                "VALUES (DEFAULT, " + testUsers.get(0) + ", " + testPosts.get(0)
                + ", 'test comment', DEFAULT, NULL, DEFAULT);";

        ArrayList<Long> result = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            statement.setLong(1, testPosts.get(1));
            statement.executeUpdate();
            getGeneratedKeys(result, statement);
            connection.commit();
            return result;
        } catch (SQLException e){
            if (!e.getSQLState().equals("23000")){
                throw e;
            } else {
                System.out.println("This comments exist yet");
                executeSearchId(result, connection, getTestedPost_ids);
                return result;
            }
        }
    }



    public static void deleteTestedEntities(){

    }

    public static void main(String[] args) throws ConnectionPoolException, SQLException {
        List<Long> testUsers = createTestPosts(
                ConnectionFactoryFactory.getInstance().getConnectionFactory().takeConnectionWithoutCommit());
        System.out.println(testUsers);
    }

}
