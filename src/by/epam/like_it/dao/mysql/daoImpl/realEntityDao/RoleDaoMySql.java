package by.epam.like_it.dao.mysql.daoImpl.realEntityDao;

import by.epam.like_it.dao.RoleDao;
import by.epam.like_it.model.bean.Role;

/**
 *
 */
public class RoleDaoMySql extends RoleDao {

    private static RoleDaoMySql instance;

    private RoleDaoMySql(){}

    public static RoleDaoMySql getInstance(){

        if (instance == null)
            synchronized (RoleDaoMySql.class){
                if (instance == null)
                    instance = new RoleDaoMySql();
            }
        return instance;
    }

    @Override
    protected Role getExampleObject() {
        return new Role();
    }



}

