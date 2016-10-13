package by.epam.like_it.service;


import by.epam.like_it.exception.service.action.ServiceDuplicateException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.FavoriteUserPost;
import by.epam.like_it.model.bean.Rating;
import by.epam.like_it.model.vo.db_vo.AnswerVO;
import by.epam.like_it.model.vo.db_vo.PostVO;

import java.util.Map;


/**
 * Deal with all matters which concerns action of users with client role
 */
public interface ClientService {

    boolean attachFavoriteUserPostToPostVO(PostVO vo, Long userId) throws ServiceSystemException;

    void attachUserMarksToAnswers(Map<Long, AnswerVO> vos, Long userId) throws ServiceSystemException;

    Long createFavorite(FavoriteUserPost post) throws ServiceSystemException, ServiceDuplicateException;

    boolean deleteFavorite(FavoriteUserPost post) throws ServiceSystemException;

    boolean toggleRating(Rating rating) throws ServiceSystemException, ValidationInfoException;
}
