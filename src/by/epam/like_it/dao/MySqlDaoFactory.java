package by.epam.like_it.dao;

import by.epam.like_it.dao.mysql.daoImpl.DaoUtilMySql;
import by.epam.like_it.dao.mysql.daoImpl.entityDao.FavoriteUserTagDaoMysql;
import by.epam.like_it.dao.mysql.daoImpl.entityDao.PostTagDaoMySql;
import by.epam.like_it.dao.mysql.daoImpl.realEntityDao.*;


public class MySqlDaoFactory {

    private static MySqlDaoFactory instance;

    private MySqlDaoFactory(){}

    public static MySqlDaoFactory getInstance(){
        if (instance == null)
            synchronized (MySqlDaoFactory.class){
                if (instance == null)
                    instance = new MySqlDaoFactory();
            }
        return instance;
    }

    public AnswerDao getAnswerDao(){
        return AnswerDaoMySql.getInstance();
    }

    @Deprecated
    public CategoryDao getCategoryDao(){
        return CategoryDaoMySql.getInstance();
    }

    public CommentDao getCommentDao(){
        return CommentDaoMySql.getInstance();
    }

    public FavoritePostDao getFavoritePostDao(){
        return FavoritePostDaoMySql.getInstance();
    }

    public PostDao getPostDao(){
        return PostDaoMySql.getInstance();
    }

    public RatingDao getRatingDao(){
        return RatingDaoMySql.getInstance();
    }
    public RoleDao getRoleDao(){
        return RoleDaoMySql.getInstance();
    }

    public TagDao getTagDao(){
        return TagDaoMySql.getInstance();
    }

    public UserDao getUserDao(){
        return UserDaoMySql.getInstance();
    }

    public PostTagDao getPostTagDao(){return PostTagDaoMySql.getInstance();}

    public FavoriteUserTagDao getFavoriteUserTagDao(){return FavoriteUserTagDaoMysql.getInstance();}

    public DaoUtil getDaoUtil(){
        return DaoUtilMySql.getInstance();
    }

}
