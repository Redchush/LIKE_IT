package by.epam.like_it.dao.mysql.daoImpl.realEntityDao;

import by.epam.like_it.dao.CommentDao;
import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.Comment;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.vo.db_vo.CommentVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class CommentDaoMySql extends CommentDao {

    private static final String COMMENT_VO_SELECT = "SELECT comments.id, comments.user_id, comments.answers_id, comments.content, comments.created_date, comments.updated_date, comments.banned\n" +
            ", users.id, users.role_id, users.login,  users.foto_path, users.banned\n" +
            "FROM comments\n" +
            "LEFT JOIN users \n" +
            "ON users.id = comments.user_id \n" +
            "WHERE comments.banned = FALSE\n" +
            "ORDER BY comments.answers_id, comments.created_date";

    private static final FullStackCollector<CommentVO> collector = new FullStackCollector<CommentVO>() {
        private final int commentCount = ResourceNavigator.getAttrCount("comments");

        @Override
        public CommentVO collectEntity(ResultSet set, int shift, String aliasPrefix, CommentVO instance)
                throws SQLException, PersistenceCollectorException {
            Comment beanComment = COLLECTORS.createEntity(set, 0, new Comment());
            User userComment =COLLECTORS.collectShortUser(set, commentCount, new User());
            return new CommentVO(userComment, beanComment);
        }
    };

    private static CommentDaoMySql instance;

    private CommentDaoMySql(){}

    public static CommentDaoMySql getInstance(){
        if (instance == null)
            synchronized (CommentDaoMySql.class){
                if (instance == null)
                    instance = new CommentDaoMySql();
            }
        return instance;
    }

    @Override
    protected Comment getExampleObject() {
        return new Comment();
    }

    @Override
    public List<CommentVO> findAllCommentsVO(Criteria<Comment> criteria) throws PersistenceSystemException {
        String s = CRITERIA_HANDLER.processCriteria(COMMENT_VO_SELECT, criteria);
        return UTIL_HOLDER.getUtilMySql().findAll(s, new CommentVO(), collector, null, false);
    }

//    private void putInMap(Map<Long, List<CommentVO>> map, CommentVO vo){
//        Long answerId = vo.getComment().getAnswerId();
//        List<CommentVO> commentVOs = map.get(answerId);
//        if (commentVOs == null){
//            map.put(answerId, commentVOs);
//        } else {
//            commentVOs.create(vo);
//        }
//    }

//    List<Map<Long, List<CommentVO>>> wrapper = new ArrayList<>();
//    Map<Long, List<CommentVO>> realResult = new HashMap<>();
//            wrapper.create(realResult);
//            while (set.next()){
//        CommentVO commentVO = new CommentVO();
//        collectEntity(set, 0, aliasPrefix, commentVO);
//        putInMap(realResult, commentVO);
//    }
//            return wrapper;


    //    @Override
//    public Criteria<CommentVO> getCriteria() {
//        return new CriteriaMySql<CommentVO>(CommentVO.class);
//    }
}
