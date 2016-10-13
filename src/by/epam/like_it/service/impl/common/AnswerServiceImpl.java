package by.epam.like_it.service.impl.common;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.AnswerDao;
import by.epam.like_it.dao.CommentDao;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.bean.Comment;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;
import by.epam.like_it.model.vo.db_vo.AnswerVO;
import by.epam.like_it.model.vo.db_vo.CommentVO;
import by.epam.like_it.service.AnswerService;
import org.apache.logging.log4j.LogManager;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class AnswerServiceImpl implements AnswerService {

    private static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static MySqlDaoFactory FACTORY = MySqlDaoFactory.getInstance();
    private static AnswerServiceImpl instance;

    private AnswerServiceImpl(){}

    public static AnswerServiceImpl getInstance(){

        if (instance == null)
            synchronized (AnswerServiceImpl.class){
                if (instance == null)
                    instance = new AnswerServiceImpl();
            }
        return instance;
    }

    @Override
    public LinkedHashMap<Long, AnswerVO> getAnswerVoMapByPostId(Long id) throws ServiceSystemException {
        AnswerDao answerDao =FACTORY.getAnswerDao();
        CommentDao commentVO = FACTORY.getCommentDao();
        List<AnswerVO> answerVOByPostId = null;
        try {
            answerVOByPostId = answerDao.getAnswerVOByPostId(id);
            if (answerVOByPostId != null && !answerVOByPostId.isEmpty()) {
                Criteria<Comment> criteria = makeCommentCriteria(answerVOByPostId);
                List<CommentVO> allCommentsVO = commentVO.findAllCommentsVO(criteria);
                adjustCommentsAndNormalize(allCommentsVO, answerVOByPostId);
            }
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Can't collect answers", e);
        }
        return makeMapFromList(answerVOByPostId);
    }



    private LinkedHashMap<Long, AnswerVO> makeMapFromList(List<AnswerVO> answerVOs){
        if (answerVOs == null){
            return new LinkedHashMap<>();
        }
        return answerVOs.stream().collect(Collectors.toMap(s -> s.getAnswer().getId(),
                Function.identity(), (u, v) -> u,
                LinkedHashMap::new));
    }

    private Criteria<Comment> makeCommentCriteria(List<AnswerVO> answerVOByPostId ){
        Criteria<Comment> criteria = new Criteria<>();

        Set<Long> collect = answerVOByPostId.stream()
                                              .map(s -> s.getAnswer().getId())
                                              .collect(Collectors.toSet());
        EqConstriction<Comment, Long> eqs = new EqConstriction<Comment, Long>(Comment.class, "answerId", collect);
        criteria.putConstriction(eqs);
        return criteria;
    }


    private void adjustCommentsAndNormalize(List<CommentVO> commentVOs, List<AnswerVO> answerVOs){
        if ((commentVOs == null) || (answerVOs == null)){
            return;
        }
        answerVOs.forEach(s->{
            s.getAnswer().initAllDefault();
            s.getAuthor().initAllDefault();
        });

        Map<Long, AnswerVO> answerVOMap = makeMapFromList(answerVOs);

        commentVOs.forEach(s->{
            s.getAuthor().initAllDefault();

            Long answerId = s.getComment().getAnswerId();
            AnswerVO answerVO = answerVOMap.get(answerId);
            if (answerVO == null){
                return;
            }

            answerVO.getAuthor().initAllDefault();

            List<CommentVO> comments = answerVO.getComments();
            if (comments != null){
                comments.add(s);
            } else {
                ArrayList<CommentVO> answerComments = new ArrayList<CommentVO>();
                answerComments.add(s);
                answerVO.setComments(answerComments);
            }
        });
    }


}
