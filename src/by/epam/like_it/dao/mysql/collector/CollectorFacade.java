package by.epam.like_it.dao.mysql.collector;


import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CollectorFacade<T> {

    List<T> collectEntityList(ResultSet set, String aliasPrefix, T instance)
            throws SQLException, PersistenceCollectorException;
}
