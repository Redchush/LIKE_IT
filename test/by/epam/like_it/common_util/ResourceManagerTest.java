package by.epam.like_it.common_util;

import org.junit.Test;

import static org.junit.Assert.*;


public class ResourceManagerTest {
    @Test
    public void getLocale() throws Exception {

    }

    private static final String JOIN = "resource.db.written.joinKey";
    private static final String DB_MAIN = "resource.db.database";
    private static final String BEAN_MAPPER = "resource.db.written.beanMapping";

    @Test
    public void containsKey() throws Exception {
        ResourceManager join = ResourceManager.JOIN;
    }

//    public static final ResourceBundle rootOnly = ResourceBundle.getBundle("resource.db.written.beanMapping",
//            new ResourceBundle.Control() {
//                @Override
//                public List<Locale> getCandidateLocales(String name,
//                                                        Locale locale) {
//                    return Collections.singletonList(Locale.ROOT);
//                }
//    });

    @Test
    public void getString() throws Exception {
        String expected = "[A-Za-zА-Яа-я0-9_.-]{3,255}";
        String string = ResourceManager.VALIDATION_INFO.getString("User.firstName.pattern");
        assertEquals(expected, string);

        String unexpected =";;;";
        boolean matches = unexpected.matches(string);
        assertFalse(matches);

        String unexpected2 ="ala_яяя'";
        boolean matches2 = unexpected2.matches(string);
        assertFalse(matches2);

        String unexpected3 ="я.я";
        boolean matches3 = unexpected3.matches(string);
        assertTrue(matches3);


    }

}