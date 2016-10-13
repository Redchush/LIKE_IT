package by.epam.like_it.dao;


import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.persistence.system.PersistenceUnsupportedOperationException;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.Entity;
import by.epam.like_it.model.bean.util_interface.RealEntity;

import java.util.List;

public interface DaoUtil {

    List<Entity> findEntityByEntity(Entity entity) throws PersistenceSystemException;

    boolean update(RealEntity entity) throws PersistenceSystemException, PersistenceNotUniqueException;

    boolean updateByObject(RealEntity entity) throws PersistenceNotUniqueException, PersistenceSystemException;

    long create(Entity entity) throws PersistenceNotUniqueException, PersistenceSystemException;

    /**
     * Delete entity from persistence regardless of it's type
     * @param entity - entity to be deleted
     * @return whether the entity was debated
     * @throws PersistenceSystemException - If some internal exception occured
     */
    boolean deleteReally(RealEntity entity) throws PersistenceSystemException;

    /**
     * Delete entity by setting ban attribute if it is possible
     * @param entity - entity to be deleted
     * @return whether the entity was debated
     * @throws PersistenceSystemException - If some internal exception occured
     */
    boolean deleteByBanIfPossible(RealEntity entity) throws PersistenceSystemException;

    boolean deleteByOwner(DeletableByBan entity) throws PersistenceSystemException;
}
