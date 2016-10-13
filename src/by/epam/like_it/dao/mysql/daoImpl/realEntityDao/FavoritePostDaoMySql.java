package by.epam.like_it.dao.mysql.daoImpl.realEntityDao;

import by.epam.like_it.dao.FavoritePostDao;
import by.epam.like_it.model.bean.FavoriteUserPost;


public class FavoritePostDaoMySql extends FavoritePostDao {

    private static FavoritePostDaoMySql instance;

    private FavoritePostDaoMySql(){}

    public static FavoritePostDaoMySql getInstance(){

        if (instance == null)
            synchronized (FavoritePostDaoMySql.class){
                if (instance == null)
                    instance = new FavoritePostDaoMySql();
            }
        return instance;
    }

    @Override
    protected FavoriteUserPost getExampleObject() {
        return new FavoriteUserPost();
    }


//    public FavoriteUserPost findFavoritePost(){
//
//    }
    //    @Override
//    public Criteria<FavoriteUserPost> getCriteria() {
//        return new CriteriaMySql<FavoriteUserPost>(FavoriteUserPost.class);
//    }

}
//favorite_users_posts.num = 4
//        favorite_users_posts.1 = id
//        favorite_users_posts.2 = user_id
//        favorite_users_posts.3 = post_id
//        favorite_users_posts.4 = comment
