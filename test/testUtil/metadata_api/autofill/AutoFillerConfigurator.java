package testUtil.metadata_api.autofill;


import java.util.*;

public class AutoFillerConfigurator {

    public enum ExceptionInScriptOption{
        END_RUNNING, USE_AUTOFULL, IGNORE
    }

    private static final int DEFAULT_ROW_COUNT = 100;
    private static final int DEFAULT_NULL_INTERRELATED = 0;

    private Map<String, Integer> constraintTableMap;
    private Map<String, String> scriptMap;

    private int rowCount;
    private int setNullInInterrelated;
    private int maxStringLength;

    private ExceptionInScriptOption scriptOption;


    public AutoFillerConfigurator(){
        constraintTableMap = new HashMap<>();
        scriptMap = new HashMap<>();
        rowCount = DEFAULT_ROW_COUNT;
        setNullInInterrelated = DEFAULT_NULL_INTERRELATED;
        scriptOption = ExceptionInScriptOption.END_RUNNING;
    }

    public AutoFillerConfigurator(int rowCount) {
        this();
        this.rowCount = rowCount;
    }

    public Map<String, Integer> getConstraintTableMap() {
        return constraintTableMap;
    }

    /**
     *  set List that contains special constraints for column in tables;
     */
    public void setConstraintTableMap(Map<String, Integer> constraintTableMap) {
        this.constraintTableMap = constraintTableMap;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public Map<String, String> getScriptMap() {
        return scriptMap;
    }

    /**
     * NOTICE: - vo correct work of method use VALUES pattern or VALUE pattern, NOT MIX THEM
     *          - if tables, which script will full, need vo be not fulled by autogenerating -
     *           create this tables vo skipMap in case if first condition is broken
     * @param scriptMap, where key must be table name, value - INSERT script vo be executed
     */

    public void setScriptMap(Map<String, String> scriptMap, boolean isScipped) {
        this.scriptMap = scriptMap;
        if (isScipped){
            checkScriptMap(scriptMap);
        }
    }

    private void checkScriptMap(Map<String, String> scriptMap) {

        for(Map.Entry<String, String> entry : scriptMap.entrySet()){
                        String key = entry.getKey();
                        String value = entry.getValue();

            int sub = !value.contains("VALUES") ? countInsertsInValueType(value)
                                                : countInsertsInValuesType(value);
            constraintTableMap.put(key, sub);
        }
    }
    private int countInsertsInValueType(String s){
        String[] inserts = s.split("INSERT");
        return s.split("INSERT").length - 1;
    }
    private int countInsertsInValuesType(String s){

        return s.split("[(][^()]*[)][ ]{0,5},").length; // [(]*[)][ ]{0,5},"
    }

    public static void main(String[] args) {
        String s = "INSERT INTO `LIKE_IT`.`roles` (`id`, `name`) VALUES" +
                " (1, 'owner'), (2, 'responsible'), (3, 'user'), (4, 'anonym');";
        System.out.println(new AutoFillerConfigurator().countInsertsInValuesType(s));
    }

    public int getSetNullInInterrelated() {
        return setNullInInterrelated;
    }

    public void setSetNullInInterrelated(int setNullInInterrelated) {
        this.setNullInInterrelated = setNullInInterrelated;
    }
    public int getMaxStringLength() {
        return maxStringLength;
    }

    public void setMaxStringLength(int maxStringLength) {
        this.maxStringLength = maxStringLength;
    }

    public ExceptionInScriptOption getScriptOption() {
        return scriptOption;
    }

    public void setScriptOption(ExceptionInScriptOption scriptOption) {
        this.scriptOption = scriptOption;
    }
}
