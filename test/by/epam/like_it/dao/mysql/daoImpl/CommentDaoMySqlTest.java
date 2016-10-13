package by.epam.like_it.dao.mysql.daoImpl;

import by.epam.like_it.dao.CommentDao;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.Comment;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;
import by.epam.like_it.model.vo.db_vo.CommentVO;
import org.apache.taglibs.standard.tag.common.sql.QueryTagSupport;
import org.junit.BeforeClass;
import org.junit.Test;
import testUtil.missalenious.QueryTestUtil;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class CommentDaoMySqlTest {

    private static CommentDao dao;
    private static Connection connection;
    private static ConnectionFactory connectionFactory;
    private static long lastId;

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException {
        connectionFactory = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        connection = connectionFactory.takeConnectionWithoutCommit();
        dao = MySqlDaoFactory.getInstance().getCommentDao();
        lastId  = QueryTestUtil.getLastId(dao).longValue();

    }
    @Test
    public void findAllCommentsVO() throws PersistenceException {
        Criteria<Comment> criteria = new Criteria<>();
        List results = Arrays.asList("3", "2", "28", "1");
        Set<String> comments = new HashSet<>(results);

        EqConstriction<Comment, String> constriction =
                new EqConstriction<Comment, String>(Comment.class, "answerId",comments);
        criteria.putConstriction(constriction);

        List<CommentVO> allCommentsVO = dao.findAllCommentsVO(criteria);
        for (CommentVO commentVO : allCommentsVO){
            Long answerId = commentVO.getComment().getAnswerId();
            assertTrue("all comments belong to assigned range", comments.contains(answerId.toString()));
            System.out.println(commentVO.getComment());
            System.out.println(commentVO.getAuthor());
        }
        List<Long> expectedSecondComments = Arrays.asList(57L, 2L, 3L);
        List<Long> idsOfCommentBelongTo2 = allCommentsVO.stream().filter(s -> s.getComment().getAnswerId().equals(2L))
                                          .map(s -> s.getComment().getId()).collect(Collectors.toList());

        assertEquals(expectedSecondComments, idsOfCommentBelongTo2);

    }
}