package by.epam.like_it.service.validator.impl.user_validator;

import by.epam.like_it.common_util.ResourceManager;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.User;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;


public class UserValidatorTest {

    class UserValidatorTestClass extends UserValidator{

        @Override
        public void isValidForCreate(User entity) throws ValidationInfoException {

        }
    }

    @Test
    public void validateLogin() throws Exception {
        UserValidatorTestClass testClass = new UserValidatorTestClass();
        boolean kennni = testClass.validateLogin("kennni");
        System.out.println(kennni);
        assertTrue(kennni);

        boolean password = testClass.validatePassword("12345");
        assertFalse(password);
        boolean passwordTrue = testClass.validatePassword("123456");
        assertTrue(passwordTrue);
    }

    @Test
    public void validateEmail() throws Exception {

    }

    @Test
    public void validatePassword() throws Exception {

    }

    @Test
    public void testRegExpInResource(){
        String string = ResourceManager.VALIDATION_INFO.getString("User.email.pattern");
        System.out.println(string);
        Pattern pattern = Pattern.compile(string);
        String typicalEmail = "lara@mail.ru";
        String typical2Email = "kennni@yahoo.com";

        boolean matches = typicalEmail.matches(string);
        assertTrue(matches);
        boolean matches1 = typical2Email.matches(string);
        assertTrue(matches1);

        String typicalWrong= "@mail.ru";
        matches = typicalWrong.matches(string);
        assertFalse(matches);
    }

}