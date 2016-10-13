package by.epam.like_it.service.impl;


import by.epam.like_it.common_util.ReflectionUtil;
import by.epam.like_it.dao.DaoUtil;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.service.action.ServiceActionDetectableException;
import by.epam.like_it.exception.service.action.ServiceEntityBannedException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.exception.service.validation.info.ValidatorRequiredFieldIsNullException;
import by.epam.like_it.model.adapter.Content;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.Entity;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;
import by.epam.like_it.service.EntityService;
import by.epam.like_it.service.validator.impl.content_validator.ContentValidator;
import by.epam.like_it.service.validator.impl.content_validator.EntityValidator;

import java.util.List;

public class EntityServiceImpl implements EntityService {

    private static final MySqlDaoFactory DAO_FACTORY = MySqlDaoFactory.getInstance();
    private static EntityServiceImpl instance;

    private static final ContentValidator CONTENT_VALIDATOR = ContentValidator.getInstance();
    private static final EntityValidator ENTITY_VALIDATOR = EntityValidator.getInstance();

    private EntityServiceImpl(){}

    public static EntityServiceImpl getInstance(){

        if (instance == null)
            synchronized (EntityServiceImpl.class){
                if (instance == null)
                    instance = new EntityServiceImpl();
            }
        return instance;
    }


    @Override
    public List<Entity> findEntityByEntity(Entity entity) throws ServiceSystemException,
                                                                 ServiceEntityBannedException {
        DaoUtil daoUtil = DAO_FACTORY.getDaoUtil();
        List<Entity> entityByEntity;
        try{
            entityByEntity = daoUtil.findEntityByEntity(entity);
            if (entityByEntity == null || entityByEntity.isEmpty()){
                throw new ServiceEntityBannedException();
            }
        } catch (PersistenceException e) {
            throw new ServiceSystemException("Internal service exception");
        }
        return entityByEntity;
    }

    @Override
    public Entity findOneEntityByEntity(Entity entity) throws ServiceSystemException,
                                                              ServiceEntityBannedException {
        List<Entity> entityByEntity = findEntityByEntity(entity);
        return entityByEntity.get(0);
    }

    @Override
    public DeletableByBan findOneDeletableByBan(DeletableByBan entity) throws ServiceEntityBannedException,
                                                                              ServiceSystemException {
        List<Entity> entityByEntity = findEntityByEntity(entity);
        DeletableByBan byBan = null;
        try{
            byBan = (DeletableByBan) entityByEntity.get(0);
            Boolean banned = byBan.getBanned();
            if (banned){
                throw new ServiceEntityBannedException();
            }
        } catch (ClassCastException e){
            throw new ServiceSystemException("Problem with persistence");
        }
        return byBan;
    }


    @Override
    public long createEntity(Entity entity) throws ServiceSystemException, ServiceActionDetectableException {
        long result;
        DaoUtil daoUtil = DAO_FACTORY.getDaoUtil();
        try {
             result = daoUtil.create(entity);
        } catch (PersistenceSystemException e){
            throw new ServiceSystemException("Can't create entity because of internal problem" + entity, e);
        } catch (PersistenceNotUniqueException e) {
            ErrorInfo errorInfo = e.getErrorInfo();
            if (errorInfo.getFailedField().equals(ErrorInfo.PRIMARY_VALUE)){
                errorInfo.setFailedBean(entity.getClass().getSimpleName());
            }
            throw new ServiceActionDetectableException("Can't create because of duplicate values  " + entity, errorInfo);
        }
        return result;
    }


    public long createRealEntity(RealEntity entity)
            throws ServiceActionDetectableException, ServiceSystemException, ValidationInfoException {
        entity.initRequiredDefault();
        ENTITY_VALIDATOR.isValidForCreate(entity);
        return createEntity(entity);
    }


    @Override
    public long createContent(Content content) throws ValidationInfoException,
                                                      ServiceSystemException{
        RealEntity realEntity = content.getRealEntity();
        if (realEntity == null){
            throw new ServiceSystemException("Can't create null entity");
        }
        String stringContent = content.getContent();
        content.setContent(stringContent.trim());
        String stringTitle = content.getTitle();
        if (stringTitle != null){
            content.setTitle(stringTitle.trim());
        }
        realEntity.initRequiredDefault();
        CONTENT_VALIDATOR.isValidForCreate(content);

        long resultId = -1;
        DaoUtil daoUtil = DAO_FACTORY.getDaoUtil();
        try {
            resultId = daoUtil.create(realEntity);
        } catch (PersistenceException e){
            throw new ServiceSystemException("Can't create real entity " + realEntity);
        }
        return resultId;
    }


    @Override
    public boolean updateRealEntity(RealEntity realEntity)
            throws ValidationInfoException,
                   ServiceSystemException, ServiceActionDetectableException {

        ENTITY_VALIDATOR.isValidForUpdate(realEntity);
        boolean result = false;
        DaoUtil daoUtil = DAO_FACTORY.getDaoUtil();
        try {
            result = daoUtil.updateByObject(realEntity);
        } catch (PersistenceSystemException e){
            throw new ServiceSystemException("Can't create real entity " + realEntity, e);
        } catch (PersistenceNotUniqueException e) {
            throw new ServiceActionDetectableException( "Can't create because of duplicate values  "
                    + realEntity, e.getErrorInfo());
        }
        return result;
    }

    @Override
    public boolean updateContent(Content content) throws ValidationInfoException,
                                                         ServiceSystemException {
        RealEntity realEntity = content.getRealEntity();
        if (realEntity == null){
            throw new ServiceSystemException("Can't create null entity");
        }
        String stringContent = content.getContent();
        content.setContent(stringContent.trim());
        String stringTitle = content.getTitle();
        if (stringTitle != null){
            content.setTitle(stringTitle.trim());
        }
        CONTENT_VALIDATOR.isValidForUpdate(content);
        boolean result = false;
        DaoUtil abstractEntityDao = DAO_FACTORY.getDaoUtil();
        try {
            result = abstractEntityDao.updateByObject(realEntity);
        } catch (PersistenceSystemException e){
            throw new ServiceSystemException("Can't create real entity " + realEntity, e);
        } catch (PersistenceNotUniqueException e) {
            throw new ServiceSystemException("Can't create because of duplicate values  " + realEntity);
        }
        return result;
    }


    @Override
    public boolean deleteEntity(DeletableByBan entity) throws ServiceSystemException {

        boolean result = false;
        DaoUtil daoUtil = DAO_FACTORY.getDaoUtil();
        try{
            result = daoUtil.deleteByBanIfPossible(entity);
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Can't deleteByBanIfPossible " + entity);
        }
        return result;
    }

    @Override
    public boolean deleteEntityByOwner(DeletableByBan entity) throws ServiceSystemException {
        Long id = entity.getId();
        boolean result;
        DaoUtil daoUtil = DAO_FACTORY.getDaoUtil();
        try{
            boolean containsField = ReflectionUtil.containsField(entity.getClass(), "userId");
            if (containsField) {
                result = daoUtil.deleteByOwner(entity);
            } else {
                result = daoUtil.deleteByBanIfPossible(entity);
            }
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Can't deleteByBanIfPossible " + entity);
        }
        return result;
    }

}
