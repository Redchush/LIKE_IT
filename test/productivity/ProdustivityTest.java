package productivity;

import by.epam.like_it.common_util.ReflectionUtil;
import by.epam.like_it.common_util.ResourceManager;
import by.epam.like_it.model.bean.Comment;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Contains some tests about choise between some operations
 */
public class ProdustivityTest {

    @Test
    public void compareFindInResourceOrReflection(){
        String tested = "userId";
        long testResource = testResource(tested);
        long testEx =testEx(tested);
        long testSearch = testSearch(tested);
        long testStatic = testSearchStatic(tested);
        System.out.println(testResource);
        System.out.println(testEx);
        System.out.println(testSearch);
        System.out.println(testStatic);
    }

    private long testResource(String tested){
        long start = System.currentTimeMillis();
        boolean b= ResourceManager.BEAN_MAPPER.containsKey("bean.Comment.userId");
        long end = System.currentTimeMillis();
        return end - start;
    }

    private long testEx(String tested){
        long start2 = System.currentTimeMillis();
        boolean b2 = false;
        try {
            Field userId = Comment.class.getDeclaredField(tested);
            b2 = true;
        } catch (NoSuchFieldException e) {
            b2 = false;
        }
        long end2 = System.currentTimeMillis();
        return end2 - start2;
    }

    private long testSearch(String tested){
        long start = System.currentTimeMillis();
        boolean b3 = false;
        Field[] declaredFields = Comment.class.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            if (declaredFields[i].getName().equals(tested)){
                b3 = true;
            }
        }
        long end = System.currentTimeMillis();
        return end -start;
    }

    private long testSearchStatic(String tested){
        long start = System.currentTimeMillis();
        ReflectionUtil.containsField(Comment.class, tested);
        long end = System.currentTimeMillis();
        return end -start;
    }
}
