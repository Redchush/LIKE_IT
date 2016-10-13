package by.epam.like_it.service.impl.client;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.FavoritePostDao;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.RatingDao;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.service.action.ServiceDuplicateException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.FavoriteUserPost;
import by.epam.like_it.model.bean.Rating;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;
import by.epam.like_it.model.vo.db_vo.AnswerVO;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.service.ClientService;
import by.epam.like_it.service.validator.impl.content_validator.EntityValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClientServiceImpl implements ClientService{

    private static ClientServiceImpl instance;
    private static MySqlDaoFactory FACTORY = MySqlDaoFactory.getInstance();

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private ClientServiceImpl(){}

    public static ClientServiceImpl getInstance(){

        if (instance == null)
            synchronized (ClientServiceImpl.class){
                if (instance == null)
                    instance = new ClientServiceImpl();
            }
        return instance;
    }


    @Override
    public boolean attachFavoriteUserPostToPostVO(PostVO vo, Long userId) throws ServiceSystemException {
        Long post_id = vo.getPost().getId();
        FavoriteUserPost post = new FavoriteUserPost();
        post.setUserId(userId);
        post.setPostId(post_id);

        FavoritePostDao dao = FACTORY.getFavoritePostDao();
        try {
            List<FavoriteUserPost> entityByEntity = dao.findEntityByEntity(post);
            if (entityByEntity == null || entityByEntity.isEmpty()){
                return false;
            } else {
                PostVO.CurrentUserInfo info = vo.getCurrentUserInfo();
                if (info == null){
                    info = vo.new CurrentUserInfo();
                }
                info.setUserId(userId);
                info.setFavoriteUserPost(entityByEntity.get(0));
                vo.setCurrentUserInfo(info);
                LOGGER.debug("favorite found " + entityByEntity);
            }
        } catch (PersistenceException e) {
            throw new ServiceSystemException();
        }
        return true;
    }

    @Override
    public void attachUserMarksToAnswers(Map<Long, AnswerVO> vos, Long userId) throws ServiceSystemException {
        RatingDao ratingDao = MySqlDaoFactory.getInstance().getRatingDao();
        List<Rating> all;
        try{
            Criteria<Rating> criteria = makeRatingCriteria(vos);
            all = ratingDao.findAllUserRatingsByCriteria(criteria, userId);
            adjustUserRating(all, vos, userId);
        } catch (PersistenceException e) {
            throw new ServiceSystemException("Can't attach rating", e);
        }
    }

    @Override
    public Long createFavorite(FavoriteUserPost post) throws ServiceDuplicateException, ServiceSystemException {
        FavoritePostDao favoritePostDao = FACTORY.getFavoritePostDao();
        Long id = null;
        try {
            id = favoritePostDao.create(post);
            return id;
        } catch (PersistenceNotUniqueException e){
            ServiceDuplicateException serviceDuplicateException = new ServiceDuplicateException();
            serviceDuplicateException.setActionKey("create.favorite");
            throw serviceDuplicateException;
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Can't create favorite", e);
        }
    }

    @Override
    public boolean deleteFavorite(FavoriteUserPost post) throws ServiceSystemException {
        FavoritePostDao favoritePostDao = FACTORY.getFavoritePostDao();
        try {
            return favoritePostDao.delete(post.getId());
        } catch (PersistenceException e) {
            throw new ServiceSystemException("Cant deleteByBanIfPossible favorite");
        }
    }

    @Override
    public boolean toggleRating(Rating rating) throws ServiceSystemException, ValidationInfoException {
        rating.initRequiredDefault();
        EntityValidator instance = EntityValidator.getInstance();
        instance.isValidForCreate(rating);
        RatingDao ratingDao = FACTORY.getRatingDao();
        try{
           return ratingDao.toggleRating(rating);
        } catch (PersistenceSystemException e) {
           throw new ServiceSystemException("Can't update rating");
        }
    }

    private Criteria<Rating> makeRatingCriteria(Map<Long, AnswerVO> answerVOByPostId){
        Criteria<Rating> result = new Criteria<>();
        Set<Long> longs = answerVOByPostId.keySet();
        EqConstriction<Rating, Long> constriction = new EqConstriction<>(Rating.class, "answerId", longs);
        result.putConstriction(constriction);
        return result;
    }

    private void adjustUserRating(List<Rating> ratings, Map<Long, AnswerVO> answerVOMap, Long currentUserId){
        if (ratings == null || answerVOMap == null){
            return;
        }
        for(Rating rating: ratings){
            Long answerId = rating.getAnswerId();
            AnswerVO answerVO = answerVOMap.get(answerId);
            AnswerVO.CurrentUserInfo info = answerVO.new CurrentUserInfo(currentUserId, rating);
            answerVO.setCurrentUserInfo(info);
        }
    }



}
