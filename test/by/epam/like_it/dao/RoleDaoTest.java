package by.epam.like_it.dao;

import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.dao.mysql.collector.BeanCollectors;
import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.Rating;
import by.epam.like_it.model.bean.Role;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import testUtil.missalenious.QueryTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;


public class RoleDaoTest {

    private static RoleDao DAO;
    private static Connection CONNECTION;
    private static ConnectionFactory FACTORY;
    private static long initialSize;

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException, SQLException {
        FACTORY = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        CONNECTION = FACTORY.takeConnectionWithoutCommit();
        DAO = MySqlDaoFactory.getInstance().getRoleDao();
//        initialSize = QueryTestUtil.getLastId(DAO).longValue();

    }
    @AfterClass
    public static void logout() throws SQLException {
        CONNECTION.close();
        FACTORY.dispose();
    }

    @Test
    public void isLongInStatement() throws SQLException {
        String selectAll = QueryMaker.Select.getSelectById(Role.class);

        try(PreparedStatement preparedStatement = CONNECTION.prepareStatement(selectAll)){
            preparedStatement.setLong(1, 1L);
            ResultSet set = preparedStatement.executeQuery();
            boolean next = set.next();
            System.out.println("next" + next + "preparedStatement: " + preparedStatement);

            Role entity = BeanCollectors.getInstance().createEntity(set, 0, new Role());
            System.out.println(entity);
        }

    }

}