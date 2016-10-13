package by.epam.like_it.dao;


import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.UserVO;

public abstract class UserDao  extends AbstractRealEntityDao<User>{

    public abstract User authorize(User user) throws PersistenceSystemException;

    public abstract UserVO getUserVoWithInfo(Long id) throws PersistenceSystemException;
}
