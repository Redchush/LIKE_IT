package by.epam.like_it.dao;


import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.Answer;
import by.epam.like_it.model.vo.db_vo.AnswerVO;

import java.util.List;

public abstract class AnswerDao extends AbstractRealEntityDao<Answer> {

   public abstract List<AnswerVO> getAnswerVOByPostId(Long id) throws PersistenceSystemException;

}
