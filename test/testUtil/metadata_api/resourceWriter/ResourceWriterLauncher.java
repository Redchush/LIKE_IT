package testUtil.metadata_api.resourceWriter;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testUtil.metadata_api.autofill.MetaDataException;
import testUtil.metadata_api.metaData.data.type.Patterns;
import testUtil.metadata_api.randomizer.exception.StringRandomizeInitException;
import testUtil.metadata_api.mapper.MetaDataMapper;
import testUtil.metadata_api.metaData.handler.TableManager;
import testUtil.metadata_api.metaData.data.column.Column;
import testUtil.metadata_api.metaData.data.type.TypeDescription;
import testUtil.metadata_api.metaData.data.type.impl.StringDescription;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResourceWriterLauncher {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    private static final String pathToProperties = "src\\resource\\db\\written\\";

    private static final String tableMapping = "tableMapping.properties";
    private static final String beanMapping = "beanMapping.properties";
    private static final String joinKeyMapping = "joinKey.properties";

    final String prefix = "src\\resource\\written\\relationMethods.properties";

    public static void main(String[] args) throws StringRandomizeInitException {
        ConnectionFactory factory = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        Connection connection = null;
        try {
            connection = factory.takeConnectionWithoutCommit();
            MetaDataMapper mapper = MetaDataMapper.getInstance();
            mapper.init(connection);

            TableManager manager = mapper.collectTables();
            TreeMap<String, Column> users = manager.getTableByName("users")
                                                   .getColumns();

            TypeDescription type = users.get("login").getType();
            type.setPattern(Patterns.ENGLISH_WITH_SIGNS);
            type.setMinValue(6);

            users.get("email").getType()
                 .setPattern(Patterns.EMAIL);
            manager.getTableByName("tags")
                   .getColumns().get("name")
                   .getType().setPattern(Patterns.ENGLISH_WITH_SIGNS);
            TypeDescription ratingType = manager.getTableByName("rating")
                                                .getColumns().get("rating")
                                                .getType();
            ratingType.setMinValue(-5d);
            ratingType.setMaxValue(5d);
//            System.out.println(manager.getCurrentDatabase());

            ResourceWriter resourceWriter = new ResourceWriter(manager.getCurrentDatabase());

            LinkedHashMap<String, String>[] mapsFields = resourceWriter.mapFields();
            LinkedHashMap<String, String> mapClass = mapsFields[0];
            LinkedHashMap<String, String> mapTable= mapsFields[1];

            List<LinkedHashMap<String, String>> mapJoinKeys = resourceWriter.mapJoinKeys();
            LinkedHashMap<String, String> mapImported = mapJoinKeys.get(0);
            LinkedHashMap<String, String> mapExported= mapJoinKeys.get(1);
            LinkedHashMap<String, String> mapBetweenImported= mapJoinKeys.get(2);
            LinkedHashMap<String, String> mapBetweenExported= mapJoinKeys.get(3);


            LinkedHashMap<String, String> joinMap = new LinkedHashMap<>();
            joinMap.putAll(mapImported);
            joinMap.putAll(mapExported);


            mapBetweenImported.putAll(mapBetweenExported);
            mapBetweenImported.forEach(((s, s2) -> {
                if (joinMap.containsKey(s)){
                    joinMap.put(s + ".related", s2);
                } else{
                    joinMap.put(s, s2);
                }
            }));

//            joinMap.putAll(mapBetweenImported);
//            joinMap.putAll(mapBetweenExported);


            for (LinkedHashMap<String, String> map : mapJoinKeys){
                showMap(map);
            }

            resourceWriter.writeProperties(mapClass, pathToProperties + beanMapping);
            resourceWriter.writeProperties(mapTable, pathToProperties + tableMapping);
            resourceWriter.writeProperties(joinMap, pathToProperties + joinKeyMapping);

        } catch (MetaDataException e) {
            LOGGER.error("Can'collect Metadata");
        } catch (ConnectionPoolException e) {
            LOGGER.error("Can'access connection poo;");
        } catch (ResourceWriterException e) {
            LOGGER.error("Problems with writing data");
        } finally {
            factory.dispose();
        }
    }

    private static <T extends CharSequence> void showMap( Map<String, T>  map){

        for(Map.Entry<String, T> entry : map.entrySet()){
            String key = entry.getKey();
            T value = entry.getValue();
            System.out.println(key + " = " + value);
        }
        System.out.println("______________");
    }
//    private void showMap( Map<String, StringBuilder>  map){
//        for(Map.Entry<String, StringBuilder> entry : map.entrySet()){
//            String key = entry.getKey();
//            StringBuilder value = entry.getValue();
//            System.out.println(key + "\n" + value);
//        }
//    }
}
