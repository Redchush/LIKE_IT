package by.epam.like_it.common_util;

import by.epam.like_it.dao.AbstractRealEntityDao;
import by.epam.like_it.model.bean.Comment;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class ReflectionUtilTest {

    private Comment tested;

    @Before
    public void setUp() throws Exception {
        Comment comment = new Comment();
        comment.setId(3L);
        comment.setUserId(4L);
        comment.setAnswerId(5L);
        this.tested = comment;
    }
    @Test
    public void isClassMaintainInterfaceProductivity() throws Exception {
        long start = System.currentTimeMillis();
        boolean classMaintainInterface = ReflectionUtil.isClassMaintainInterface(Comment.class, DeletableByBan.class);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        assertTrue(classMaintainInterface);
    }

    @Test
    public void isClassMaintainInterface2() throws Exception {
        boolean classMaintainInterface = ReflectionUtil.isClassMaintainInterface(Post.class, DeletableByBan.class);
        assertTrue(classMaintainInterface);
        boolean realEntityInf = ReflectionUtil.isClassMaintainInterface(Post.class, RealEntity.class);
        assertTrue(realEntityInf);
        boolean realEntityInf2 = ReflectionUtil.isClassMaintainInterface(Tag.class, RealEntity.class);
        assertTrue(realEntityInf2);
        boolean checkNot = ReflectionUtil.isClassMaintainInterface(Post.class, AbstractRealEntityDao.class);
        assertFalse(checkNot);
    }

    @Test
    public void getNotNullFields() throws Exception {
        List<String> expected = Arrays.asList("id", "userId", "answerId");
        List<String> notNullFields = ReflectionUtil.getNotNullFieldsList(tested);
        assertEquals(expected, notNullFields);
    }

    @Test
    public void getNotNullFieldsExceptId() throws Exception {
        List<String> expected = Arrays.asList("userId", "answerId");
        List<String> notNullFields = ReflectionUtil.getNotNullFieldsExceptIdList(tested);
        assertEquals(expected, notNullFields);
    }

    @Test
    public void isIdField() throws Exception {

    }



    @Test
    public void isClassMaintainInterface() throws Exception {

    }

    @Test
    public void containsField() throws Exception {

    }

    @Test
    public void name() throws Exception {


    }
}