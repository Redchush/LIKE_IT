package by.epam.like_it.dao.mysql.collector;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.exception.persistence.system.PersistenceCollectorException;
import by.epam.like_it.model.bean.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;


public class BeanCollectors {

    private static Logger logger = LogManager.getLogger(ClassName.getClassName());

    private static BeanCollectors instance;

    private BeanCollectors(){}

    public static BeanCollectors getInstance(){

        if (instance == null)
            synchronized (BeanCollectors.class){
                if (instance == null)
                    instance = new BeanCollectors();
            }
        return instance;
    }

    private Method methodNameConstructor(String name) throws NoSuchMethodException {

        Method result = this.getClass().getMethod(String.format("create%s", name)
                , ResultSet.class, int.class);
        return result;
    }



    public <T> List<T> createEntityList(ResultSet set, int shift, T instanceToOverride)
            throws SQLException {
        List<T> result = new ArrayList<>();
        while (set.next()) {
            T entity = createEntity(set, shift, instanceToOverride);
            result.add(entity);
        }
        return result;
    }

    /**
     * This method exist criteria_to maintain access from generic classes
     * @param set
     * @param shift
     * @param instance
     * @param <T>
     * @return
     * @throws SQLException
     */

    public <T> T createEntity(ResultSet set,int shift, T instance) throws SQLException {
        throw new SQLException();
    }

    /**
     * This method exist criteria_to maintain access from generic classes
     * @param statement
     * @param from
     * @param entity
     * @param <T>
     * @return
     * @throws SQLException
     */

    public <T> int fillStatement(PreparedStatement statement, int from, T entity) throws SQLException {
        throw new SQLException();
    }

    public User collectShortUser(ResultSet set, int shift, User instance)
            throws SQLException, PersistenceCollectorException {
        int counter = shift + 1;
        Long id = set.getLong(counter++);
        Byte roleId = set.getByte(counter++);
        String login = set.getString(counter++);
        String fotoPath = set.getString(counter++);
        if (set.wasNull()){
            fotoPath = null;
        }
        Boolean isBanned = set.getBoolean(counter);
        instance.setId(id);
        instance.setRoleId(roleId);
        instance.setLogin(login);
        instance.setFotoPath(fotoPath);
        instance.setBanned(isBanned);
        return instance;
    }

    public int getShortUserNum(){
        return 5;
    }

    public Post collectShortPost(ResultSet set, int shift)
            throws SQLException, PersistenceCollectorException {
        int counter = shift + 1;
        Long id = set.getLong(counter++);
        String title = set.getString(counter);
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        return post;
    }

    public int getShortPostNum(){
        return 2;
    }


    //    public AnswerVO(Long id, Long userId, Long postId, String content, Timestamp createdDate, Timestamp updatedDate, Boolean banned)
    public Answer createEntity(ResultSet set, int shift, Answer answer) throws SQLException {
        long id = set.getLong(1 + shift);
        if (id == 0){
            return null;
        }
        long parentUserId = set.getLong("user_id");
        long postId = set.getLong("post_id");
        String content = set.getString("content");
        Timestamp createdDate = set.getTimestamp("created_date");
        Timestamp updatedDate = set.getTimestamp("updated_date");
        boolean isBanned = set.getBoolean("banned");
        return new Answer(id, parentUserId, postId, content, createdDate, updatedDate, isBanned);
    }

    /**
     * All fill statements ignore the id parameter!
     * @param statement
     * @param from
     * @param entity
     * @return
     * @throws SQLException
     */

    public int fillStatement(PreparedStatement statement, int from, Answer entity) throws SQLException {

        Long user_id = entity.getUserId();
        statement.setLong(from++, user_id);

        Long post_id = entity.getPostId();
        statement.setLong(from++, post_id);

        String content = entity.getContent();
        statement.setString(from++, content);

        Timestamp created_date = entity.getCreatedDate();
        statement.setTimestamp(from++, created_date);
        Timestamp updated_date = entity.getUpdatedDate();
        statement.setTimestamp(from++, updated_date);

        boolean isBanned = entity.getBanned();
        statement.setBoolean(from++, isBanned);
        return from;
    }

    //    public CategoryContent(Long id, String title, Integer languageId, String     description, Integer parentCategory,
//                    Timestamp createdDate, Boolean published)
    public Category createEntity(ResultSet set, int shift, Category category) throws SQLException {

        long id = set.getLong(1 + shift);
        if (id == 0){
            return null;
        }
        String title = set.getString("title");
        byte languageId = set.getByte("language_id");

        String description = set.getString("description");
        long parentId = set.getLong("parent_category");
        Timestamp createdDate = set.getTimestamp("created_date");
        boolean isPublished = set.getBoolean("published");

        Category entity = new Category(id, title, languageId, description, parentId, createdDate,  isPublished);
        return entity;
    }

    public int fillStatement(PreparedStatement statement, int from, Category entity) throws SQLException {
        String title = entity.getTitle();
        statement.setString(from++, title);

        byte language_id = entity.getLanguageId();
        statement.setByte(from++, language_id);

        String description = entity.getDescription();
        statement.setString(from++, description);

        Long categoryId = entity.getParentCategory();
        statement.setLong(from++, categoryId);
        Timestamp created_date = entity.getCreatedDate();
        statement.setTimestamp(from++, created_date);
        boolean published = entity.getPublished();
        statement.setBoolean(from++, published);
        return from;
    }


    //    public Comment(Long id, Long userId, Long answerId, String content, Timestamp createdDate,
//                   Timestamp updatedDate, Boolean banned) {
    public Comment createEntity(ResultSet set, int shift, Comment comment) throws SQLException {
        Long id = set.getLong(1 + shift);
        if (id == 0){
            return null;
        }
        Long parentUserId = set.getLong("user_id");
        Long parentPostId = set.getLong("answers_id");
        String content = set.getString("content");
        Timestamp createdDate = set.getTimestamp("created_date");
        Timestamp updatedDate = set.getTimestamp("updated_date");
        boolean isBanned = set.getBoolean("banned");
        Comment entity = new Comment(id, parentUserId, parentPostId, content, createdDate, updatedDate, isBanned);
        return entity;
    }

    public int fillStatement(PreparedStatement statement, int from,  Comment entity) throws SQLException {

        Long user_id = entity.getUserId();
        statement.setLong(from++, user_id);

        long answers_id = entity.getAnswerId();
        statement.setLong(from++, answers_id);

        String content = entity.getContent();
        statement.setString(from++, content);

        Timestamp created_date = entity.getCreatedDate();
        statement.setTimestamp(from++, created_date);

        Timestamp updated_date = entity.getUpdatedDate();
        statement.setTimestamp(from++, updated_date);

        boolean isBanned = entity.getBanned();
        statement.setBoolean(from++, isBanned);
        return from;
    }

    //    public FavoriteUserPost(Long id, Long userId, Long postId, String comment)
    public FavoriteUserPost createFavoritePost(ResultSet set, int shift, FavoriteUserPost post) throws SQLException {
        long id = set.getInt(1+shift);
        if (id == 0){
            return null;
        }
        long userId = set.getLong("user_id");
        long postId = set.getLong("post_id");
        String comment = set.getString("comment");
        return new FavoriteUserPost(id, userId, postId, comment);
    }

    public int fillStatement(PreparedStatement statement,  int from, FavoriteUserPost entity) throws SQLException {

        Long user_id = entity.getUserId();
        statement.setLong(from++, user_id);

        Long post_id = entity.getPostId();
        statement.setLong(from++, post_id);

        String comment = entity.getComment();
        statement.setString(from++, comment);
        return from;
    }

    //    public PostVO(Long id, Long userId, Long categoryId, String title, String content, Timestamp createdDate, Timestamp updatedDate, Boolean banned) {
    public Post createEntity(ResultSet set, int shift, Post post) throws SQLException {

        Long id = set.getLong(1 + shift);
        if (id == 0){
            return null;
        }
        Long parentUserId = set.getLong("user_id");
        Long parentCategoryId = set.getLong("category_id");
        String title = set.getString("title");
        String content = set.getString("content");
        Timestamp createdDate = set.getTimestamp("created_date");
        Timestamp updatedDate = set.getTimestamp("updated_date");
        boolean isBanned = set.getBoolean("banned");

        return new Post(id, parentUserId, parentCategoryId, title, content, createdDate, updatedDate, isBanned);
    }

    public int fillStatement(PreparedStatement statement, int from,  Post entity) throws SQLException {
        long user_id = entity.getUserId();
        statement.setLong(from++, user_id);

        long category_id = entity.getCategoryId();
        statement.setLong(from++, category_id);

        String title = entity.getTitle();
        statement.setString(from++, title);

        String content = entity.getContent();
        statement.setString(from++, content);

        Timestamp created_date = entity.getCreatedDate();
        statement.setTimestamp(from++, created_date);

        Timestamp updated_date = entity.getUpdatedDate();
        statement.setTimestamp(from++, updated_date);

        boolean isBanned = entity.getBanned();
        statement.setBoolean(from++, isBanned);
        return from;
    }

    //    public Rating(Long id, Long userId, Long answerId, Byte rating, Timestamp createdDate, Timestamp updatedDate, Boolean banned)
    public Rating createEntity(ResultSet set, int shift, Rating rating) throws SQLException {

        long id = set.getLong(1 + shift);
        if (id == 0){
            return null;
        }
        Long parentAnswerId = set.getLong("answer_id");
        Long parentUserId = set.getLong("user_id");
        Byte raiting = set.getByte("rating");

        Timestamp createdDate = set.getTimestamp("created_date");
        Timestamp updated_date = set.getTimestamp("updated_date");
        boolean isBanned = set.getBoolean("banned");

        return new Rating(id, parentUserId, parentAnswerId,  raiting, createdDate, updated_date, isBanned);
    }

    public int fillStatement(PreparedStatement statement,  int from, Rating entity) throws SQLException {
        Long user_id = entity.getUserId();
        statement.setLong(from++, user_id);

        long answer_id = entity.getAnswerId();
        statement.setLong(from++, answer_id);

        int rating = entity.getRating();
        statement.setInt(from++, rating);

        Timestamp created_date = entity.getCreatedDate();
        statement.setTimestamp(from++, created_date);
        Timestamp updated_date = entity.getUpdatedDate();
        statement.setTimestamp(from++, updated_date);

        boolean isBanned = entity.getBanned();
        statement.setBoolean(from++, isBanned);
        return from;
    }

//    public Role(Integer id, String name)

    public Role createEntity(ResultSet set, int shift, Role role) throws SQLException {
        int id = set.getByte(1 + shift);
        if (id == 0){
            return null;
        }
        String name = set.getString("name");
        return new Role(id, name);
    }

    public int fillStatement(PreparedStatement statement, int from, Role entity) throws SQLException {
        String name = entity.getName();
        statement.setString(from++, name);
        return from;
    }

    //    public Tag(Long id, String name)
    public Tag createEntity(ResultSet set, int shift, Tag tag) throws SQLException {
        long id = set.getLong(1 + shift);
        if (id == 0){
            return null;
        }
        String name = set.getString("name");
        return new Tag(id, name);
    }

    public int fillStatement(PreparedStatement statement,int from, Tag entity) throws SQLException {
        String name = entity.getName();
        statement.setString(from++, name);
        return from;
    }

    /**
     * Collect tags list from two group concat - ed columns. First column - id-s, second - names
     * @param set - Result Set as source
     * @param from - from which position in set start collect entity
     * @param entity - example
     * @return List<Tag> extracted from two concated columns
     * @throws SQLException - skipped from db
     */

    public List<Tag> createEntityByGroupConcat(ResultSet set, int from, Tag entity) throws SQLException{
        List<Tag> tags = new ArrayList<>();
        String string = set.getString(++from);
        if (string == null || string.equals("null")){
            return tags;
        }
        String[] split =string.split(",");
        String names = set.getString(++from);
        String[] splittedNames = names.split(",");
        for (int i = 0; i < split.length; i++) {
            String id = split[i].trim();
            long l = Long.parseLong(id);
            String name = splittedNames[i].trim();
            Tag tag = new Tag(l, name);
            tags.add(tag);
        }
        return tags;
    }

//(Long id, Long roleId, Integer languageId, String login, String password, String email, String lastName, String firstName, String about_me, String foto_path, Timestamp createdDate, Timestamp updatedDate, Boolean banned)

    public User createEntity(ResultSet set, int shift, User user) throws SQLException {
        Long id = set.getLong(1 + shift);
        Byte role = set.getByte("role_id");
        Byte languageId= set.getByte("language_id");
        String login = set.getString("login");
        String password = set.getString("password");
        String email = set.getString("email");

        String lastName = set.getString("last_name");
        String firstName = set.getString("first_name");
        String about = set.getString("about_me");
        String foto = set.getString("foto_path");

        Timestamp createdDate = set.getTimestamp("created_date");
        Timestamp updatedDate = set.getTimestamp("updated_date");
        boolean isBanned = set.getBoolean("banned");

        User entity = new User(id, role,languageId, login, password, email, lastName,
                firstName, about,foto, createdDate, updatedDate, isBanned);
        return entity;
    }
    //(Long id, Long roleId, Integer languageId, String login, String password, String email, String lastName, String firstName, String about_me, String foto_path, Timestamp createdDate, Timestamp updatedDate, Boolean banned)

    public int fillStatement(PreparedStatement statement, int from, User entity) throws SQLException {

        Byte role_id = entity.getRoleId();
        statement.setByte(from++, role_id);

        Byte language_id = entity.getLanguageId();
        statement.setByte(from++, language_id);

        String login = entity.getLogin();
        statement.setString(from++, login);

        String password = entity.getPassword();
        statement.setString(from++, password);
        String email = entity.getEmail();
        statement.setString(from++, email);

        String lastName = entity.getLastName();
        statement.setString(from++, lastName);
        String firstName = entity.getFirstName();
        statement.setString(from++, firstName);

        String aboutMe = entity.getAboutMe();
        statement.setString(from++, aboutMe);
        String foto_path = entity.getFotoPath();
        statement.setString(from++,foto_path);

        Timestamp created_date = entity.getCreatedDate();
        statement.setTimestamp(from++, created_date);
        Timestamp updated_date = entity.getUpdatedDate();
        statement.setTimestamp(from++, updated_date);

        boolean isBanned = entity.getBanned();
        statement.setBoolean(from++, isBanned);
        return from;
    }

    //    public List<T> ivoke(ResultSet set, CriteriaMySql criteria)
//            throws ReflectiveOperationException, SQLException {
//
//        List<String> refTables = criteria.getRefTables();
//        String baseTable = criteria.getBaseTable();
//        String baseClass = ResourceNavigator.getReferencedClass(baseTable);
//        refTables.create(0, baseTable);
//        Map<T, T> map = new HashMap<T, T>();
//
//        while (set.next()){
//            int counter = 0;
//            int shift= 0;
//            T main = null;
//            for (String name: refTables){
//                String relationTable = "";
//                String clazz = ResourceNavigator.getReferencedClass(name);
//                String possibleRef = ResourceNavigator.getAnotherTableByTable(baseTable, clazz);
//
//                if (!possibleRef.skipIfEmpty()){
//                    clazz = ResourceNavigator.getReferencedClass(possibleRef);
//                    relationTable=name;
//                }
//                Method need = methodNameConstructor(clazz);
//                Object invoke = need.invoke(this, set, shift);
//
//                if (counter == 0){
//                   T temp = map.putIfAbsent((T) invoke,(T) invoke);
//                   main = (temp == null) ? (T) invoke : temp;
//                } else {
//                    Class refClazz = invoke.getClass();
//                    attachDependant((T) main, invoke, relationTable, refClazz);
//                }
//                int addToShift = ResourceNavigator.getAttrCount(name);
//                shift+= addToShift;
//                counter++;
//            }
//        }
//        return new ArrayList<T>(map.keySet());
//    }

//    private <D> void  attachDependant(Object mainObj, Object invoke, String relTable,
//                                      Class<D> refCazz)
//            throws ReflectiveOperationException {
//
//        String link = ResourceNavigator.getKeyForClassLink(mainObj.getClass(), invoke.getClass(), relTable);
//        D attachment = (D) invoke;
//        T main = (T) mainObj;
//        if (!link.skipIfEmpty()){
//            Method method = main.getClass().getMethod(link, invoke.getClass());
//            method.invoke(main, attachment);
//        }
//    }
}
