package by.epam.like_it.dao.mysql.collector.order_based;

import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class ReflectionBasedCollectorTest {

    private static ConnectionFactory connectionFactory = null;
    private static Connection connection = null;

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException {
        connectionFactory = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        connection = connectionFactory.takeConnectionWithoutCommit();
    }

    @AfterClass
    public static void logout() throws ConnectionPoolException, PersistenceException {
        connectionFactory.dispose();
    }

//    @Test
//    public void collectEntity() throws Exception {
//        String selectAll = QueryMaker.getSelectAll(User.class);
//        Criteria definition = new Criteria();
////        definition.setStart(0);
////        definition.setCount(20);
//
//        String s = QueryMaker.appendLimit(definition, selectAll);
//        System.out.println(s);
//        List<User> users = new ArrayList<>();
//        try(PreparedStatement preparedStatement = connection.prepareStatement(s);
//            ResultSet set = preparedStatement.executeQuery();){
//            User user = new User();
//           while (set.next()){
//               ReflectionBasedCollector<User> collector = new ReflectionBasedCollector<>();
//               User user1 = collector.collectEntity(set, 0, "", user);
//               users.add(user);
//           }
//        }
//        System.out.println(users);
//        assertEquals(users.size(), 20);
//    }

    @Test
    public void collectEntity1() throws Exception {

    }

    @Test
    public void collectAllEntities() throws Exception {

    }

    public void correctAssignment() throws IllegalAccessException, InstantiationException {
        User user = new User();
        user.setId(1L);
        user.setRoleId((byte) 2);
        user.setLanguageId((byte) 2);
        user.setLogin("login");
        user.setPassword("password");
        user.setEmail("email");

        User user1 = User.class.newInstance();
        Field[] declaredFields = User.class.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(s->s.setAccessible(true));

        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            Object object = field.get(user);
            field.set(user1, object);
        }
        Arrays.stream(declaredFields).forEach(s->s.setAccessible(false));
        System.out.println(user);
        System.out.println(user1);
        assertEquals(user, user1);
    }


}