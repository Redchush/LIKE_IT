package by.epam.like_it.controller.command.util;

import org.junit.Test;


public class UrlMakerTest {
    @Test
    public void changeRequestURL() throws Exception {
        String src = "FrontController";
        String target = "log_in";
        StringBuffer buffer = new StringBuffer("http://localhost:8080/FrontController?command=logination");
        changeRequestURLExample(buffer, src, target);
        System.out.println(buffer);


    }

    private void changeRequestURLExample( StringBuffer buffer, String replacement, String target) {
        int i = buffer.indexOf(replacement);
        int last = i + replacement.length();
        buffer.replace(i, last, target);
        System.out.println(i);
        System.out.println(last);
    }

}