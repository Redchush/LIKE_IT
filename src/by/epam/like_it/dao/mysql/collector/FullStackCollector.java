package by.epam.like_it.dao.mysql.collector;


import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public interface FullStackCollector<T> extends CollectorFacade<T>{

    default int fillStatement(PreparedStatement statement, int from, T entity)
            throws SQLException, PersistenceCollectorException {
      /*NOP*/
       return from;
    }

    default int fillStatement(PreparedStatement statement, int from, Supplier supplier)
            throws SQLException, PersistenceCollectorException {
        /*NOP*/
       return from;
    }

    T collectEntity(ResultSet set, int shift, String aliasPrefix, T instance)
            throws SQLException, PersistenceCollectorException;

    @Override
    default List<T> collectEntityList(ResultSet set, String aliasPrefix, T instance)
            throws SQLException, PersistenceCollectorException {

        List<T> result = new ArrayList<>();
        while (set.next()){
            T entity = collectEntity(set, 0, aliasPrefix, instance);
            if (entity != null){
                result.add(entity);
            }
        }
        return result;
    }
}
