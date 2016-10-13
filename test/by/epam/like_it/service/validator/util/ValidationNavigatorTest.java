package by.epam.like_it.service.validator.util;

import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.validation_vo.info.ValidationInfoLong;
import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;


public class ValidationNavigatorTest {
    @Test
    public void getValidationInfoLong() throws Exception {

    }

    @Test
    public void getValidationMap() throws Exception {

    }

    @Test
    public void getAllValidationInfo() throws Exception {
        Map<String, ValidationInfoLong> allValidationInfo = NAVIGATOR.getValidationMapLong(User.class);
        ValidationInfoLong validationInfo = allValidationInfo.get("login");
        assertEquals(validationInfo.getMin().longValue(), 3L);
    }

    private static final ValidationNavigator NAVIGATOR = ValidationNavigator.getInstance();
    @Test
    public void getPattern() throws Exception {

    }

    @Test
    public void getDefault() throws Exception {
        String aDefault = NAVIGATOR.getDefault("User", "fotoPath");
        System.out.println(aDefault);
    }

}