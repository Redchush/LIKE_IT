package by.epam.like_it.dao.mysql.collector.impl;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;
import by.epam.like_it.dao.mysql.collector.BeanCollectors;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.model.bean.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Collector, which work in case if the fields criteria_to be collected is known on the moment of collection.
 * @param <T>
 */
public abstract class NameBasedCollector<T> implements FullStackCollector<T> {

    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    protected static final BeanCollectors collector = BeanCollectors.getInstance();
    public static final Map<String, NameBasedCollector<?>> map = new HashMap<>();
    static {
        map.put("Answer", new NameBasedCollector<Answer>() {
            @Override
            public int fillStatement(PreparedStatement statement, int from, Answer entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            public Answer reallyCollect(ResultSet set, int shift, String aliasPrefix, Answer instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });
        map.put("CategoryContent", new NameBasedCollector<Category>() {
            @Override
            public int fillStatement(PreparedStatement statement, int from, Category entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            public Category reallyCollect(ResultSet set, int shift, String aliasPrefix, Category instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });

        map.put("CategoryTag",  new NameBasedCollector<CategoryTag>(){
            @Override
            public int fillStatement(PreparedStatement statement, int from, CategoryTag entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            public CategoryTag reallyCollect(ResultSet set, int shift, String aliasPrefix, CategoryTag instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });
        map.put("Comment", new NameBasedCollector<Comment>(){
            @Override
            public int fillStatement(PreparedStatement statement, int from, Comment entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            public Comment reallyCollect(ResultSet set, int shift, String aliasPrefix, Comment instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });
        map.put("FavoriteUserTag", new NameBasedCollector<FavoriteUserTag>() {
            @Override
            public int fillStatement(PreparedStatement statement, int from, FavoriteUserTag entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            public FavoriteUserTag reallyCollect(ResultSet set, int shift, String aliasPrefix, FavoriteUserTag instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });

        map.put("FavoriteUserPost", new NameBasedCollector<FavoriteUserPost>(){

            @Override
            public int fillStatement(PreparedStatement statement, int from, FavoriteUserPost entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            public FavoriteUserPost reallyCollect(ResultSet set, int shift, String aliasPrefix, FavoriteUserPost instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });
        map.put("Language", new NameBasedCollector<Language>(){
            @Override
            public int fillStatement(PreparedStatement statement, int from, Language entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            public Language reallyCollect(ResultSet set, int shift, String aliasPrefix, Language instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });
        map.put("Post", new NameBasedCollector<Post>(){

            @Override
            public int fillStatement(PreparedStatement statement, int from, Post entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            public Post reallyCollect(ResultSet set, int shift, String aliasPrefix, Post instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });
        map.put("PostTag", new NameBasedCollector<Language>(){

            @Override
            public int fillStatement(PreparedStatement statement, int from, Language entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            public Language reallyCollect(ResultSet set, int shift, String aliasPrefix, Language instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });

        map.put("Rating", new NameBasedCollector<Rating>() {
            @Override
            public int fillStatement(PreparedStatement statement, int from, Rating entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            public Rating reallyCollect(ResultSet set, int shift, String aliasPrefix, Rating instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });
        map.put("ReadedPost", new NameBasedCollector<ReadedPost>() {
            @Override
            public int fillStatement(PreparedStatement statement, int from, ReadedPost entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            public ReadedPost reallyCollect(ResultSet set, int shift, String aliasPrefix, ReadedPost instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });

        map.put("Role", new NameBasedCollector<Role>() {
            @Override
            public int fillStatement(PreparedStatement statement, int from, Role entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
              return collector.fillStatement(statement, from, entity);
            }

            @Override
            public Role reallyCollect(ResultSet set, int shift, String aliasPrefix, Role instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });
        map.put("Tag", new NameBasedCollector<Tag>(){
            @Override
            public int fillStatement(PreparedStatement statement, int from, Tag entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            protected Tag reallyCollect(ResultSet set, int shift, String aliasPrefix, Tag instance)
                    throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });
        map.put("User", new NameBasedCollector<User>() {
            @Override
            public int fillStatement(PreparedStatement statement, int from, User entity)
                    throws SQLException, PersistenceCollectorException {
                boolean b = super.needStatementToFill(statement);
                if (!b){
                    return from;
                }
                return collector.fillStatement(statement, from, entity);
            }

            @Override
            public User reallyCollect(ResultSet set, int shift, String aliasPrefix, User instance) throws SQLException {
                return collector.createEntity(set, shift, instance);
            }
        });
    }

    /**
     *
     * @param example - example
     * @param <T> - entity type
     * @return predefined COLLECTORS, which use methods from BeanCollectors class, in which
     * stoned the methods criteria_to fill and collect;
     */
    public static<T> NameBasedCollector<T> getCollectorByEntity(T example){
        return (NameBasedCollector<T>) map.get(example.getClass().getSimpleName());
    }

    /**
     * If this method not overridden, it will redirect criteria_to ReflectionBasedCollector
     * @param set - Result set, from which
     * @param shift -
     * @param aliasPrefix
     * @param instance
     * @return
     * @throws SQLException
     * @throws PersistenceCollectorException
     */
    @Override
    public T collectEntity(ResultSet set, int shift, String aliasPrefix, T instance)
            throws SQLException, PersistenceCollectorException {
        try {
            return reallyCollect(set, shift, aliasPrefix, instance);
        } catch (Exception e){
            LOGGER.info("BeanCollectors not applicable for instance " + instance + "Trying use order-based Collerctor");
            set.first();
            return new ReflectionBasedCollector<T>().reallyCollect(set, shift, instance);
        }
    }


    protected T reallyCollect(ResultSet set, int shift, String aliasPrefix, T instance)
            throws SQLException, PersistenceCollectorException {
        return new ReflectionBasedCollector<T>().reallyCollect(set, shift, instance);
    }


    @Override
    public int fillStatement(PreparedStatement statement, int from, T entity)
            throws SQLException, PersistenceCollectorException {
        return new ReflectionBasedCollector<T>().fillStatement(statement, from, entity);
    }

    public Set<T> collectEntitySet(ResultSet set, String aliasPrefix, T instance)
            throws SQLException, PersistenceCollectorException {
        Set<T> result = new HashSet<T>();
        while (set.next()){
            T entity = collectEntity(set, 0, aliasPrefix, instance);
            result.add(entity);
        }
        return result;
    }

    private boolean needStatementToFill(PreparedStatement statement) throws SQLException {
        if (statement == null){
            return false;
        }
        ParameterMetaData parameterMetaData = statement.getParameterMetaData();
        return parameterMetaData.getParameterCount() != 0;
    }

}
