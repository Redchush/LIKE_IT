package by.epam.like_it.dao;


import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;

import java.util.List;

public abstract class PostDao extends AbstractRealEntityDao<Post> {

    public abstract List<PostVO> getPostsVo(InitialPostCriteria definition) throws PersistenceSystemException;

    public abstract ListCounterResponse<PostVO> getPostsVoListCounter(InitialPostCriteria definition) throws PersistenceSystemException;

    public abstract PostVO getSinglePostVo(Long id) throws PersistenceSystemException;

    /**
     * Never return null : in case of absence return empty list
     * @param postCriteria - criteria with definite condition for search
     * @return List<Post> that contains the found posts.
     * @throws PersistenceSystemException
     */
    public abstract List<Post> getShortPostByCriteria(Criteria<Post> postCriteria) throws PersistenceSystemException;

    /**
     * @param userId - id of user which favorite posts will be searched
     * @return ListCounterResponse<PostVO> that contains the found posts and their total count
     * Never contains null : in case of absence return empty list
     * @throws PersistenceSystemException
     */
    public abstract ListCounterResponse<PostVO> getFavoritePostVoListCounter(Long userId) throws
                                                                                          PersistenceSystemException;
}
