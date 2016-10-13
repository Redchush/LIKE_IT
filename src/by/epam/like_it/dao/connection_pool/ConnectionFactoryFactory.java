package by.epam.like_it.dao.connection_pool;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.connection_pool.impl.custom.CustomConnectionPool;
import by.epam.like_it.dao.connection_pool.impl.raw.RawConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionFactoryFactory {

    public enum FactoryType{RAW, CUSTOM }

    private static Logger logger = LogManager.getLogger(ClassName.getClassName());

    private final Lock lock;

    private static ConnectionFactoryFactory instance;

    private FactoryType current = FactoryType.RAW;

    private ConnectionFactoryFactory(){
        lock = new ReentrantLock();
    }

    public static ConnectionFactoryFactory getInstance(){
        if (instance == null)
            synchronized (ConnectionFactoryFactory.class){
                if (instance == null)
                    instance = new ConnectionFactoryFactory();
            }
        return instance;
    }

    /**
     *
     * @param type - type of wanted ConnectionFactory
     */
    public void setConnectionType(FactoryType type){
        try{
            lock.lock();
            current = type;
        } finally {
            lock.unlock();
        }
    }


    /**
     *
     * @return ConnectionFactory, which produce specified connections.
     * The type of CONNECTION_FACTORY depends on setConnectionType(FactoryType type) method
     */

    public ConnectionFactory getConnectionFactory() {
        try{
            lock.lock();
            switch (current) {
                case RAW:
                    return new RawConnection();
                case CUSTOM:
                    return CustomConnectionPool.getInstance();
                default:
                    throw new RuntimeException("There no exists such FactoryType");
            }

        } finally {
            lock.unlock();
        }
    }
//
//    public void initPoodData(){
//        try{
//            lock.lock();
//            try {
//                switch (current) {
//                    case RAW:
//                        /*NOP*/
//                    case CUSTOM:
//                        CustomConnectionPool instance = CustomConnectionPool.getInstance();
//                        instance.initPoolData();
//                    default:
//                        throw new RuntimeException("There no exists such FactoryType");
//                }
//            } catch (ConnectionPoolException e) {
//                logger.warn("Failed init CustomConnectionPool. From now raw connection is using.");
//            }
//        } finally {
//            lock.unlock();
//        }
//    }
}
