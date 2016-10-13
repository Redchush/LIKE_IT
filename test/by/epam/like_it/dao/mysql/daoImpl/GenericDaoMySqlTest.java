package by.epam.like_it.dao.mysql.daoImpl;

import by.epam.like_it.dao.FavoritePostDao;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.RatingDao;
import by.epam.like_it.model.bean.FavoriteUserPost;
import by.epam.like_it.model.bean.Rating;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class GenericDaoMySqlTest {
    @Test
    public void delete() throws Exception {

    }

    @Test
    public void findEntityByEntity() throws Exception {

    }

    @Test
    public void findAll() throws Exception {
        MySqlDaoFactory factory = MySqlDaoFactory.getInstance();
        RatingDao ratingDao = factory.getRatingDao();
        List<Rating> all = ratingDao.findAll();
    }

}