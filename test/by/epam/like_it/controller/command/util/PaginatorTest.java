package by.epam.like_it.controller.command.util;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.*;


public class PaginatorTest {

    private static final PagePaginator paginator = PagePaginator.getInstance();

    @Test
    public void setAccessiblePages() throws Exception {

    }

    /**
     *
     * @throws Exception
     * @see PagePaginator#makeAccessiblePagesArray(int, int)
     */
    @Test
    public void makeAccessiblePagesArrayTest() throws Exception {
        Method method =
                paginator.getClass().getDeclaredMethod("makeAccessiblePagesArray", int.class, int.class);
        method.setAccessible(true);

        int[] invoke = (int[]) method.invoke(paginator, 5, 1); //float criteria_to left
        int[] expected = new int[]{1,2,3,4,5};
        System.out.println(Arrays.toString(invoke));
        assertArrayEquals(expected, invoke);

        invoke = (int[]) method.invoke(paginator, 18, 17); //float criteria_to right
        expected = new int[]{14,15,16,17,18};
        assertArrayEquals(expected, invoke);

        invoke = (int[]) method.invoke(paginator, 18, 15); //normal
        expected = new int[]{13,14,15,16,17};
        assertArrayEquals(expected, invoke);
        System.out.println(Arrays.toString(invoke));
        method.setAccessible(false);
    }

    public void fillAcessibleTest() throws Exception {
        Method fillAcessible =
                paginator.getClass().getDeclaredMethod("fillAcessible", int[].class, int.class);
        fillAcessible.setAccessible(true);
        fillAcessible.setAccessible(false);

    }
//
//    public static void main(String[] args) {
//        System.out.println(int[].class);
//        System.out.println(new int[20].getClass());
//    }

}