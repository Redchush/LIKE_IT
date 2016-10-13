package by.epam.like_it.dao.mysql.util.query;


import by.epam.like_it.common_util.ReflectionUtil;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.Entity;
import by.epam.like_it.model.bean.util_interface.RealEntity;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class QueryMediator {

    private static final QueryCache CACHE = QueryCache.getInstance();

    private static QueryMediator instance;

    private QueryMediator(){}

    public static QueryMediator getInstance(){

        if (instance == null)
            synchronized (QueryMediator.class){
                if (instance == null)
                    instance = new QueryMediator();
            }
        return instance;
    }

    public String getSelectAll(Class targetClass) {
        ConcurrentHashMap<String, String> selectAll = CACHE.getSelectAll();
        String result = selectAll.get(targetClass.getSimpleName());
        if ( result != null){
            return result;
        } else {
            result = QueryMaker.Select.getSelectAll(targetClass);
            selectAll.put(targetClass.getSimpleName(), result);
            return result;
        }
    }

    public String getCreate(Class targetClass, int count, boolean withId) {
        ConcurrentHashMap<String, String[]> createParts = CACHE.getCreateParts();
        String[] parts = createParts.get(targetClass.getSimpleName());
        if (parts != null ){
            return QueryMaker.Create.appendInsertValues(new StringBuilder(parts[0]), parts[1], count).toString();
        } else {
            parts = QueryMaker.Create.getCreateParts(targetClass, withId);
            createParts.put(targetClass.getSimpleName(), parts);
            return QueryMaker.Create.appendInsertValues(new StringBuilder(parts[0]), parts[1], count).toString();
        }
    }

    public String getSelectByObject(Object entity){
        ConcurrentHashMap<Integer, String> updateByObject = CACHE.getSelectByObject();
        List<String> notNullFields = ReflectionUtil.getNotNullFieldsList(entity);
        notNullFields.add(entity.getClass().getSimpleName());
        int code = notNullFields.hashCode();

        String result = updateByObject.get(code);
        if (result != null){
            return result;
        } else {
            notNullFields.remove(notNullFields.size() - 1);
            result =  QueryMaker.Select.getSelectByObject(entity, notNullFields.stream());
            updateByObject.put(code, result);
            return result;
        }
    }

    public String getUpdateByNotNullFields(RealEntity entity) {
        ConcurrentHashMap<Integer, String> updateByObject = CACHE.getUpdateByNotNullFields();
        List<String> notNullFields = ReflectionUtil.getNotNullFieldsExceptIdList(entity);
        notNullFields.add(entity.getClass().getSimpleName());
        int code = notNullFields.hashCode();
        String result = updateByObject.get(code);
        if (result != null) {
            return result;
        } else {
            notNullFields.remove(notNullFields.size() - 1);
            result = QueryMaker.Update.getUpdateByNotNullFields(entity, notNullFields.stream());
            updateByObject.put(code, result);
            return result;
        }
    }

    public String getUpdateByAllFields(RealEntity entity){
        ConcurrentHashMap<String, String> cash = CACHE.getUpdateByAllFields();
        String key = entity.getClass().getSimpleName();
        String result = cash.get(key);
        if (result != null) {
            return result;
        } else {
            result = QueryMaker.Update.getUpdateByAllFields(entity.getClass());
            cash.put(key, result);
            return result;
        }
    }

    public String getDelete(Entity entity, boolean byBanIfPossible){
        ConcurrentHashMap<Integer, String> cash = CACHE.getDelete();
        int code = entity.getClass().getSimpleName().hashCode();
        code = appendHash(code, byBanIfPossible);
        String result = cash.get(code);

        if (result == null) {
            boolean isMaintainBan = ReflectionUtil.isClassMaintainInterface(entity.getClass(),DeletableByBan.class);
            result = isMaintainBan ? QueryMaker.Delete.getDeleteByBan(entity.getClass())
                                     : QueryMaker.Delete.getDeleteById(entity.getClass());
            cash.put(code, result);
       }
        return result;
    }


    /**
     * @param copyOfSign
     * @return string which contains copyOfSign separated by ', '
     */
    public String getPreperadPart(int copyOfSign){
        String prepared_string = CACHE.getPREPARED_STRING();
        int length = calculateLength(copyOfSign);
        return prepared_string.substring(0, length);
    }

    private int calculateLength(int n){
        return n + (n-1)*2;
    }


    /**
     * get select all from cash and add by id parameter
     * @param targetClass - class to be selected
     * @return select query
     */
    public String getSelectById(Class<? extends RealEntity> targetClass){
        String selectAll = getSelectAll(targetClass);
        return QueryMaker.Select.getSelectById(selectAll);
    }

    private int appendHash(int result, boolean b){
        return  31 * result + (b ? 1 : 0);
    }

    public boolean hasClassId(Class clazz){
       return ReflectionUtil.isClassMaintainInterface(clazz, RealEntity.class);
    }
}
