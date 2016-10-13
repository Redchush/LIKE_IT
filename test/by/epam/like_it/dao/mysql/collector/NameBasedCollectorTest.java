package by.epam.like_it.dao.mysql.collector;

import by.epam.like_it.dao.mysql.collector.impl.NameBasedCollector;
import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.model.bean.User;
import org.junit.Test;


public class NameBasedCollectorTest {

    @Test
    public void getCollectorByClass() throws Exception {

        NameBasedCollector<User> collectorByClass = NameBasedCollector.getCollectorByEntity(new User());
        QueryMaker.Select.getSelectById(User.class);

    }

    @Test
    public void collectEntity() throws Exception {

    }

    @Test
    public void reallyCollect() throws Exception {

    }

    @Test
    public void fillStatement() throws Exception {

    }

    @Test
    public void collectEntitySet() throws Exception {

    }

}