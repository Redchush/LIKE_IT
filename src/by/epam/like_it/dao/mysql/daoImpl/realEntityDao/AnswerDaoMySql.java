package by.epam.like_it.dao.mysql.daoImpl.realEntityDao;

import by.epam.like_it.dao.AnswerDao;
import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.Answer;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.AnswerVO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;


public class AnswerDaoMySql extends AnswerDao{

    private static final String ANSWER_VO_SELECT  = "SELECT answers.id, answers.user_id, answers.post_id, answers.content, answers.created_date, answers.updated_date, answers.banned\n" +
            ", users.id, users.role_id, users.login, users.foto_path, users.banned\n" +
            ", AVG(rating.rating) AS avg__rating__rating\n" +
            ", SUM(rating.rating)\n" +
            ", COUNT(rating.rating)  \n" +
            "FROM answers\n" +
            "LEFT JOIN users \n" +
            " ON users.id = answers.user_id \n" +
            "LEFT JOIN rating\n" +
            " ON answers.id = rating.answer_id AND rating.banned = FALSE\n" +
            "WHERE answers.banned = FALSE AND answers.post_id = ? \n" +
            "GROUP BY answers.id\n" +
            "ORDER BY avg__rating__rating DESC";

    private static FullStackCollector<AnswerVO> COLLECTOR_VO = new FullStackCollector<AnswerVO>() {
        private final int answersCount = ResourceNavigator.getAttrCount("answers");
        private final int fieldOfUserShort = COLLECTORS.getShortUserNum();

        @Override
        public AnswerVO collectEntity(ResultSet set, int shift, String aliasPrefix, AnswerVO instance)
                throws SQLException, PersistenceCollectorException {
            int counter = shift;
            AnswerVO vo = new AnswerVO();

            Answer answer = COLLECTORS.createEntity(set, counter, new Answer());
            counter +=answersCount;

            User user = COLLECTORS.collectShortUser(set, counter, new User());
            counter +=fieldOfUserShort + 1;

            Double avgRating = set.getDouble(counter);
            Long commonRating = set.getLong(++counter);
            Long countRating = set.getLong(++counter);
            vo.setAnswer(answer);
            vo.setAuthor(user);
            vo.setAvgRate(avgRating);
            vo.setTotalRate(commonRating);
            vo.setCountRatings(countRating);
            return vo;
        }

        @Override
        public int fillStatement(PreparedStatement statement, int from, Supplier supplier)
                throws SQLException, PersistenceCollectorException {
            Long id = (Long) supplier.get();
            statement.setLong(1, id);
            return 1;
        }
    };

    private static AnswerDaoMySql instance;

    private AnswerDaoMySql(){}

    public static AnswerDaoMySql getInstance(){

        if (instance == null)
            synchronized (AnswerDaoMySql.class){
                if (instance == null)
                    instance = new AnswerDaoMySql();
            }
        return instance;
    }

    @Override
    protected Answer getExampleObject() {return new Answer();}

    /***
     *
     * @param id
     * @return List<AnswerVO>. In case of absence any entity -> return empty list. Never return null
     * @throws PersistenceSystemException - if som internal problem occured
     */
    @Override
    public List<AnswerVO> getAnswerVOByPostId(Long id) throws PersistenceSystemException {
        return UTIL_HOLDER.getUtilMySql().findAll(ANSWER_VO_SELECT, new AnswerVO(), COLLECTOR_VO, ()-> id, true);
    }
}


