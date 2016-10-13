package by.epam.like_it.dao.mysql.daoImpl.entityDao;


import by.epam.like_it.dao.FavoriteUserPostDao;
import by.epam.like_it.model.bean.FavoriteUserPost;

public class FavoriteUserPostDaoMySql extends FavoriteUserPostDao {

    private static FavoriteUserPostDaoMySql instance;

    private FavoriteUserPostDaoMySql(){}

    public static FavoriteUserPostDaoMySql getInstance(){

        if (instance == null)
            synchronized (FavoriteUserPostDaoMySql.class){
                if (instance == null)
                    instance = new FavoriteUserPostDaoMySql();
            }
        return instance;
    }

    @Override
    protected FavoriteUserPost getExampleObject() {
        return new FavoriteUserPost();
    }


}
