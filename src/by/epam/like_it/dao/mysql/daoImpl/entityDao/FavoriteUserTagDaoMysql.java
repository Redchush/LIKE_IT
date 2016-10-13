package by.epam.like_it.dao.mysql.daoImpl.entityDao;


import by.epam.like_it.dao.FavoriteUserTagDao;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.FavoriteUserTag;

import java.util.Collection;

public class FavoriteUserTagDaoMysql extends FavoriteUserTagDao{

    private static FavoriteUserTagDaoMysql instance;

    private static final String DELETE_QUERY = "DELETE FROM favorite_user_tags\n"
                                              + " WHERE user_id = ? AND tags_id IN (%s)";

    private FavoriteUserTagDaoMysql(){}

    public static FavoriteUserTagDaoMysql getInstance(){

        if (instance == null)
            synchronized (FavoriteUserTagDaoMysql.class){
                if (instance == null)
                    instance = new FavoriteUserTagDaoMysql();
            }
        return instance;
    }

    @Override
    protected FavoriteUserTag getExampleObject() {
        return new FavoriteUserTag();
    }

    @Override
    public boolean delete(Long userId, Collection<Long> tagIds) throws PersistenceSystemException {
       return UTIL_HOLDER.getUtilMySql().deleteByMainAndDependant(userId, tagIds, DELETE_QUERY);
    }
}
