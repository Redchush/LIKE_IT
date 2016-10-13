package by.epam.like_it.service.impl.system;

import by.epam.like_it.dao.mysql.util.query.QueryCache;
import by.epam.like_it.service.CashService;


public class CashServiceImpl implements CashService {

    private static CashServiceImpl instance;

    private CashServiceImpl(){}

    public static CashServiceImpl getInstance(){

        if (instance == null)
            synchronized (CashServiceImpl.class){
                if (instance == null)
                    instance = new CashServiceImpl();
            }
        return instance;
    }

    @Override
    public void invalidateCash(){
        QueryCache instance = QueryCache.getInstance();
        instance.reset();
    }
}
