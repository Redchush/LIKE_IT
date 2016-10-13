package by.epam.like_it.dao;


import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.Comment;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.vo.db_vo.CommentVO;

import java.util.List;


/**
 * Extracting comment instance from peristalses
 */
public abstract class CommentDao extends AbstractRealEntityDao<Comment>  {

    public abstract List<CommentVO> findAllCommentsVO(Criteria<Comment> criteria) throws PersistenceSystemException ;
}
