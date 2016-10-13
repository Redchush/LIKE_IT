package by.epam.like_it.exception.persistence.util;

import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.TagDao;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;


public class ExceptionParserTest {

    private static TagDao dao;
    private static Connection connection;
    private static ConnectionFactory factory;
    private static final String EXCEPTION_EXAMPLE = "MySQLIntegrityConstraintViolationException: Duplicate entry " +
            "'lara@mail.ru' for key 'users.email-UNIQUE'";
    private static final String EXCEPTION_EXAMPLE_STATE = "23000";


    @BeforeClass
    public static void login()
            throws PersistenceException, SQLException, ConnectionPoolException {
        connection = ConnectionFactoryFactory.getInstance()
                                             .getConnectionFactory()
                                             .takeConnectionWithoutCommit();
        dao = MySqlDaoFactory.getInstance().getTagDao();
    }

    @AfterClass
    public static void logout() throws SQLException {
        connection.close();
    }


    @Test
    public void isUniqueConstraintBroken() throws Exception {

    }
    @Test
    public void test(){

        SQLException throwable = new SQLException(EXCEPTION_EXAMPLE, "23000");
        ErrorInfo errorInfo = ExceptionParser.getInstance().parseSQLException(throwable);
        System.out.println(errorInfo);
    }

    @Test
    public void parseSQLException() throws Exception {
        try{
            Tag tagNotExpected = new Tag(10L, "tagToCreate");
            Tag tagToCreate = new Tag();
            tagToCreate.setName("tagToCreate");
            long f = dao.create(tagToCreate);
            long g = dao.create(tagToCreate);
        } catch (PersistenceNotUniqueException e){
            String fieldExpected = "name";
            String tableExpected = "Tag";
            String valueExpected = "tagToCreate";
            ErrorInfo errorInfo = e.getErrorInfo();
            String fieldViolated = errorInfo.getFailedField();
            String tableViolated = errorInfo.getFailedBean();
            String valueViolated = errorInfo.getValueViolated();
            assertEquals(fieldExpected, fieldViolated);
            assertEquals(tableExpected, tableViolated);
            assertEquals(valueExpected, valueViolated);
        }
    }

    @Test
    public void checkRegExp(){
        String typeMsg = "Caused by: com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry 'tagToCreate' for key 'tags.name-UNIQUE'";


    }
    @Test
    public void parse() throws PersistenceException {
        try{

            Tag tagNotExcpected = new Tag(10L, "tagToCreate");
            Tag tagToCreate = new Tag();
            tagToCreate.setName("tagToCreate");
            long f = dao.create(tagToCreate);
            long g = dao.create(tagToCreate);
        } catch (PersistenceNotUniqueException e){
            assertTrue(true);
        }
    }

    private void operate(String message){
        Pattern compile = Pattern.compile("'[\\.a-zA-z_-]*'");
        Matcher matcher = compile.matcher(message);
        String first = "";
        String second = "";
        int counter = 0;
        while (matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            String match = message.substring(start+1, end-1);
            if (counter ==0){
                first = match;
            } else {
                second = match;
            }
            counter++;
        }
        String[] split = second.split("\\.");
        String table = split[0];
        int prefixIndex = split[1].indexOf("-");
        String field = split[1].substring(0, prefixIndex);

    }

}