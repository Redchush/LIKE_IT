package by.epam.like_it.dao.mysql.util.query;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class QueryCache {

    private volatile Values values;


    private static class Values{
        private final String PREPARED_STRING;

        private Values(){
            createAll = new ConcurrentHashMap<>();
            createParts = new ConcurrentHashMap<>();
            selectAll = new ConcurrentHashMap<>();
            selectByObject = new ConcurrentHashMap<>();
            updateByAllFields = new ConcurrentHashMap<>();
            updateByNotNullFields = new ConcurrentHashMap<>();
            delete = new ConcurrentHashMap<>();
            PREPARED_STRING = Collections.nCopies(100, new StringBuilder("?")).stream().collect(Collectors.joining(", "));
        }

        private ConcurrentHashMap<String, String> createAll;
        private ConcurrentHashMap<String, String[]> createParts;

        private ConcurrentHashMap<String, String> selectAll;
        private ConcurrentHashMap<Integer, String> delete;

        private ConcurrentHashMap<Integer, String> selectByObject;
        private ConcurrentHashMap<String, String> updateByAllFields;
        private ConcurrentHashMap<Integer, String> updateByNotNullFields;
    }

    private static QueryCache instance;

    private QueryCache(){
        values = new Values();
    }

    public static QueryCache getInstance(){

        if (instance == null)
            synchronized (QueryCache.class){
                if (instance == null)
                    instance = new QueryCache();
            }
        return instance;
    }


    public ConcurrentHashMap<String, String> getSelectAll() {
        return values.selectAll;
    }

    /**
     * where in value:
     * String[0] - create header
     * String[1] - create set part
     * Storaged separate for handling uniquely create many and one entities;
     * @return createParts map;
     */
    public ConcurrentHashMap<String, String[]> getCreateParts() {
        return values.createParts;
    }

    public ConcurrentHashMap<String, String> getCreateAll() {
        return values.createAll;
    }

    public ConcurrentHashMap<Integer, String> getSelectByObject() {
        return values.selectByObject;
    }

    public ConcurrentHashMap<Integer, String> getUpdateByNotNullFields() {
        return values.updateByNotNullFields;
    }

    public ConcurrentHashMap<String, String> getUpdateByAllFields() {
        return values.updateByAllFields;
    }

    public ConcurrentHashMap<Integer, String> getDelete() {
        return values.delete;
    }

    public String getPREPARED_STRING() {
        return values.PREPARED_STRING;
    }

    public synchronized void reset() {
        values = new Values();
    }
}
