package by.epam.like_it.service.validator.impl.content_validator;

import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class EntityValidatorTest {

    private static final EntityValidator validator = EntityValidator.getInstance();
    @Test
    public void isValidForCreate() throws Exception {

        User user = new User();
        user.setLogin("lo");
        try {
            validator.isValidForUpdate(user);
        } catch (ValidationInfoException e){
            System.out.println(e);
            InvalidInfo invalidInfo = e.getInvalidInfo();
            List<String> failedFields = invalidInfo.getFailedFields();
            assertTrue(failedFields.contains("login"));
        }
//        User user1 = new User();
//        user1.setLogin("login");
//        user1.s
//        try {
//
//        }


    }

    @Test
    public void isValidForUpdate1() throws Exception {

    }

    @Test
    public void validateOneStringField() throws Exception {

    }

    @Test
    public void validateOneNumberField() throws Exception {
        boolean languageId1 = validator.validateOneNumberField(User.class.getSimpleName(), "languageId", 1);
        assertTrue(languageId1);

        boolean languageId2 = validator.validateOneNumberField(User.class.getSimpleName(), "languageId", 2);
        assertTrue(languageId2);
    }

    @Test
    public void validateId() throws Exception {

    }

    @Test
    public void isValidForUpdate() throws Exception {

    }

}