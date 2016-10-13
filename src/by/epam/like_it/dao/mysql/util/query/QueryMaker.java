package by.epam.like_it.dao.mysql.util.query;


import by.epam.like_it.common_util.ReflectionUtil;
import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class QueryMaker {

    protected static final String SELECT = "SELECT ";
    private static final String DELETE = "DELETE FROM ";
    private static final String UPDATE = "UPDATE ";
    private static final String INSERT = "INSERT INTO ";

    private static final String SELECT_PATTERN = "SELECT %s \nFROM %s\n";

    public static final String FROM = "FROM ";
    private static final String VALUES = "\nVALUES ";
    private static final String SET = "\nSET ";
    private static final String DEFAULT = "DEFAULT";
    private static final String APPEND_BY_ID = "\nWHERE id = ?";
    private static final String APPEND_BY_ID_FILLED = "\nWHERE id = ";

    private static final String APPEND_SET_BANNED = "\nSET banned = true";
    private static final String APPEND_AND_USER_ID = "\nAND user_id = ?";
    private static final String ON_DUPLICATE_UPDATE = "\nON DUPLICATE KEY UPDATE ";

    protected static final String WHERE_PREFIX = "WHERE ";
    protected static final String PATTERN_LIMIT_TO = "LIMIT %d, %d";
    protected static final String PATTERN_LIMIT_FROM_TO = "LIMIT %d, %d ";
    protected static final String PATTERN_LIKE = "%s LIKE '%%s%' \n";
    protected static final String PATTERN_EQ_WHOLE_ENTITY = "%s='%s' ";
    protected static final String PATTERN_EQ_FIELD = "%s.%s='%s' ";
    protected static final String PATTERN_ORDER = "ORDER BY %s.%s %s ";
    protected static final String PATTERN_DELIMITER = "\n%s ";


    public static class Select{

        public static String getSelectByObject(Object entity) {
            Stream<String> notNullFieldsStream = ReflectionUtil.getNotNullFieldsStream(entity);
            return getSelectByObject(entity, notNullFieldsStream);
        }

        public static String getSelectByObject(Object entity, Stream<String> notNullFields){
            String selectAll = getSelectAll(entity.getClass());
            String wherePart = notNullFields.map(s -> ResourceNavigator
                    .getRefTableField(entity.getClass().getSimpleName(), s) + "=? ")
                                            .collect(Collectors.joining(" AND ", "WHERE ", "\n"));
            if (wherePart.endsWith("WHERE \n")){
                return selectAll;
            }
            return  selectAll + wherePart;
        }

        public static String getSelectAll(Class targetClass) {
            String tableName = ResourceNavigator.getRefTable(targetClass);
            Stream<String> fieldsStream = ResourceNavigator.getALLFieldsStream(tableName, true);
            String fieldsString = fieldsStream.collect(Collectors.joining(", "));
            return String.format(SELECT_PATTERN, fieldsString, tableName);
        }

        public static String getSelectPartOfQuery(Class clazz, boolean withSelect){
            String tableName = ResourceNavigator.getRefTable(clazz);
            return withSelect ? SELECT + ResourceNavigator.getALLFieldsStream(tableName, true)
                                                          .collect(Collectors.joining(", "))
                              : SELECT + ResourceNavigator.getALLFieldsStream(tableName, true)
                                                          .collect(Collectors.joining(", "));
        }

        public static String getSelectById(Class targetClass) {
            String queryAll = getSelectAll(targetClass);
            return queryAll + APPEND_BY_ID;
        }

        protected static String getSelectById(String selectAll){
            return selectAll + APPEND_BY_ID;
        }
    }

    public static class Update{

        public static String getUpdateByNotNullFields(RealEntity entity) {
            Stream<String> notNullFieldsExceptIdStream = ReflectionUtil.getNotNullFieldsExceptIdStream(entity);
            return getUpdateByNotNullFields(entity, notNullFieldsExceptIdStream);

        }

        protected static String getUpdateByNotNullFields(RealEntity entity, Stream<String> notNullFields) {
            String tableName = ResourceNavigator.getRefTable(entity.getClass());
            String setPartOfQuery = notNullFields.map(s -> ResourceNavigator
                    .getRefTableField(entity.getClass().getSimpleName(), s) + "=? ")
                    .collect(Collectors.joining(", ", " SET ", APPEND_BY_ID));
            StringBuilder query = new StringBuilder(UPDATE);
            query.append(tableName)
                 .append(setPartOfQuery);
            return query.toString();
        }

        /**
         * include in prepareStatement id , so it is unknown what type will be at runtime
         */
        @NotNull
        public static String getUpdateResponsible(Class clazz) {
            return getCoreUpdate(clazz).append(APPEND_AND_USER_ID).toString();
        }

        /**
         * include in prepareStatement id , so it is unknown what type will be at runtime
         */
        @NotNull
        public static String getUpdateByAllFields(Class clazz) {
           return getCoreUpdate(clazz).toString();
        }

        private static StringBuilder getCoreUpdate(Class clazz){
            String tableName = ResourceNavigator.getRefTable(clazz);
            List<String> fields = ResourceNavigator.getALLFieldsList(tableName, true);
            String setPartOfQuery = modifyAndCollectPattern(fields, ", ", "%s = ?", 1);

            StringBuilder query = new StringBuilder(UPDATE);
            query.append(tableName)
                 .append(SET)
                 .append(setPartOfQuery)
                 .append(APPEND_BY_ID);
            return query;
        }
    }


    public static class Delete{
        @NotNull
        public static String getDeleteById(Class targetClass) {
            StringBuilder result = new StringBuilder(DELETE);
            result.append(ResourceNavigator.getRefTable(targetClass))
                  .append(APPEND_BY_ID);
            return result.toString();
        }

        @NotNull
        public static String getDeleteByBan(Class targetClass) {
            return getCoreDeleteByBan(targetClass).toString();
        }

        @NotNull
        public static String getDeleteResponsible(Class targetClass) {
            StringBuilder result = getCoreDeleteByBan(targetClass).append(APPEND_AND_USER_ID);
            return result.toString();
        }

        private static StringBuilder getCoreDeleteByBan(Class targetClass){
            StringBuilder result = new StringBuilder(UPDATE);
            result.append(ResourceNavigator.getRefTable(targetClass))
                  .append(APPEND_SET_BANNED)
                  .append(APPEND_BY_ID);
            return result;
        }
    }

    public static class Create{
        /**
         * @param clazz
         * @param withID - if true - paste DEFAULT on first place
         * @return
         */
        @NotNull
        public static String getCreate(Class clazz, boolean withID) {
            String tableName = ResourceNavigator.getRefTable(clazz);

            List<String> fields = ResourceNavigator.getALLFieldsList(tableName ,true);
            String fieldsString = collectList(fields, ", ", 0);
            String setPart = surroundWithParenthesis(modifyAndCollectByReplacement(fields, ",", "?", 0));
            setPart = withID ? setPart.replaceFirst("\\?", DEFAULT) : setPart;
            StringBuilder insertHeader = getInsertQueryHeader(tableName, fieldsString);
            return appendInsertValues(insertHeader, setPart, 1).toString();
        }

        @NotNull
        public static String[] getCreateParts(Class clazz, boolean withID) {
            String tableName = ResourceNavigator.getRefTable(clazz);

            List<String> fields = ResourceNavigator.getALLFieldsList(tableName ,true);
            String fieldsString = collectList(fields, ", ", 0);
            String setPart = surroundWithParenthesis(modifyAndCollectByReplacement(fields, ",", "?", 0));
            setPart = withID ? setPart.replaceFirst("\\?", DEFAULT) : setPart;
            StringBuilder insertHeader = getInsertQueryHeader(tableName, fieldsString);
            return new String[]{insertHeader.toString(), setPart};
        }

        private static StringBuilder getInsertQueryHeader(String tableName, String fieldsString){
            StringBuilder query = new StringBuilder(INSERT);
            return query.append(tableName)
                 .append(" ")
                 .append(surroundWithParenthesis(fieldsString))
                 .append(VALUES);
        }

        public static StringBuilder appendInsertValues(StringBuilder mainPart,
                                                        String setPart, int howMuch){
            mainPart.append(setPart);
            for (int i = 1; i < howMuch; i++) {
                mainPart.append(",").append(setPart);
            }
            return mainPart;
        }
        /**
         * @param clazz
         * @param withID - if true - paste DEFAULT on first place
         * @return
         */
        @NotNull
        public static String getCreateList(Class clazz, int count, boolean withID) {
            String tableName = ResourceNavigator.getRefTable(clazz);

            List<String> fields = ResourceNavigator.getALLFieldsList(tableName ,true);
            String fieldsString = collectList(fields, ", ", 0);
            String setPart = surroundWithParenthesis(modifyAndCollectByReplacement(fields, ",", "?", 0));
            setPart = withID ? setPart.replaceFirst("\\?", DEFAULT) : setPart;
            StringBuilder query = getInsertQueryHeader(tableName, fieldsString);
            appendInsertValues(query, setPart, count);
            return query.toString();
        }

        public static String getCreateOnDuplicateUpdate(Class clazz, String... updatedFields) {
            String create = getCreate(clazz, true);
            String collect = Arrays.stream(updatedFields)
                                   .map(s -> ResourceNavigator.getRefTableField(clazz.getSimpleName(), s) + "= ?")
                                   .collect(Collectors.joining(", ", " ", " "));
            return create + ON_DUPLICATE_UPDATE + collect;
        }
    }


    /*decorative methods*/

    private static String collectList(List<String> list, String separator, int skip) {
        final Stream<String> stream = list.parallelStream();
        return stream.skip(skip).reduce((s1, s2) -> s1 + separator + s2).get();
    }

    private static String modifyAndCollectPattern(List<String> list, String separator,
                                                  String pattern, int skip) {
        final Stream<String> stream = list.parallelStream();
        return stream.skip(skip).map(s -> String.format(pattern, s))
                     .reduce((s1, s2) -> s1 + separator + s2).get();
    }

    private static String modifyAndCollectByReplacement(List<String> list, String separator,
                                                        String modification, int skip) {
        final Stream<String> stream = list.parallelStream();
        return stream.skip(skip).map(s -> modification)
                     .reduce((s1, s2) -> s1 + separator + s2).get();
    }

    private static String surroundWithParenthesis(String s) {
        return "(" + s + ")";
    }

    public static StringBuilder surroundWithParenthesis(StringBuilder s) {
        return s.insert(0, "(").append(")");
    }

}
