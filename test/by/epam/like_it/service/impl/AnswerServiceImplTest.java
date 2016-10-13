package by.epam.like_it.service.impl;

import by.epam.like_it.dao.CommentDao;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.AnswerVO;
import by.epam.like_it.service.AnswerService;
import by.epam.like_it.service.ClientService;
import by.epam.like_it.service.ServiceFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class AnswerServiceImplTest {


    private static CommentDao dao;
    private static Connection connection;
    private static ConnectionFactory connectionFactory;
    private static AnswerService ANSWER_SERVICE;

    @BeforeClass
    public static void login() throws ConnectionPoolException, PersistenceException {
        connectionFactory = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        connection = connectionFactory.takeConnectionWithoutCommit();
        dao = MySqlDaoFactory.getInstance().getCommentDao();
        ANSWER_SERVICE = ServiceFactory.getInstance().getAnswerService();

    }

    @Test
    public void getAnswersListByPostId() throws Exception {
        Long testedAnswer = 4L;
        List<Long> expectedAnswersId = Arrays.asList(3L, 2L, 28L, 1L);

        LinkedHashMap<Long, AnswerVO> answersListByPostId = ANSWER_SERVICE.getAnswerVoMapByPostId(testedAnswer);
        System.out.println(answersListByPostId);

        List<Long> collect = answersListByPostId.values().stream().map(s -> s.getAnswer().getId()).collect(Collectors.toList
                ());
        assertEquals(expectedAnswersId, collect);

        AnswerVO aLong = answersListByPostId.get(2L);
        List<Long> expectedSecondComments = Arrays.asList(57L, 2L, 3L);
        List<Long> actualComments =
                aLong.getComments().stream().map(s -> s.getComment().getId()).collect(Collectors.toList());

        assertEquals(expectedSecondComments, actualComments);
        User author = aLong.getAuthor();
        System.out.println(author);

        System.out.println(aLong);

    }

    @Test
    public void attachUserMarksToAnswers() throws Exception {
        Long currentUser = 2L;
        LinkedHashMap<Long, AnswerVO> answersListByPostId = ANSWER_SERVICE.getAnswerVoMapByPostId(3L);
        String collect = answersListByPostId.entrySet().stream().map(s -> s.getKey() + " = " + s.getValue())
                                            .collect(Collectors.joining
                                                    ("\n"));
        System.out.println(collect);
        ClientService clientService = ServiceFactory.getInstance().getClientService();
        clientService.attachUserMarksToAnswers(answersListByPostId, currentUser);

    }

}