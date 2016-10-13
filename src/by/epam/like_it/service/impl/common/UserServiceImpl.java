package by.epam.like_it.service.impl.common;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.DaoUtil;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.UserDao;
import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.service.action.ServiceActionDetectableException;
import by.epam.like_it.exception.service.action.ServiceEntityBannedException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.PhotoVo;
import by.epam.like_it.model.vo.db_vo.UserVO;
import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;
import by.epam.like_it.service.UserService;
import by.epam.like_it.exception.service.action.ServiceAuthException;
import by.epam.like_it.exception.service.ServiceException;
import by.epam.like_it.service.validator.impl.PhotoValidator;
import by.epam.like_it.service.validator.impl.user_validator.UserLoginationValidator;
import by.epam.like_it.service.validator.impl.user_validator.UserRegistrationValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class UserServiceImpl implements UserService {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private MySqlDaoFactory FACTORY = MySqlDaoFactory.getInstance();

    private static UserServiceImpl instance;

    private UserServiceImpl(){}

    public static UserServiceImpl getInstance(){

        if (instance == null)
            synchronized (UserServiceImpl.class){
                if (instance == null)
                    instance = new UserServiceImpl();
            }
        return instance;
    }

    /**
     * Extract User bean from persistence and assign default values for all fields allowed default;
     * @param user - user criteria_to be authorized
     * @return User bean with default values
     * @throws ValidationInfoException
     * @throws ServiceSystemException
     * @see by.epam.like_it.model.bean.util_interface.RealEntity
     */
    @Override
    public User authorise(User user) throws ValidationInfoException, ServiceSystemException, ServiceAuthException {
        UserLoginationValidator validator = UserLoginationValidator.getInstance();
        validator.isValidForCreate(user);


        User result = null;
        UserDao dao = FACTORY.getUserDao();
        try{
            result = dao.authorize(user);
            if (result == null){
                ServiceAuthException serviceAuthException = new ServiceAuthException("This user is't exist");
                serviceAuthException.setErrorInfo(new ErrorInfo("User",ServiceAuthException.LOGIN_FAIL));
                throw serviceAuthException;
            } else {
                String expectedPassword = result.getPassword();
                String actualPassword = user.getPassword();
                boolean isIt = expectedPassword.equals(actualPassword);
                if (!isIt){
                    ServiceAuthException serviceAuthException = new ServiceAuthException("Someone failed criteria_to log in by" +
                            " reason on inappropriate password");
                    LOGGER.debug(expectedPassword + " " + actualPassword);
                    serviceAuthException.setErrorInfo(new ErrorInfo("User", ServiceAuthException.PASSWORD_FAIL));
                    throw serviceAuthException;
                }
                if (result.getBanned()){
                    ServiceAuthException serviceAuthException = new ServiceAuthException("Banned user try criteria_to log in");
                    serviceAuthException.setErrorInfo(new ErrorInfo("User", ServiceAuthException.BANNED_FAIL));
                    throw serviceAuthException;
                }
            }
            result.initAllDefault();
            LOGGER.debug("User authorised : " + result);
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Unknown problem");
        }
        return result;
    }



    /**
     * Save User bean in persistence and assign default values criteria_to bean in parameter for all fields allowed default;
     * @param user - user criteria_to be registered
     * @return User bean with default values
     * @throws ValidationInfoException
     * @throws ServiceSystemException
     * @see by.epam.like_it.model.bean.util_interface.RealEntity
     */
    @Override
    public User register(User user) throws ValidationInfoException, ServiceSystemException, ServiceAuthException {
        UserRegistrationValidator validator = UserRegistrationValidator.getInstance();
        validator.isValidForCreate(user);
        user.initRequiredDefault();

        UserDao dao = FACTORY.getUserDao();
        try {
            long b = dao.create(user);
            if (b == 0){
                throw new ServiceAuthException("Wrong email or login");
            } else {
                user.setId(b);
            }
            user.initAllDefault();
        }catch (PersistenceNotUniqueException e){
            throw new ServiceAuthException(e.getErrorInfo());
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Error in source", e);
        }
        LOGGER.debug("User registered: " + user);
        return user;
    }

    /**
     *
     * @param id - id of user in request
     * @return userVO with info and favorite tags. Assign id value -1 if this user is banned
     * @throws ServiceEntityBannedException - in case current user is banned
     * @throws ServiceSystemException
     */
    @Override
    public UserVO getUserProfile(Long id) throws ServiceEntityBannedException, ServiceSystemException{
        UserDao userDao = FACTORY.getUserDao();
        UserVO userVoWithInfo = null;
        try {
            userVoWithInfo = userDao.getUserVoWithInfo(id);
            User user = userVoWithInfo.getUser();
            user.initAllDefault();
            if (user.getBanned()){
                throw new ServiceEntityBannedException();
            }
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Problem during finding user by id " + id);
        }
        return userVoWithInfo;
    }



    public static final String FOTO_ROOT = "resources\\user_foto\\";

    @Override
    public String updatePhoto(PhotoVo vo) throws ValidationInfoException, ServiceActionDetectableException, ServiceSystemException {

        PhotoValidator validator = PhotoValidator.getInstance();

        validator.isValidForUpdate(vo);
        byte[] newPhoto = vo.getNewPhoto();
        String extention = vo.getExtension();


        User user = vo.getUser();
        String pathToSave = FOTO_ROOT + user.getId() + extention;

        String fileToSave = vo.getRealPath() + pathToSave;

        LOGGER.debug(fileToSave);
        try(BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileToSave))) {
            outputStream.write(newPhoto);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            throw new ServiceSystemException("Problem with file system");
        } catch (IOException e) {
            throw new ServiceSystemException("Problem with file system during saving photo");
        }

        String prevPhoto = vo.getPrevPhoto();
        String defaultPath = validator.getDefaultPhotoPath();
        pathToSave = "/"+ pathToSave.replace("\\", "/");
        if (!prevPhoto.equals(defaultPath) && !prevPhoto.equals(pathToSave)){
            deletePrevFile(vo.getRealPath(), prevPhoto);
        }
        user.setFotoPath(pathToSave);
        boolean b = updateUser(user);
        if (b){
            return pathToSave;
        }
        return null;
    }

    @Override
    public boolean deletePhoto(PhotoVo vo) throws ServiceActionDetectableException, ServiceSystemException {
        PhotoValidator validator = PhotoValidator.getInstance();
        String defaultPhotoPath = validator.getDefaultPhotoPath();

        User user = vo.getUser();
        user.setFotoPath(defaultPhotoPath);
        boolean b = updateUser(user);
        if (b){
            deletePrevFile(vo.getRealPath(), vo.getPrevPhoto());
        }
        return b;
    }

    private boolean deletePrevFile(String fullPath, String prev){
        String total = fullPath + prev.replace("/", "\\");
        LOGGER.debug("delete prev " + total);
        File file = new File(total);
        return file.delete();
    }

    public boolean updateUser(User user) throws ServiceActionDetectableException, ServiceSystemException {
        DaoUtil daoUtil = FACTORY.getDaoUtil();
        try {
            return daoUtil.updateByObject(user);
        }  catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Can't update user file path: internal problem", e);
        } catch (PersistenceNotUniqueException e) {
            throw new ServiceActionDetectableException( "Can't update user file path", new ErrorInfo("User",
                    "fotoPath"));
        }
    }


}
