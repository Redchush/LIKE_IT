package by.epam.like_it.dao.mysql.daoImpl;


public class DaoUtilHolder  {

    private static DaoUtilHolder instance;

    private DaoUtilMySql utilMySql;

    private DaoUtilHolder(){
        utilMySql = DaoUtilMySql.getInstance();
    }

    public static DaoUtilHolder getInstance(){

        if (instance == null)
            synchronized (DaoUtilHolder.class){
                if (instance == null)
                    instance = new DaoUtilHolder();
            }
        return instance;
    }

    public DaoUtilInner getUtilMySql() {
        return utilMySql;
    }
}
