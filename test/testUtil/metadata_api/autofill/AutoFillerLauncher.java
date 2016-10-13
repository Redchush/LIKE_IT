package testUtil.metadata_api.autofill;


import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import testUtil.metadata_api.mapper.MetaDataMapper;
import testUtil.metadata_api.metaData.handler.TableManager;
import testUtil.metadata_api.randomizer.StringRandom;
import testUtil.metadata_api.randomizer.exception.StringRandomizeInitException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AutoFillerLauncher {

    public static void main(String[] args) throws StringRandomizeInitException {
        ConnectionFactory factory = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        try(Connection connection = factory.takeConnectionWithoutCommit()){

            StringRandom.getInstance().init(null, null, null);
            MetaDataMapper mapper = MetaDataMapper.getInstance();
            mapper.init(connection);

            TableManager manager = mapper.collectTables();
            System.out.println(manager);

            AutoFillerConfigurator configurator = new AutoFillerConfigurator();

            String s = "INSERT INTO `LIKE_IT`.`roles` (`id`, `name`) VALUES" +
                    " (1, 'owner'), (2, 'responsible'), (3, 'user'), (4, 'anonym');";

            String s1 = "INSERT INTO `LIKE_IT`.`languages` (`id`, `name`) VALUES " +
                    "(1, 'EN'),(2, 'RU');";

            Map<String, String> script = new HashMap<>();
            script.put("roles", s);
            script.put("languages", s1);

//            HashMap<String, Integer> constrains = new HashMap<>();
//            constrains.put("readed_posts", 300);
//            constrains.put("post_tags", 200);

            configurator.setScriptMap(script, true);
            configurator.setMaxStringLength(150);
            configurator.setSetNullInInterrelated(3);
//            configurator.setConstraintTableMap(constrains);
            configurator.setScriptOption(AutoFillerConfigurator.ExceptionInScriptOption.IGNORE);

            AutoFillerDB fillerDB = new AutoFillerDB(configurator);
            fillerDB.init(connection, manager);
            fillerDB.fill();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (MetaDataException e) {
            e.printStackTrace();
        }
    }
}
