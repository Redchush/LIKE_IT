package by.epam.like_it.dao.mysql.daoImpl;


import by.epam.like_it.dao.DaoUtil;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemInternalException;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public interface DaoUtilInner extends DaoUtil {

    public <D> List<D> findAll(String query, D instance,
                               FullStackCollector<D> collector, Supplier supplier,
                               boolean scrollable) throws PersistenceSystemException;

    <D> ListCounterResponse<D> findAllAndCountRows(String query, D instance,
                                                   FullStackCollector<D> collector, Supplier supplier,
                                                   boolean scrollable)
            throws PersistenceSystemException;

    boolean deleteByMainAndDependant(Long main, Collection<Long> dependant,
                                     String queryWithIn) throws PersistenceSystemException;

    boolean delete(String query, List<Long> values) throws PersistenceSystemInternalException;

    void closeAllSource(ResultSet set, PreparedStatement statement, Connection connection);

    void closeAllSource(PreparedStatement statement, Connection connection);

    void closeAllSource(ResultSet set, Statement statement);
}
