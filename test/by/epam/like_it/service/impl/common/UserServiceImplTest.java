package by.epam.like_it.service.impl.common;

import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.UserDao;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.PhotoVo;
import by.epam.like_it.model.vo.db_vo.UserVO;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.UserService;
import by.epam.like_it.exception.service.action.ServiceAuthException;
import by.epam.like_it.exception.service.ServiceException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserServiceImplTest {

    @Test
    public void updatePhoto() throws Exception {
//        String path = "D:\\projects\\DAO_v4_without_Criteria\\web\\resources\\user_foto\\Circled_User_Male-50.png";
//        String ex = "D:\\projects\\DAO_v4_without_Criteria\\web\\resources\\user_foto";
        String path = "test\\source\\test_photo.jpg";
        User user = new User();
        user.setId(2L);

        PhotoVo vo = new PhotoVo();
        vo.setPhotoName("test_photo.jpg");
        vo.setUser(user);
        vo.setRealPath("D:\\projects\\DAO_v4_without_Criteria\\web\\");
        vo.setPrevPhoto("/resources/user_foto/2.png");

        Path pathExample = Paths.get(path);
        byte[] data = Files.readAllBytes(pathExample);

        vo.setNewPhoto(data);
        service.updatePhoto(vo);

        UserVO userProfile = service.getUserProfile(2L);
        String fotoPath = userProfile.getUser().getFotoPath();
        String pathExpected = "/resources/user_foto/2.jpg";
        assertEquals(pathExpected, fotoPath);


//        vo.setPhotoName("test_photo.png");
//        vo.setPrevPhoto(fotoPath);
//        service.updatePhoto(vo);


    }


    private static UserService service;
    private static User userTestedWithBan;
    private static User userTestedWithoutBan;
    private static User userToUpdate;
    private static int initialSizeOftable;
    private static int lastInitialId;

    private static Connection connection;
    private static UserDao dao;

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException, SQLException {
        connection = ConnectionFactoryFactory.getInstance().getConnectionFactory().takeConnectionWithoutCommit();
        dao = MySqlDaoFactory.getInstance().getUserDao();
        service = ServiceFactory.getInstance().getUserService();
        String initial = "SELECT * from users";
        try(Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(initial)){
            set.last();
            lastInitialId = set.getInt(1);
        }
    }

    @Test
    public void authorise() throws Exception {
        User user = new User();
        user.setLogin("lara");
        user.setPassword("123456");
        try {
            User authorise = service.authorise(user);
        } catch (ServiceAuthException e){
            String failedField = e.getErrorInfo().getFailedField();
            assertEquals(failedField, "banned");
            System.out.println(e);
        }

    }
    @Test
    public void registerValid(){

    }

    @Test
    public void register() throws ServiceException {
        String testCreateLogin = "testLogin";
        try{
            User user = new User();
            user.setLogin(testCreateLogin);
            user.setPassword("1");
            user.setEmail("email");
            try {
                User register = service.register(user);
            }catch (ValidationInfoException e){
                assertTrue("Exception trown" , true);
                List<String> failedField = e.getInvalidInfo().getFailedFields();
                List<String> list = Arrays.asList( "password", "email");
                assertEquals(failedField, list);
            }
            user.setPassword("123456");
            user.setEmail("myEmail@mail.ru");
            try{
                User register = service.register(user);
                assertEquals(register.getBanned(), Boolean.FALSE);
            } catch (ValidationInfoException e){
                e.printStackTrace();
            }
        } finally {
            try(Statement statement = connection.createStatement()){
                statement.executeUpdate(" DELETE FROM users WHERE login = '" + testCreateLogin + "'");
                statement.executeUpdate("ALTER TABLE `LIKE_IT`.`users` " +
                        " AUTO_INCREMENT = " + (lastInitialId + 1));
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void getUserProfile() throws Exception {
        UserVO userProfile = service.getUserProfile(2L);
        System.out.println(userProfile);
    }


}