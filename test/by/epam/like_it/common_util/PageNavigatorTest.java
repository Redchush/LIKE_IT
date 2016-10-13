package by.epam.like_it.common_util;

import org.junit.Test;


public class PageNavigatorTest {
    @Test
    public void getPage() throws Exception {
        String page = PageNavigator.getInstance().getPage(PageNavigator.Pages.INDEX);
        System.out.println(page);
    }

}