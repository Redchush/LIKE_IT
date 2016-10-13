package by.epam.like_it.dao.mysql.collector.impl;

import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.dao.mysql.collector.type.TypeHandler;
import by.epam.like_it.dao.mysql.collector.type.TypeHandlerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


public class SingleEntityCollector<T> implements FullStackCollector<T> {

    @Override
    public T collectEntity(ResultSet set, int shift, String aliasPrefix, T instance)
            throws SQLException, PersistenceCollectorException {
        boolean next = set.next();
        if (next) {
            String simpleName = instance.getClass().getSimpleName();
            TypeHandler<?> wrapperByClassName = TypeHandlerFactory.getInstance()
                                                                  .getWrapperByClassName(simpleName);
            return (T) wrapperByClassName.getDataFromResultSet(set, shift);
        }
        return null;
    }

    @Override
    public List<T> collectEntityList(ResultSet set, String aliasPrefix, T instance)
            throws SQLException, PersistenceCollectorException {
        T t = collectEntity(set, 1, aliasPrefix, instance);
        return Collections.singletonList(t);
    }

    @Override
    public int fillStatement(PreparedStatement statement, int from, T entity)
            throws SQLException, PersistenceCollectorException {
        return 0;
    }
}
