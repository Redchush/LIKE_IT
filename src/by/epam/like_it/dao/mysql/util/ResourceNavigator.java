package by.epam.like_it.dao.mysql.util;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.ResourceManager;
import org.apache.logging.log4j.LogManager;

import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static by.epam.like_it.common_util.ResourceManager.BEAN_MAPPER;
import static by.epam.like_it.common_util.ResourceManager.TABLE_MAPPER;

/**
 * Class with static methods : facade for dealing with properties.
 */
public class ResourceNavigator {

    private static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    public static final String BEAN_FORMAT = "bean.%s";
    public static final String BEAN_NUM_FORMAT = "bean.%s.num";

    public static final String TABLE_FORMAT = "table.%s";
    public static final String TABLE_NUM_FORMAT = "table.num.%s";

    public static final String ALL_TABLE_FIELDS_FORMAT = "table.%s.all";
    public static final String ALL_TABLE_FIELDS_QUALIFIED_FORMAT = "table.%s.all.qualified";

    public static final String RELATED_DELIMITER = ".";
    public static final String JOIN_KEY_PATTERN = "%s.%s";

    public static final String BEAN_ALL_TYPES_PATTERN = "bean.%s.types";
    public static final String BEAN_ALL_TYPES_QUALIFIED_PATTERN  = "bean.%s.types.qualified";
    public static final String TABLE_ALL_TYPES_PATTERN = "table.%s.types";

    public static String getRefTable(Class beanClass){
        String key =String.format(BEAN_FORMAT, beanClass.getSimpleName());
        try {
            return BEAN_MAPPER.getString(key);
        } catch (MissingResourceException e){
            LOGGER.error("Try criteria_to find non-existent resource on key " + key);
        }
        return "";
    }

    public static String getReferencedClass(String tableName){
        String result ="";
        String key = String.format(TABLE_FORMAT,tableName);
        try {
            result = TABLE_MAPPER.getString(key);
        } catch (MissingResourceException e){
            LOGGER.error("Try criteria_to find non-existent ReferencedTable by key " + key + " on class tableName " + tableName);
        }
        return result;
    }

    /**
     *
     * @param tableName - name of table in database. May be got by getReferencedClass() method;
     * @return COUNT of fields in db
     * @see #getReferencedClass
     */
    public static int getAttrCount(String tableName){
        String columnCount = TABLE_MAPPER.getString(String.format(TABLE_NUM_FORMAT, tableName));
        return Integer.parseInt(columnCount);
    }

    /**
     *
     * @param beanClass - bean class
     */
    public static int getAttrCount(Class beanClass) {
        String referencedTable = getRefTable(beanClass);
        return getAttrCount(referencedTable);
    }

    public static String getALLFields(String tableName, boolean qualifed){
        String key = qualifed ? String.format(ALL_TABLE_FIELDS_QUALIFIED_FORMAT, tableName)
                              : String.format(ALL_TABLE_FIELDS_FORMAT, tableName);
        return TABLE_MAPPER.getString(key);
    }

    public static List<String> getALLFieldsList(String tableName, boolean qualifed){
        String[] split = getALLFields(tableName, qualifed).split(" ");
        return Arrays.asList(split);
    }

    public static Stream<String> getALLFieldsStream(String tableName, boolean qualifed){
        return Stream.of(getALLFields(tableName, qualifed).split(" "));
    }

    public static String getAllBeanTypes(String className, boolean qulified) {
        return qulified ? ResourceManager.BEAN_MAPPER.getString(String.format(BEAN_ALL_TYPES_QUALIFIED_PATTERN, className))
                        : ResourceManager.BEAN_MAPPER.getString(String.format(BEAN_ALL_TYPES_PATTERN, className));
    }
    public static List<String> getALLTypessList(String tableName, boolean qualifed){
        String[] split = getAllBeanTypes(tableName, qualifed).split(" ");
        return Arrays.asList(split);
    }

    public static String getAllTableTypes(String tableName){
        return ResourceManager.TABLE_MAPPER.getString(String.format(TABLE_ALL_TYPES_PATTERN, tableName));
    }

    public static List<Integer> getAllTableTypesList(String tableName){
        return Arrays.stream(getAllTableTypes(tableName).split(" ")).map(s -> Integer.parseInt(s))
                     .collect(Collectors.toList());
    }


    public static String getReferencedBeanField(String tableName, String fieldName){
        try{
           return ResourceManager.TABLE_MAPPER.getString("table." + tableName + "." + fieldName);
        } catch (MissingResourceException e){
            return "";
        }
    }

    public static String getRefTableField(String className, String fieldName){
        return ResourceManager.BEAN_MAPPER.getString("bean." + className + "." + fieldName);
    }


    public static String getJoinOn(String tableFirst, String tableSecond){
        String key ="";
        try{
            key = String.format(JOIN_KEY_PATTERN, tableFirst, tableSecond);
//            return ResourceManager.JOIN.getString(key);
            return null;
        } catch (MissingResourceException e){
            LOGGER.error("Try criteria_to find non-existent join by key " + key + " of tables : " + tableFirst + ", " +
                    tableSecond);
        }
        return "";
    }

}
