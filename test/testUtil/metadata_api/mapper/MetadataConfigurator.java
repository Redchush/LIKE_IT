package testUtil.metadata_api.mapper;


import org.apache.ibatis.metadata.Database;
import testUtil.metadata_api.metaData.data.type.Patterns;
import testUtil.metadata_api.metaData.handler.TableManager;
import testUtil.metadata_api.metaData.data.column.Column;
import testUtil.metadata_api.metaData.data.type.TypeDescription;
import testUtil.metadata_api.metaData.data.type.impl.StringDescription;

import java.util.TreeMap;

public class MetadataConfigurator {

    public void configureInitial(TableManager manager){

        Database database = new Database(null, null);

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
    }
}
