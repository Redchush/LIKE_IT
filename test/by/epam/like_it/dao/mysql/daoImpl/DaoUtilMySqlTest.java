package by.epam.like_it.dao.mysql.daoImpl;

import by.epam.like_it.dao.DaoUtil;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.util_interface.Entity;
import by.epam.like_it.service.ServiceFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class DaoUtilMySqlTest {

    @Test
    public void updateByObject() throws Exception {
        Post expected = new Post();
        expected.setId(105L);
        expected.setContent("test test test");
        DaoUtilMySql instance = DaoUtilMySql.getInstance();
        boolean b = instance.updateByObject(expected);

        Post post1 = new Post();
        post1.setId(105L);
        List<Entity> entityByEntity = instance.findEntityByEntity(post1);
        Post real = (Post) entityByEntity.get(0);
        assertEquals(expected.getContent(), real.getContent());
    }

    @Test
    public void findEntityByEntity() throws Exception {

        Post post = new Post();
        post.setUserId(2L);
        DaoUtilMySql instance = DaoUtilMySql.getInstance();
        List<Entity> entityByEntity = instance.findEntityByEntity(post);
        System.out.println(entityByEntity);
    }

}