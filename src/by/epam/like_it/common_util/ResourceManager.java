package by.epam.like_it.common_util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;


public enum ResourceManager {
    /**
     * Contains database connection initial properties
     */
    DB(Constants.DB_MAIN,  Locale.ROOT),
    /**
     * Contains keys for join tables
     */
    JOIN(Constants.JOIN,  Locale.ROOT),
    /**
     * Contains all bean fields and corresponding table fields
     */
    BEAN_MAPPER(Constants.BEAN_MAPPER,  Locale.ROOT),
    /**
     * Contains all table fields and corresponding bean
     */
    TABLE_MAPPER(Constants.TABLE_MAPPER, Locale.ROOT),
    VIEW_CONFIG(Constants.VIEW_CONFIG,  Locale.ROOT),
    /**
     * Contains all information about restriction on fields of any bean
     */
    VALIDATION_INFO(Constants.VALIDATION, Locale.ROOT),

    FRONTEND_RU(Constants.FRONTEND, new Locale("ru")),
    FRONTEND_EN(Constants.FRONTEND, Locale.ENGLISH),

    PAGE(Constants.PAGE);

    /**
     * Contains paths vo resources
     */
    private static class Constants {
        private static final String DB_MAIN = "resource.db.database";
        private static final String JOIN = "resource.db.joinKey";
        private static final String VALIDATION = "resource.db.validation";
        private static final String BEAN_MAPPER = "resource.db.beanMapping";
        private static final String TABLE_MAPPER = "resource.db.tableMapping";
        private static final String PAGE = "resource.frontend.pageMapping";
        private static final String VIEW_CONFIG = "resource.frontend.viewConfig";
        private static final String FRONTEND = "resource.frontend.locale";
    }

    private ResourceBundle resourceBundle;
    private final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private final ClassLoader loader = Thread.currentThread().getContextClassLoader();

    ResourceManager(String string){
         resourceBundle = ResourceBundle.getBundle(string);
     }

    ResourceManager(String string, Locale locale){
        try {
            resourceBundle = ResourceBundle.getBundle(string, locale, loader);
        } catch (MissingResourceException e){
            LOGGER.fatal("Resources not found!: cause" + e.getCause(), e);
        }
    }

    public String getString(String key){
        return resourceBundle.getString(key);
    }

    public Set<String> keySet() {
        return resourceBundle.keySet();
    }

    public String[] getStringArray(String key) {
        return resourceBundle.getStringArray(key);
    }

    public Object getObject(String key) {
        return resourceBundle.getObject(key);
    }

    public Locale getLocale() {
        return resourceBundle.getLocale();
    }

    public Enumeration<String> getKeys() {
        return resourceBundle.getKeys();
    }

    public boolean containsKey(String key) {
       return resourceBundle.containsKey(key);
    }
}
