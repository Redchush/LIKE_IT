package by.epam.like_it.dao;


import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.FavoriteUserTag;

import java.util.Collection;

public abstract class FavoriteUserTagDao extends AbstractEntityDao<FavoriteUserTag> {

    public abstract boolean delete(Long userId, Collection<Long> tagIds) throws PersistenceSystemException;

}
