package testUtil.metadata_api.resourceWriter;


import by.epam.like_it.common_util.ClassName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testUtil.metadata_api.metaData.data.Database;
import testUtil.metadata_api.metaData.data.Table;
import testUtil.metadata_api.metaData.data.column.Column;
import testUtil.metadata_api.metaData.data.column.ExportedColumn;
import testUtil.metadata_api.metaData.data.column.ImportedColumn;
import testUtil.metadata_api.metaData.data.type.TypeDescription;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ResourceWriter {

    private static Logger logger = LogManager.getLogger(ClassName.getClassName());
    private static final String BEAN_PREFIX = "bean.";
    private static final String TABLE_PREFIX = "table.";
    private static final String NUM_PREFIX = "num.";
    private static final String BEAN_ALL_PATTERN = "bean.%s.all";
    private static final String TABLE_ALL_PATTERN = "table.%s.all";
    private static final String TABLE_ALL_QUALIFIED_PAT = "table.%s.all.qualified";
    private static final String BEAN_ALL_TYPES_PATTERN = "bean.%s.types";
    private static final String BEAN_ALL_TYPES_QUALIFIED_PAT = "bean.%s.types.qualified";
    private static final String TABLE_ALL_TYPES_PAT = "table.%s.types";

    private static final String BEAN_VALIDATION_MIN_PATTERN = "%s.%s.min";
    private static final String BEAN_VALIDATION_MAX_PATTERN = "%s.%s.max";
    private static final String BEAN_VALIDATION_IS_NULLABLE_PATTERN = "%s.%s.isCanBeNull";

    private Connection connection;
    private DatabaseMetaData metaData;
    private List<String> tableNames;
    private HashMap<String, NavigableMap<String, String>> result = new HashMap<>();
    private Database database;

    public ResourceWriter(Database database){
        this.database = database;
    }


    public LinkedHashMap<String, String>[] mapFields() {

        LinkedHashMap<String, String>[] result = new LinkedHashMap[2];
        LinkedHashMap<String, String> beans = new LinkedHashMap<>();
        LinkedHashMap<String, String> tables = new LinkedHashMap<>();
        result[0] = beans;
        result[1] = tables;

        for(Table table : database.getTables().values()){

            String tableName = table.getTableName();
            String className = convertToJavaStyle(tableName, true);

            String keyForBeans  = BEAN_PREFIX + className;
            String keyForTables = TABLE_PREFIX + tableName;
            int columnCount = table.getColumns().size();

            beans.put(keyForBeans, tableName);
            beans.put(BEAN_PREFIX + NUM_PREFIX + className, ""+columnCount);
            List<Column> columnsInOrder = table.getColumnsInOrder();

            String allBeanField = columnsInOrder.stream()
                                       .map(Column::getName)
                                       .map(s-> convertToJavaStyle(s, false))
                                       .collect(Collectors.joining(" "));

            beans.put(String.format(BEAN_ALL_PATTERN, className), allBeanField);



            tables.put(keyForTables, className);
            tables.put(TABLE_PREFIX + NUM_PREFIX + tableName, "" + columnCount);

            String allTableField  = columnsInOrder.stream()
                                                 .map(Column::getName)
                                                 .collect(Collectors.joining(" "));
            tables.put(String.format(TABLE_ALL_PATTERN, tableName), allTableField);

            String keyQaul = String.format(TABLE_ALL_QUALIFIED_PAT, tableName);
            String valQalified = columnsInOrder.stream().map(s-> tableName + "." + s.getName()).collect
                                                    (Collectors.joining(" "));

            tables.put(keyQaul, valQalified);


            String keyForBeanTypes = String.format(BEAN_ALL_TYPES_PATTERN, className);
            String allBeanTypes = columnsInOrder.stream()
                                                .map(Column::getType)
                                                .map(s->s.getExample().getClass().getSimpleName())
                                                .collect(Collectors.joining(" "));

            beans.put(keyForBeanTypes, allBeanTypes);

            String keyForQualBeanTypes = String.format(BEAN_ALL_TYPES_QUALIFIED_PAT, className);
            String allBeanQualTypes = columnsInOrder.stream()
                                                    .map(Column::getType)
                                                    .map(s->s.getExample().getClass().getCanonicalName())
                                                    .collect(Collectors.joining(" "));
            beans.put(keyForQualBeanTypes, allBeanQualTypes);

            String keyForTableAllTypes = String.format(TABLE_ALL_TYPES_PAT, tableName);
            String allTableTypes = columnsInOrder.stream()
                                                 .map(Column::getType)
                                                 .map(s->""+ s.getSqlDataType())
                                                 .collect(Collectors.joining(" "));
            tables.put(keyForTableAllTypes, allTableTypes);

            for (Column column : columnsInOrder) {
                String fieldTableName = column.getName();
                String fieldJavaName = convertToJavaStyle(fieldTableName, false);

                keyForBeans = BEAN_PREFIX + className + "." + fieldJavaName;
                keyForTables = TABLE_PREFIX + tableName + "." + fieldTableName;

                beans.put(keyForBeans, fieldTableName);
                tables.put(keyForTables, fieldJavaName);
            }
        }
        return result;
    }


    public LinkedHashMap<String, String> mapMinMax() {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();

        for(Table table : database.getTables().values()){

            String tableName = table.getTableName();
            String className = convertToJavaStyle(tableName, true);

            List<Column> columnsInOrder = table.getColumnsInOrder();

            String allBeanField = columnsInOrder.stream()
                                                .map(Column::getName)
                                                .map(s-> convertToJavaStyle(s, false))
                                                .collect(Collectors.joining(" "));
            for (Column column : columnsInOrder) {
                String fieldTableName = column.getName();
                String fieldJavaName = convertToJavaStyle(fieldTableName, false);

                String keyForMin  = String.format(BEAN_VALIDATION_MIN_PATTERN, className, fieldJavaName);
                String keyForMax  = String.format(BEAN_VALIDATION_MAX_PATTERN, className, fieldJavaName);
                String keyForNull = String.format(BEAN_VALIDATION_IS_NULLABLE_PATTERN, className, fieldJavaName);
                TypeDescription type = column.getType();
                boolean nullable = column.isNullable();

                Double valueForMin = type.getMinValue();
                Double valueForMax = type.getMaxValue();

                result.put(keyForNull, String.valueOf(nullable));
                result.put(keyForMin, String.valueOf(valueForMin));
                result.put(keyForMax, String.valueOf(valueForMax));
            }
        }
        return result;
    }

    /**
     *
     * @return list keys-values for join tables on base of:
     *      1) imported (on 0 index). Produce link 1-1
     *      2) exported (on 1 index). Produce link 1-many
     *      3) exported with exported of one table  (link criteria_to tables throw some table without it, so produce link
     *      many-criteria_to-many). For example:
     *      table 'posts' has exported column id criteria_to read_posts(Fields: post_id, user_id)[link posts.id = read_posts
     *      .post_id)
     *      and exported column id criteria_to favorite_users_posts(Fields: post_id, user_id) [link posts.id =
     *      favorite_users_posts.post_id).
     *      So it is possible interrlink read_posts and favorite_users_posts by read_posts.post_id =
     *      favorite_users_posts.post_id
     *      4) imported with  imported column in other table . For example:
     *      posts.user_id link criteria_to user.id
     *      answer.user_id link criteria_to user.id
     *      We can join on posts.user_id = answer.user_id. If we start from post, we will get the answers, that was
     *      been made by the same user, what read this post
     * @throws SQLException
     */

    public List<LinkedHashMap<String, String>> mapJoinKeys(){
        String valueFormat = "%s.%s = %s.%s"; // join value as in sql table1.table2 = table1.field1= table2.field2
        String keyFormat = "%s.%s";  // two tables name

        List<LinkedHashMap<String, String>> result = new ArrayList<>();

        LinkedHashMap<String, String> importedMap = new LinkedHashMap<>();
        LinkedHashMap<String, String> exportedMap = new LinkedHashMap<>();
        LinkedHashMap<String, String> importsBetweenMap = new LinkedHashMap<>();
        LinkedHashMap<String, String> exportsBetweenMap = new LinkedHashMap<>();
        result.add(importedMap);
        result.add(exportedMap);
        result.add(importsBetweenMap);
        result.add(exportsBetweenMap);


        for(Map.Entry<String, Table> entry : database.getTables().entrySet()){
               String thisTable = entry.getKey();
               Table value = entry.getValue();

            for(Map.Entry<String, ImportedColumn> entryImport :  value.getImportedColumns().entrySet()){
                String thisField = entryImport.getKey();   //return field of this table

                ImportedColumn valueImport = entryImport.getValue();
                String refColumnName = valueImport.getRefColumnName();
                String refTable = valueImport.getRefTable();

                String joinValue = String.format(valueFormat, thisTable, thisField, refTable, refColumnName); //put
                // imported
                String keyValue =  String.format(keyFormat, thisTable, refTable);
                importedMap.put(keyValue, joinValue);

                joinValue = String.format(valueFormat, refTable, refColumnName, thisTable, thisField); // put exported
                keyValue =  String.format(keyFormat,  refTable, thisTable);
                exportedMap.put(keyValue, joinValue);

                Table table = database.getTables().get(refTable); //other table
                List<ExportedColumn> collect = table.getExportedColumns().entrySet()            //exported in other
                                                    // table with the
                                                    // same field that imported in current table
                                                    .stream().filter(s -> s.getValue().getRealColumn().getName()
                                                                           .equals(refColumnName)).map(
                                                Map.Entry::getValue).collect
                                                (Collectors.toList());
                for (ExportedColumn column: collect){
                    String refrefColumnName = valueImport.getRefColumnName();
                    if (!refrefColumnName.equals(refColumnName)) {
                        String refrefTable = valueImport.getRefTable();
                        joinValue = String.format(valueFormat, refTable, refColumnName, refrefTable,
                                refrefColumnName); // put exported
                        keyValue = String.format(keyFormat, refTable, refrefTable);
                        importsBetweenMap.put(keyValue, joinValue);

                    }
                }
            }

            for(Map.Entry<String, ExportedColumn> entryExport :  value.getExportedColumns().entrySet()){

                String thisField = entryExport.getKey();  //return refTable.refField
                ExportedColumn valueExport = entryExport.getValue();
                String refColumnName = valueExport.getRefColumnName();
                String refTable = valueExport.getRefTable();

                Column thisColumn = valueExport.getRealColumn();

                for(Map.Entry<String, ExportedColumn> entrySecondExport :  value.getExportedColumns().entrySet()){
                    ExportedColumn exportedColumn = entrySecondExport.getValue();
                    Column realColumn = exportedColumn.getRealColumn();

                    if (thisColumn.equals(realColumn)){
                        String otherRefTable = exportedColumn.getRefTable();
                        if (!otherRefTable.equals(refTable)) {
                            String otherRefColumn = exportedColumn.getRefColumnName();

                            String joinValue =
                                    String.format(valueFormat, refTable, refColumnName, otherRefTable, otherRefColumn);
                            String keyValue = String.format(keyFormat, refTable, otherRefTable);
                            exportsBetweenMap
                                    .put(keyValue, joinValue);                              //iterrelated exported
                        }
                    }
                }
            }
        }
        return result;
    }

//    public boolean checkBeanTypesMapping(){
//       boolean result = false;
//        return result;
//    }

    /**
     *
     * @param name : name of field or table
     * @param isClass : mark is it table name or table field name
     * @return identification converted criteria_to java style
     */
   private String convertToJavaStyle(String name, boolean isClass) {
        String result = "";
        String temp = name;

        if (temp.endsWith("s")){
            temp = temp.substring(0, temp.length() - 1);
        }
        if (temp.endsWith("ie")){
            temp = temp.substring(0, temp.length() - 2) + "y";
        }
        while (temp.contains("_")){
            int delimIndex = temp.indexOf('_');
            char toReplace = temp.charAt(delimIndex + 1);

            String group = "_" + toReplace;
            String mustBe = ""+Character.toUpperCase(toReplace);

            temp = temp.replace("s_", "_");
            temp = temp.replace("ie_", "y_");
            temp = temp.replace(group, mustBe);
        }
        if (isClass) {
            Character first = name.charAt(0);
            first = Character.toUpperCase(first);
            result = first + temp.substring(1);
        } else {
            result = temp;
        }
        return result;
    }


   public void writeProperties(LinkedHashMap<String, String> map, String path) throws ResourceWriterException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            StringBuilder builder = new StringBuilder();

            map.entrySet().forEach(s-> builder.append(s.getKey()).append(" = ")
                                              .append(s.getValue()).append("\n"));
            writer.write(builder.toString());
        } catch (IOException e) {
            throw new ResourceWriterException("Can't write the properties",e);
        }
    }

    public Map<String, String> collectBeans(boolean withInterfaces, boolean withSuperclass)  {
        Map<String, String> beans = new HashMap<>();

        for (Map.Entry<String, Table> entry : database.getTables().entrySet()) {
            String tableName = entry.getKey();
            Table table = entry.getValue();
            StringBuilder wholeBean = new StringBuilder("");

            String className = convertToJavaStyle(tableName, true);
            wholeBean.append("public class ")
                     .append(className);
            if (withSuperclass){
                wholeBean.append(" extends Entity ");
            }
            if (withInterfaces){
                List<String> interfaces = getInterfaces(table);
                wholeBean.append(interfaces.stream().collect(Collectors.joining(", ")));
            }
            wholeBean.append("{\n\n");
            for (Column column : table.getColumnsInOrder()) {
                String fieldName = column.getName();

                String javaType = column.getType().getExample().getClass().getName();
                String javaName = convertToJavaStyle(fieldName, false);

                StringBuilder result = new StringBuilder("private ");
                result.append(javaType)
                      .append(" ")
                      .append(javaName)
                      .append(";")
                      .append("\n");
                wholeBean.append(result);
                System.out.println(result);
            }
            beans.put(className, wholeBean.append("\n}").toString());
        }
        return beans;
    }

    public List<String> getInterfaces(Table table) {
        String formatImported = "Imported<%s>";
        String formatExported = "Exported<%s>";
        List<String> result = new ArrayList<>();
        for (ImportedColumn s : table.getImportedColumns().values()){
            String imported = String.format(formatImported, s.getRefTable());
            result.add(imported);
        }
        for (ExportedColumn s : table.getExportedColumns().values()){
            String exported = String.format(formatImported, s.getRefTable());
            result.add(exported);
        }
        return result;
    }

    public void writeBeans(String prefix, Map<String, String> beansContent){
        for(Map.Entry<String, String> entry : beansContent.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();

            String packages = prefix.replace("src\\", "")
                                    .replace("\\", ".");
            String packageName = "package " + packages.substring(0, packages.length() - 1)
                    + ";\n\n";
            String path = prefix + key + ".java";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                writer.write(packageName);
                writer.write(value);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
