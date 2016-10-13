package by.epam.like_it.dao.mysql.util.criteriaChain;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;


public class ConstrictionChainTest {
   private static String example = "WHERE posts.banned = FALSE\n" +
                                   "GROUP BY posts.id \n";
   private static String expected = "WHERE posts.banned = FALSE\n" +
            "TEST GROUP BY posts.id \n";

    @Test
    public void adjustSubPartTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ConstrictionChain instance = ConstrictionChain.getInstance();
        Method adjustSubPart = instance.getClass().getDeclaredMethod("adjustSubPart", StringBuilder.class,
                StringBuilder.class);
        adjustSubPart.setAccessible(true);
        StringBuilder builder = new StringBuilder(example);
        adjustSubPart.invoke(instance, builder, new StringBuilder("TEST "));
        adjustSubPart.setAccessible(false);
        assertEquals(expected,builder.toString());
    }

}