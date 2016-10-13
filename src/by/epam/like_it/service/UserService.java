package by.epam.like_it.service;


import by.epam.like_it.exception.service.action.ServiceActionDetectableException;
import by.epam.like_it.exception.service.action.ServiceAuthException;
import by.epam.like_it.exception.service.action.ServiceEntityBannedException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.PhotoVo;
import by.epam.like_it.model.vo.db_vo.UserVO;

/**
 * Deal with all matters which concerns directly to user as entity.
 */
public interface UserService {

    /**
     * Extract User bean from persistence and assign default values for all fields allowed default;
     * @param user - user criteria_to be authorized
     * @return User bean with default values
     * @throws ValidationInfoException
     * @throws ServiceSystemException
     * @see by.epam.like_it.model.bean.util_interface.RealEntity
     */
    User authorise(User user) throws ServiceSystemException, ServiceAuthException, ValidationInfoException;

    /**
     * Save User bean in persistence and assign default values criteria_to bean in parameter for all fields allowed default;
     * @param user - user criteria_to be registered
     * @return User bean with default values
     * @throws ValidationInfoException
     * @throws ServiceSystemException
     * @see by.epam.like_it.model.bean.util_interface.RealEntity
     */
    User register(User user) throws ServiceSystemException, ServiceAuthException, ValidationInfoException;

    /**
     *
     * @param id - id of user in request
     * @return userVO with info and favorite tags. Assign id value -1 if this user is banned
     * @throws ServiceEntityBannedException - in case current user is banned
     * @throws ServiceSystemException -
     */
    UserVO getUserProfile(Long id) throws ServiceSystemException, ServiceEntityBannedException;

    String updatePhoto(PhotoVo vo) throws ValidationInfoException, ServiceActionDetectableException, ServiceSystemException;

    boolean deletePhoto(PhotoVo vo) throws ServiceActionDetectableException, ServiceSystemException;
}

