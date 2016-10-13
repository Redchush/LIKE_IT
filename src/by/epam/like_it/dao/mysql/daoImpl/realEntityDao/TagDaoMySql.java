package by.epam.like_it.dao.mysql.daoImpl.realEntityDao;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.TagDao;
import by.epam.like_it.dao.mysql.collector.impl.ReflectionBasedCollector;
import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.dao.mysql.collector.impl.SingleEntityCollector;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemInternalException;
import by.epam.like_it.exception.persistence.util.ExceptionParser;
import by.epam.like_it.model.bean.PostTag;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.facade.PostTagCriteria;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.model.vo.db_vo.TagVO;
import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TagDaoMySql extends TagDao {

    private static TagDaoMySql instance;

    private TagDaoMySql(){}

    public static TagDaoMySql getInstance(){

        if (instance == null)
            synchronized (TagDaoMySql.class){
                if (instance == null)
                    instance = new TagDaoMySql();
            }
        return instance;
    }

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    private static final String DELETE_QUERY = QUERY_MEDIATOR.getSelectAll(Tag.class) +
                                                " WHERE tags_name IN (%s)";

    private static final String SELECT_POST_TAG = "SELECT SQL_CALC_FOUND_ROWS tags.id, tags.name, COUNT(posts_tags.post_id) AS " +
            "counter__posts_tags__post_id\n" +
            "FROM tags\n" +
            "LEFT JOIN `posts_tags`\n"  +
            "ON tags.id = posts_tags.tag_id\n" +
            "GROUP BY tags.id\n";

    private static final String SELECT_USER_TAG = "SELECT SQL_CALC_FOUND_ROWS tags.id, tags.name \n" +
            "FROM tags\n" +
            "LEFT JOIN favorite_user_tags \n" +
            "ON tags.id = favorite_user_tags.tags_id\n" +
            "WHERE favorite_user_tags.user_id = ?";

    private static final String SELECT_USER_TAG_WITH_INFO =
            "SELECT SQL_CALC_FOUND_ROWS tags.id, tags.name, COUNT(posts_tags.post_id) AS counter__posts_tags__post_id\n" +
            "FROM tags\n" +
            "LEFT JOIN favorite_user_tags \n" +
            "ON tags.id = favorite_user_tags.tags_id\n" +
            "LEFT JOIN posts_tags  \n" +
            "ON favorite_user_tags.tags_id = posts_tags.tag_id\n" +
            "WHERE favorite_user_tags.user_id = ? \n" +
            "GROUP BY tags.id";


    private static final  String initialCount = "SELECT COUNT(*)\n" +
            "FROM tags\n";



    private final FullStackCollector<TagVO> POST_TAG_VO_COLLECTOR = new FullStackCollector<TagVO>() {
        @Override
        public TagVO collectEntity(ResultSet set, int shift, String aliasPrefix, TagVO instance)
                throws SQLException, PersistenceCollectorException {
            TagVO vo = new TagVO();
            Tag entity = COLLECTORS.createEntity(set, 0, new Tag());
            vo.setTag(entity);
            vo.setInfo(vo.new TagInfo());
            long counter_posts_tags = set.getLong("counter__posts_tags__post_id");
            vo.getInfo().setCountPostTag(counter_posts_tags);
            return vo;
        }

        @Override
        public int fillStatement(PreparedStatement statement, int from, Supplier supplier)
                throws SQLException, PersistenceCollectorException {
            if (supplier != null) {
                Long userId = (Long) supplier.get();
                statement.setLong(1, userId);
                return 2;
            }
            return 1;
        }
    };

    private final FullStackCollector<Tag> USER_TAG_COLLECTOR = new FullStackCollector<Tag>() {
        @Override
        public Tag collectEntity(ResultSet set, int shift, String aliasPrefix, Tag instance)
                throws SQLException, PersistenceCollectorException {
            return COLLECTORS.createEntity(set, 0, new Tag());
        }

        @Override
        public int fillStatement(PreparedStatement statement, int from, Supplier supplier)
                throws SQLException, PersistenceCollectorException {
            Long userId = (Long) supplier.get();
            statement.setLong(1, userId);
            return 1;
        }
    };

    @Override
    protected Tag getExampleObject() {
        return new Tag();
    }

    @Override
    public List<TagVO> findTagWithInfo(PostTagCriteria definition)
            throws PersistenceSystemException {
        String select = CRITERIA_HANDLER.processCriteria(SELECT_POST_TAG, definition);
        return UTIL_HOLDER.getUtilMySql().findAll(select, new TagVO(), POST_TAG_VO_COLLECTOR, null, false);
    }

    @Override
    public ListCounterResponse<TagVO> getPostTagsResponse(PostTagCriteria definition) throws PersistenceSystemException {
        String select = CRITERIA_HANDLER.processCriteria(SELECT_POST_TAG, definition);
        return UTIL_HOLDER.getUtilMySql().findAllAndCountRows(select, new TagVO(), POST_TAG_VO_COLLECTOR, null, false);
    }

    @Override
    public ListCounterResponse<Tag> getUserTagsResponse(Long userId, Criteria<Tag> definition) throws
                                                                                      PersistenceSystemException {
        String select = CRITERIA_HANDLER.processCriteria(SELECT_USER_TAG, definition);
        return UTIL_HOLDER.getUtilMySql().findAllAndCountRows(select, new Tag(), USER_TAG_COLLECTOR, ()->userId, false);
    }

    @Override
    public ListCounterResponse<TagVO> getUserTagsResponseWithInfo(Long userId, PostTagCriteria definition) throws
                                                                                               PersistenceSystemException {
        String select = CRITERIA_HANDLER.processCriteria(SELECT_USER_TAG_WITH_INFO, definition);
        return UTIL_HOLDER.getUtilMySql().findAllAndCountRows(select, new TagVO(), POST_TAG_VO_COLLECTOR, ()->userId, false);
    }

    @Override
    public Long getInitialCount() throws PersistenceSystemException {
        List<Long> all = UTIL_HOLDER.getUtilMySql().findAll(initialCount, 0L, new SingleEntityCollector<Long>(), null, false);
        return all.get(0);
    }


    @Override
    public boolean createTagAndPostTag(List<Tag> tags, Long userId )
            throws PersistenceSystemException, PersistenceNotUniqueException {
        int initialSize = tags.size();
        String preperadPart = QUERY_MEDIATOR.getPreperadPart(initialSize);
        String query = String.format(DELETE_QUERY, preperadPart);

        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet set = null;
        try {
            connection = CONNECTION_FACTORY.takeConnectionWithoutCommit();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ReflectionBasedCollector<Tag> collector = new ReflectionBasedCollector<Tag>();
            List<String> remainNames = new ArrayList<>();
            for (int i = 0; i < initialSize -1; i++) {
                String tagName = tags.get(i).getName();
                statement.setString(i+1, tagName);
                remainNames.add(tagName);
            }
            set = statement.executeQuery();
            List<Tag> tagsFound = collector.collectEntityList(set, "", new Tag()); // tags existing in db

            closeAllSource(set, statement);
            clearFoundNames(tagsFound, remainNames);
            List<Long> generatedKeys = new ArrayList<>();

            if (tagsFound.size() != initialSize) {          //there is something to create
                query = QUERY_MEDIATOR.getCreate(Tag.class, remainNames.size(), true);
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < remainNames.size() - 1; i++) {
                    statement.setString(i + 1, remainNames.get(i));
                }
                statement.executeUpdate();
                generatedKeys = getGeneratedKeys(statement);
                statement.close();
            }

            generatedKeys.addAll(tagsFound.stream().map(Tag::getId).collect(Collectors.toList()));

            query = QUERY_MEDIATOR.getCreate(PostTag.class, initialSize, false);
            statement = connection.prepareStatement(query);
            int counter = 1;
            for (int i = 0; i < initialSize - 1; i++) {
                statement.setLong(counter++, userId);
                statement.setLong(counter++, generatedKeys.get(i));
            }
            int state = statement.executeUpdate();
            connection.commit();
            return isPositiveState(state);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Can't execute rollback from create by initial query: \n" + query
                        + "with filled statement: \n" + statement +
                        " and entity \n " + tags  + " state :" + e.getSQLState(), e);
                throw new PersistenceSystemInternalException(this.getClass() + " error in rollback after create by id", e);
            }
             throw new PersistenceSystemInternalException(this.getClass() + " error in create by id", e);
        } catch (ConnectionPoolException e) {
            throw new PersistenceSystemInternalException(this.getClass().getSimpleName() + " : can't take the connection", e);
        } finally {
            closeAllSource(set, statement, connection);
        }
    }

    private void clearFoundNames(List<Tag> tagsFound, List<String> remainNames){
        for (Tag tag : tagsFound) {
            String name = tag.getName();
            remainNames.remove(name);
        }
    }

}

