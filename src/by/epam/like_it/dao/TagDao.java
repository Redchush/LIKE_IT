package by.epam.like_it.dao;


import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.Tag;

import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.facade.PostTagCriteria;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.model.vo.db_vo.TagVO;

import java.util.List;

public abstract class TagDao extends AbstractRealEntityDao<Tag>{


    /**
     * Find all tags by criteria object. Return result wrapped in object ListCounterResponse,
     * in which total - count of user's tags,
     *          items - list of TagVO with Tag object and info about how many posts marked by this tag
     * @param definition - definition for search
     * @return ListCounterResponse object. In case of absence result ListCounterResponse will contains 0 as total and
     * empty list as list of TagVO. Never return null.
     * @throws PersistenceSystemException - in case operation failure because of internal reasons
     */
    public abstract List<TagVO> findTagWithInfo(PostTagCriteria definition)
            throws PersistenceSystemException;

    public abstract ListCounterResponse<TagVO> getPostTagsResponse(PostTagCriteria definition) throws
                                                                                               PersistenceSystemException;

    public abstract ListCounterResponse<Tag> getUserTagsResponse(Long userId, Criteria<Tag> definition) throws
                                                                                                        PersistenceSystemException;

    public abstract Long getInitialCount() throws PersistenceSystemException;

    /**
     * Find all tags on which user was subscribed. Return result wrapped in object ListCounterResponse,
     * in which total - count of user's tags,
     *          items - list of TagVO with Tag object and info about how many posts marked by this tag
     * @param userId - id of user who is possessor of tag
     * @param definition - definition for search
     * @return ListCounterResponse object. In case of absence result ListCounterResponse will contains 0 as total and
     * empty list as list of TagVO. Never return null.
     * @throws PersistenceSystemException - in case operation failure because of internal reasons
     */
    public abstract ListCounterResponse<TagVO> getUserTagsResponseWithInfo
            (Long userId, PostTagCriteria definition) throws PersistenceSystemException;

    public abstract boolean createTagAndPostTag(List<Tag> tags, Long userId )
            throws PersistenceSystemException, PersistenceNotUniqueException;
}
