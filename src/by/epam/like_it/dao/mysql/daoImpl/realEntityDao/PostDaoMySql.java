package by.epam.like_it.dao.mysql.daoImpl.realEntityDao;


import by.epam.like_it.dao.PostDao;
import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;
import by.epam.like_it.dao.mysql.collector.FullStackCollector;
import by.epam.like_it.dao.mysql.collector.impl.ReflectionBasedCollector;
import by.epam.like_it.dao.mysql.util.CriteriaHandler;
import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.model.bean.*;

import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.model.vo.util.EmptyVoManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static org.apache.ibatis.ognl.DynamicSubscript.all;

public class PostDaoMySql extends PostDao {

    private static PostDaoMySql instance;

    private PostDaoMySql(){}

    public static PostDaoMySql getInstance(){

        if (instance == null)
            synchronized (PostDaoMySql.class){
                if (instance == null)
                    instance = new PostDaoMySql();
            }
        return instance;
    }

    private static final String INITIAL_SELECT ="SELECT SQL_CALC_FOUND_ROWS  posts.id, posts.user_id, posts.category_id, posts.title, posts.content, posts.created_date, posts.updated_date, posts.banned\n" +
            ", users.id, users.role_id, users.login, users.foto_path, users.banned \n" +
            ", categories.id, categories.title, categories.language_id, categories.description, categories.parent_category, categories.created_date, categories.published\n" +
            ", GROUP_CONCAT(DISTINCT tags.id)\n" +
            ", GROUP_CONCAT(DISTINCT tags.name)\n" +
            ", COUNT(DISTINCT readed_posts.user_id) AS counter__readed_posts__user_id\n" +
            ", COUNT(DISTINCT favorite_users_posts.user_id) AS counter__favorite_users_posts__user_id\n" +
            ", COUNT(DISTINCT answers.id) AS counter__answers__id\n" +
            "FROM posts\n" +
            "LEFT JOIN users ON users.id = posts.user_id \n" +
            "LEFT JOIN categories ON categories.id = posts.category_id AND categories.published = true\n" +
            "LEFT JOIN posts_tags ON posts_tags.post_id = posts.id\n" +
            "LEFT JOIN tags ON tags.id = posts_tags.tag_id\n" +
            "LEFT JOIN readed_posts ON readed_posts.post_id = posts.id\n" +
            "LEFT JOIN favorite_users_posts\n" +
            "ON favorite_users_posts.post_id = posts.id\n" +
            "LEFT JOIN answers \n" +
            "ON answers.post_id = posts.id AND answers.banned = FALSE\n" +
            "GROUP BY posts.id \n";

    private static final String SHORT_POST_SELECT = "SELECT posts.id, posts.title FROM posts";

    private static final String INITIAL_SELECT_ONE;
    private static final String INITIAL_SELECT_FAVORITE_USER;
    static{
        EqConstriction<Post, String> eqPostId = new EqConstriction<>(Post.class, "id", Collections.singleton("?"));
        INITIAL_SELECT_ONE = CRITERIA_HANDLER.processSinglePart(INITIAL_SELECT, CriteriaHandler.Mode.PREPARED, eqPostId);

        EqConstriction<FavoriteUserPost, String> eqFavoriteUserId
                = new EqConstriction<>(FavoriteUserPost.class, "userId", Collections.singleton("?"));
        EqConstriction<Post, String> postNotBanned
                = new EqConstriction<>(Post.class, "banned", Collections.singleton("false"));
        INITIAL_SELECT_FAVORITE_USER = CRITERIA_HANDLER.processSinglePart(INITIAL_SELECT,
                CriteriaHandler.Mode.PREPARED, eqFavoriteUserId, postNotBanned);
    }


    private static final FullStackCollector<PostVO> POST_VO_COLLECTOR = new FullStackCollector<PostVO>() {

        private final ReflectionBasedCollector<Post> postCollector = new ReflectionBasedCollector<Post>();
        private final ReflectionBasedCollector<Category> catCollector = new ReflectionBasedCollector<Category>();
        private final int postCount;
        private final int catCount;
        private final int tagCount;
        private final int shortUserCount;
        {
            postCount = ResourceNavigator.getAttrCount(Post.class);
            catCount = ResourceNavigator.getAttrCount(Category.class);
            tagCount = ResourceNavigator.getAttrCount(Tag.class);
            shortUserCount = COLLECTORS.getShortUserNum();
        }

        @Override
        public PostVO collectEntity(ResultSet set, int shift, String aliasPrefix, PostVO instance)
                throws SQLException, PersistenceCollectorException {

            PostVO result = new PostVO();
            int counter = 0;
            Post post = postCollector.reallyCollect(set, counter,  new Post());
            result.setPost(post);
            counter = counter + postCount;

            User user = COLLECTORS.collectShortUser(set, postCount, new User());
            result.setAuthor(user);
            counter = counter + shortUserCount;

            Category category = catCollector.reallyCollect(set, counter, new Category());
            result.setCategory(category);
            counter += catCount;

            List<Tag> tags = COLLECTORS.createEntityByGroupConcat(set, counter, new Tag());
            result.setTags(new ArrayList<>(tags));

            PostVO.Info info =  result.new Info();
            result.setInfo(info);
            counter = counter + tagCount;

            long read = set.getLong(++counter); //so collectors collect from 0, but really -> from 1
            long favorite = set.getLong(++counter);
            long answers = set.getLong(++counter);
            info.setReadedCount(read);
            info.setFavoriteCount(favorite);
            info.setAnswersCount(answers);
            return result;
        }
     };

    private static final FullStackCollector<PostVO> SINGLE_ENTITY_COLLECTOR = new FullStackCollector<PostVO>(){
        @Override
        public int fillStatement(PreparedStatement statement, int from, Supplier supplier)
                throws SQLException, PersistenceCollectorException {
            Long integer = (Long) supplier.get();
            statement.setLong(1, integer);
            return from;
        }

        @Override
        public PostVO collectEntity(ResultSet set, int shift, String aliasPrefix, PostVO instance)
                throws SQLException, PersistenceCollectorException {
            return POST_VO_COLLECTOR.collectEntity(set, shift, "", instance);
        }
    };

    private static final FullStackCollector<Post> SHORT_COLLECTOR = new FullStackCollector<Post>() {
        @Override
        public Post collectEntity(ResultSet set, int shift, String aliasPrefix, Post instance)
                throws SQLException, PersistenceCollectorException {
            return COLLECTORS.collectShortPost(set, shift);
        }
    };

    @Override
    protected Post getExampleObject() {
        return new Post();
    }

    /**
     *
     * @param definition
     * @return
     * @throws PersistenceSystemException
     */
    @Override
    public List<PostVO> getPostsVo(InitialPostCriteria definition) throws PersistenceSystemException {
        String select = null;
        CriteriaHandler instance = CriteriaHandler.getInstance();
        String query = instance.processCriteria(INITIAL_SELECT, definition);
        return UTIL_HOLDER.getUtilMySql().findAll(query, new PostVO(),  POST_VO_COLLECTOR, null, false);
    }

    /**
     *
     * @param definition
     * @return
     * @throws PersistenceSystemException
     */

    @Override
    public ListCounterResponse<PostVO> getPostsVoListCounter(InitialPostCriteria definition) throws PersistenceSystemException {
        String select = CRITERIA_HANDLER.processCriteria(INITIAL_SELECT, definition);
        return UTIL_HOLDER.getUtilMySql().findAllAndCountRows(select, new PostVO(), POST_VO_COLLECTOR,  null, false);
    }

    /**
     *
     * @param id
     * @return PostVO if entity was found and EmptyVoManager.EMPTY_POST_VO if not
     * @throws PersistenceSystemException
     */
    @Override
    public PostVO getSinglePostVo(Long id) throws PersistenceSystemException {
        List<PostVO> all = UTIL_HOLDER.getUtilMySql().findAll(INITIAL_SELECT_ONE, new PostVO(),
                SINGLE_ENTITY_COLLECTOR, () -> id, false);
        if (all.isEmpty()){
            return EmptyVoManager.EMPTY_POST_VO;
        } else {
            return all.get(0);
        }
    }

    @Override
    public ListCounterResponse<PostVO> getFavoritePostVoListCounter(Long userId) throws PersistenceSystemException {
        return UTIL_HOLDER.getUtilMySql().findAllAndCountRows(INITIAL_SELECT_FAVORITE_USER,
                                          new PostVO(),SINGLE_ENTITY_COLLECTOR, () -> userId, false);
    }

    /**
     * Never return null : in case of absence return empty list
     * @param postCriteria
     * @return List<Post> that contains the found posts.
     * @throws PersistenceSystemException
     */
    @Override
    public List<Post> getShortPostByCriteria(Criteria<Post> postCriteria) throws PersistenceSystemException {
        String select = CRITERIA_HANDLER.processCriteria(SHORT_POST_SELECT, postCriteria);
        return UTIL_HOLDER.getUtilMySql().findAll(select, new Post(), SHORT_COLLECTOR, null, false);
    }

}

