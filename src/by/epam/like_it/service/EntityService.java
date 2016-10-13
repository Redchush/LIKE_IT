package by.epam.like_it.service;

import by.epam.like_it.exception.service.action.ServiceActionDetectableException;
import by.epam.like_it.exception.service.action.ServiceEntityBannedException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.exception.service.validation.info.ValidatorRequiredFieldIsNullException;
import by.epam.like_it.model.adapter.Content;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.Entity;
import by.epam.like_it.model.bean.util_interface.RealEntity;

import java.util.List;


public interface EntityService {

    /**
     * If there are no entity in persistence or found entity is banned, throws ServiceEntityBannedException
     * @param entity - entity as data source for searching
     * @return List<Entity>  found in persistence which fields match the fields of argument entity
     * @throws ServiceSystemException - If internal problem occured
     * @throws ServiceEntityBannedException - If there are no entity in persistence or found entity is banned
     */
    List<Entity> findEntityByEntity(Entity entity) throws ServiceSystemException, ServiceEntityBannedException;

    Entity findOneEntityByEntity(Entity entity) throws ServiceSystemException, ServiceEntityBannedException;

    DeletableByBan findOneDeletableByBan(DeletableByBan entity) throws ServiceSystemException, ServiceEntityBannedException;



    boolean updateRealEntity(RealEntity realEntity) throws
                                                    ValidationInfoException,
                                                           ServiceSystemException, ServiceActionDetectableException;

    boolean updateContent(Content content) throws ValidationInfoException,
                                                  ServiceSystemException;

    boolean deleteEntity(DeletableByBan entity) throws ServiceSystemException;



    boolean deleteEntityByOwner(DeletableByBan entity) throws ServiceSystemException;

    /**
     * Prepare entity for saving, initializing all required fields by default values.
     * Check all fields is valid for creating
     * Save entity into persistence
     * @param entity - entity to be saved
     * @return id of new created entity
     * @throws ServiceSystemException - if some internal exception occured
     * @throws ServiceActionDetectableException - if create action fail.
     * @throws ValidationInfoException - if some field is't valid.
     * @see ValidationInfoException
     */
    long createRealEntity(RealEntity entity)
            throws ServiceActionDetectableException, ServiceSystemException, ValidationInfoException;

    /**
     * Presume needn't to validate
     * @param entity - entity to be saved
     * @return id of new created entity or 0 if entity hasn't id
     * @throws ServiceSystemException - if some internal exception occured
     * @throws ServiceActionDetectableException  if create action fail
     */
    long createEntity(Entity entity) throws ServiceSystemException, ServiceActionDetectableException;

    /**
     * Prepare entity for saving, initializing all required fields by default values.
     * Check all fields is valid for creating
     * Save entity into persistence.
     * Validate by information in ContentValidator.
     * @param content - adapter from entity to content
     * @return id of newly saved entity
     * @throws ValidatorRequiredFieldIsNullException - some important field is null
     * @throws ValidationInfoException - if some field is't valid.
     * @throws ServiceSystemException - if some internal exception occured
     */
    long createContent(Content content) throws ValidationInfoException,
                                               ServiceSystemException;
}
