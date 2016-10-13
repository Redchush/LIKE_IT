package by.epam.like_it.service.validator.impl.content_validator;


import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.adapter.PostContent;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class ContentValidatorTest {

    private static ContentValidator validator = ContentValidator.getInstance();


    @Test
    public void validateFieldsOnNullTest() throws Exception {
        Method validateFieldsOnNull =
                validator.getClass().getDeclaredMethod("validateFieldsOnNull", Class.class, RealEntity.class);

        Post post = new Post();

        post.setUserId(1L);
        post.initAllDefault();
        List<String> expected = Arrays.asList("title", "content");
        validateFieldsOnNull.setAccessible(true);
        List<String> failedField = null;
        try {
            Object invoke = validateFieldsOnNull.invoke(validator, Post.class, post);
        } catch (InvocationTargetException e){
            ValidationInfoException targetException = (ValidationInfoException) e.getTargetException();
            failedField = targetException.getInvalidInfo().getFailedFields();
            System.out.println(failedField);
            System.out.println(e.getMessage());
            assertEquals(expected, failedField);
        } finally {
            validateFieldsOnNull.setAccessible(false);
        }
        assertNotNull("the exception not trown", failedField);


    }


    @Test
    public void validateFieldsOnNullTest_valid() throws Exception {

        Post post = new Post();
        post.setUserId(1L);

        List<String> list100chars = Collections.nCopies(20, "fives");
        String title = list100chars.stream().collect(Collectors.joining(" "));
        post.setTitle(title);

        List<String> list = Collections.nCopies(40, "fives");
        String content = list.stream().collect(Collectors.joining(" "));
        post.setContent(content);
        post.initAllDefault();

        List<String> failedField = null;
        validator.isValidForCreate(new PostContent(post));
        assertNull("the exception not trown", failedField);

    }

    //    validateFieldsOnNull

}