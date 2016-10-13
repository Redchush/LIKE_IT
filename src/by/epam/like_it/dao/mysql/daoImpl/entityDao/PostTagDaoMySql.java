package by.epam.like_it.dao.mysql.daoImpl.entityDao;

import by.epam.like_it.dao.PostTagDao;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.PostTag;

import java.util.List;

public class PostTagDaoMySql extends PostTagDao{

    private static final String DELETE_QUERY = "DELETE FROM posts_tags\n"
            + " WHERE post_id = ? AND tag_id IN (%s)";

    private static PostTagDaoMySql instance;

    private PostTagDaoMySql(){}

    public static PostTagDaoMySql getInstance(){

        if (instance == null)
            synchronized (PostTagDaoMySql.class){
                if (instance == null)
                    instance = new PostTagDaoMySql();
            }
        return instance;
    }

    @Override
    protected PostTag getExampleObject() {
        return new PostTag();
    }

    @Override
    public boolean delete(Long postTagId, List<Long> tagIds) throws PersistenceSystemException {
        return UTIL_HOLDER.getUtilMySql().deleteByMainAndDependant(postTagId, tagIds, DELETE_QUERY);
    }
}
