package by.epam.like_it.dao.mysql.util;

import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.criteria_to.core.Limit;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;
import by.epam.like_it.model.criteria_to.core.constriction.InConstriction;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;
import by.epam.like_it.model.criteria_to.facade.PostTagCriteria;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class CriteriaHandlerTest {



    private static String test = "SELECT posts.id, posts.user_id, posts.category_id, posts.title, posts.content, posts.created_date, posts.updated_date, posts.banned\n" +
            ", categories.id, categories.title, categories.language_id, categories.description, categories.parent_category, categories.created_date, categories.published\n" +
            ", GROUP_CONCAT(DISTINCT tags.id)\n" +
            ", GROUP_CONCAT(DISTINCT tags.name)\n" +
            ", COUNT(DISTINCT readed_posts.user_id) AS counter__readed_posts__user_id\n" +
            ", COUNT(DISTINCT favorite_users_posts.user_id) AS counter__favorite_users_posts__user_id\n" +
            ", COUNT(DISTINCT answers.id) AS counter__answers__id\n" +
            "FROM posts\n" +
            "LEFT JOIN categories ON categories.id = posts.category_id \n" +
            "LEFT JOIN posts_tags ON posts_tags.post_id = posts.id\n" +
            "LEFT JOIN tags ON tags.id = posts_tags.tag_id\n" +
            "LEFT JOIN readed_posts ON readed_posts.post_id = posts.id\n" +
            "LEFT JOIN favorite_users_posts\n" +
            "ON favorite_users_posts.post_id = posts.id\n" +
            "LEFT JOIN answers \n" +
            "ON answers.post_id = posts.id AND answers.banned = FALSE\n" +
            "GROUP BY posts.id \n";

    private static String expected = "SELECT posts.id, posts.user_id, posts.category_id, posts.title, posts.content, posts.created_date, posts.updated_date, posts.banned\n" +
            ", categories.id, categories.title, categories.language_id, categories.description, categories.parent_category, categories.created_date, categories.published\n" +
            ", GROUP_CONCAT(DISTINCT tags.id)\n" +
            ", GROUP_CONCAT(DISTINCT tags.name)\n" +
            ", COUNT(DISTINCT readed_posts.user_id) AS counter__readed_posts__user_id\n" +
            ", COUNT(DISTINCT favorite_users_posts.user_id) AS counter__favorite_users_posts__user_id\n" +
            ", COUNT(DISTINCT answers.id) AS counter__answers__id\n" +
            "FROM posts\n" +
            "LEFT JOIN categories ON categories.id = posts.category_id \n" +
            "LEFT JOIN posts_tags ON posts_tags.post_id = posts.id\n" +
            "LEFT JOIN tags ON tags.id = posts_tags.tag_id\n" +
            "LEFT JOIN readed_posts ON readed_posts.post_id = posts.id\n" +
            "LEFT JOIN favorite_users_posts\n" +
            "ON favorite_users_posts.post_id = posts.id\n" +
            "LEFT JOIN answers \n" +
            "ON answers.post_id = posts.id AND answers.banned = FALSE\n" +
            "WHERE \n" +
            "posts.banned = 'false'\n" +
            "GROUP BY posts.id \n" +
            "ORDER BY counter__readed_posts__user_id DESC\n"+
            "LIMIT 0, 50 ";


    private static String filledCriteriaForTEST = "SELECT posts.id, posts.user_id, posts.category_id, posts.title, posts.content, posts.created_date, posts.updated_date, posts.banned\n" +
            ", categories.id, categories.title, categories.language_id, categories.description, categories.parent_category, categories.created_date, categories.published\n" +
            ", GROUP_CONCAT(DISTINCT tags.id)\n" +
            ", GROUP_CONCAT(DISTINCT tags.name)\n" +
            ", COUNT(DISTINCT readed_posts.user_id) AS counter__readed_posts__user_id\n" +
            ", COUNT(DISTINCT favorite_users_posts.user_id) AS counter__favorite_users_posts__user_id\n" +
            ", COUNT(DISTINCT answers.id) AS counter__answers__id\n" +
            "FROM posts\n" +
            "LEFT JOIN categories ON categories.id = posts.category_id \n" +
            "LEFT JOIN posts_tags ON posts_tags.post_id = posts.id\n" +
            "LEFT JOIN tags ON tags.id = posts_tags.tag_id\n" +
            "LEFT JOIN readed_posts ON readed_posts.post_id = posts.id\n" +
            "LEFT JOIN favorite_users_posts\n" +
            "ON favorite_users_posts.post_id = posts.id\n" +
            "LEFT JOIN answers \n" +
            "ON answers.post_id = posts.id AND answers.banned = FALSE\n" +
            "WHERE \n" +
            "posts.banned = 'false'\n" +
            "GROUP BY posts.id";

    private static CriteriaHandler HANDLER;

    @BeforeClass
    public static void logIn(){
        HANDLER = CriteriaHandler.getInstance();
    }

    @Test
    public void processSingleEqPartTest() throws Exception {
        EqConstriction<Post, String> eqs = new EqConstriction<Post, String>(Post.class, "id", Collections.singleton("?"));
        String s = HANDLER.processSinglePart(test, CriteriaHandler.Mode.USUAL, eqs);
        System.out.println(s);
    }


    @Test
    public void processCriteriaTest() throws Exception {
        InitialPostCriteria criteria = new InitialPostCriteria();
        criteria.setLimit(new Limit(0, 50));
        System.out.println(criteria);

        CriteriaHandler instance = CriteriaHandler.getInstance();
        String actual = instance.processCriteria(test, criteria);

        assertEquals(expected, actual);
        System.out.println(actual);
    }

    @Test
    public void processCriteriaWithOrs() throws Exception {
        InitialPostCriteria criteria = new InitialPostCriteria();
        EqConstriction<Tag, String> tagConstriction = new EqConstriction<>();
        Set<String> tagSet = new HashSet<>();
        tagSet.add("2");
        tagSet.add("63");

        tagConstriction.setBeanClass(Tag.class);
        tagConstriction.setField("id");
        tagConstriction.setOrValues(tagSet);
        criteria.putTagConstriction(tagConstriction);

        String actual = HANDLER.processCriteria(test, criteria);
        String wherePart = "posts.banned = 'false' AND (tags.id = '2' OR tags.id = '63')";
        assertTrue(actual.contains(wherePart));
        System.out.println(actual);
    }

    @Test
    public void processCriteriaWithExistingWheare() throws Exception {
        InitialPostCriteria criteria = new InitialPostCriteria();
        EqConstriction<Tag, String> tagConstriction = new EqConstriction<>();
        Set<String> tagSet = new HashSet<>();
        tagSet.add("2");
        tagSet.add("63");

        tagConstriction.setBeanClass(Tag.class);
        tagConstriction.setField("id");
        tagConstriction.setOrValues(tagSet);
        criteria.putTagConstriction(tagConstriction);

        String actual = HANDLER.processCriteria(filledCriteriaForTEST, criteria);
//        String wherePart = "posts.banned = 'false' AND (tags.id = '2' OR tags.id = '63')";
//        assertTrue(actual.contains(wherePart));
        System.out.println(actual);
    }


    String tagSelect =  "SELECT SQL_CALC_FOUND_ROWS tags.id, tags.name, COUNT(posts_tags.post_id) AS " +
            "counter__posts_tags__post_id\n" +
            "FROM tags\n" +
            "LEFT JOIN `posts_tags`\n"  +
            "ON tags.id = posts_tags.tag_id\n" +
            "GROUP BY tags.id\n";
    @Test
    public void processSinglePartTest() throws Exception {
        Limit limit = new Limit(0, 20);
        String result = HANDLER.processSinglePart(tagSelect, limit).trim();
        String expected = tagSelect + "LIMIT 0, 20";
        assertEquals(expected, result);

    }

    private static final String SELECT_POST_TAG = "SELECT SQL_CALC_FOUND_ROWS tags.id, tags.name, COUNT(posts_tags.post_id) AS " +
            "counter__posts_tags__post_id\n" +
            "FROM tags\n" +
            "LEFT JOIN `posts_tags`\n"  +
            "ON tags.id = posts_tags.tag_id\n" +
            "GROUP BY tags.id\n";


    @Test
    public void processCriteria() throws Exception {
        PostTagCriteria criteria = new PostTagCriteria();
        criteria.setLimit(new Limit(0, 20));
        List<Long> longs = Arrays.asList(1L, 2L, 3L, 4L);

        InConstriction<Tag,Long> inConstriction = new InConstriction<Tag,Long>(Tag.class, "id",
                new HashSet<Long>(longs));
        criteria.putConstriction(inConstriction);

        String s = HANDLER.processCriteria(SELECT_POST_TAG, criteria);
        System.out.println(s);
        String partExpected = "tags.id IN ('1', '2', '3', '4')";
        assertTrue(s.contains(partExpected));
    }
}