package by.epam.like_it.dao;


import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.Rating;
import by.epam.like_it.model.criteria_to.core.Criteria;

import java.util.List;

public abstract class RatingDao extends AbstractRealEntityDao<Rating>{

    public abstract List<Rating> findAllUserRatingsByCriteria(Criteria<Rating> ratingCriteria, Long user_id)
            throws PersistenceSystemException;

    public abstract boolean toggleRating(Rating entity) throws PersistenceSystemException;
}
