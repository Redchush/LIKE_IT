package by.epam.like_it.dao.mysql.util.criteriaChain;

import by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.simple.LikeChain;
import by.epam.like_it.model.criteria_to.core.constriction.LikeConstriction;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;


public class LikeChainTest {


    public static LikeChain chain = LikeChain.getInstance();


    /**
     * String applyFormat(LikeConstriction.LikeType type, String tableName, String fieldName, String value)
     * @throws Exception
     */
    @Test
    public void doSubChain() throws Exception {
        String table = "posts";
        String field = "title";
        String value = "греб";

        String expectedStart = "posts.title LIKE 'греб%'";
        String expectedEnd= "posts.title LIKE '%греб'";
        String expectedContains = "posts.title LIKE '%греб%'";

        Method applyFormat =
                chain.getClass().getDeclaredMethod("applyFormat", LikeConstriction.LikeType.class, String.class, String.class, String
                        .class);

        applyFormat.setAccessible(true);
        String actualStart =
                (String) applyFormat.invoke(chain, LikeConstriction.LikeType.STARTS_WITH, table, field, value);
        String actualEnd =
                (String) applyFormat.invoke(chain, LikeConstriction.LikeType.ENDS_WITH, table, field, value);
        String actualContains =
                (String) applyFormat.invoke(chain, LikeConstriction.LikeType.CONTAINS, table, field, value);

        assertEquals(expectedStart, actualStart);
        assertEquals(expectedEnd, actualEnd);
        assertEquals(expectedContains, actualContains);

        applyFormat.setAccessible(false);

    }

}