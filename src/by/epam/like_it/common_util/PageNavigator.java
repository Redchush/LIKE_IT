package by.epam.like_it.common_util;

/**
 * The class for finding correct page
 */

public class PageNavigator {

    public enum Pages{
        INDEX("index"), ERROR("error"), MAIN("main"),
        REGISTRATION("registration"),
        LOGINATION("logination"),

        BREAKAGE_PAT("logination_breakage_case"),
        BREAKAGE_PAT_TO("logination_breakage_case_to"),
        SESSION_EXPIRED("logination_session_expired"),

        CONTROLLER("controller"), SUCCESS("success"),
        POST("post"),
        PROFILE("profile"), USER("user"),
        NOT_FOUND("not_found");



        private String key;

        Pages(String key) {
            this.key = key;
        }
    }
    private static PageNavigator instance;

    private PageNavigator(){}

    public static PageNavigator getInstance(){

        if (instance == null)
            synchronized (PageNavigator.class){
                if (instance == null)
                    instance = new PageNavigator();
            }
        return instance;
    }

    public String getPage(Pages page) {
        return ResourceManager.PAGE.getString(page.key);
    }
}

