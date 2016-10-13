package by.epam.like_it.dao;


import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.PostTag;

import java.util.List;

public abstract class PostTagDao extends AbstractEntityDao<PostTag>{

    public abstract boolean delete(Long postTag, List<Long> tagIds) throws PersistenceSystemException;
}
