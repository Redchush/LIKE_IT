package testUtil.metadata_api.autofill;


import by.epam.like_it.dao.connection_pool.ConnectionFactory;
import by.epam.like_it.dao.connection_pool.ConnectionFactoryFactory;
import by.epam.like_it.exception.persistence.connection_pool.ConnectionPoolException;
import testUtil.metadata_api.randomizer.exception.StringRandomizeInitException;
import testUtil.metadata_api.randomizer.StringRandom;
import testUtil.metadata_api.mapper.MetaDataMapper;
import testUtil.metadata_api.mapper.MetadataConfigurator;
import testUtil.metadata_api.metaData.data.Table;
import testUtil.metadata_api.metaData.handler.TableManager;
import testUtil.metadata_api.metaData.data.column.Column;

import java.sql.Connection;
import java.sql.SQLException;

public class AutoUpdate {

    public static void main(String[] args) {
        ConnectionFactory factory = ConnectionFactoryFactory.getInstance().getConnectionFactory();
        try(Connection connection = factory.takeConnectionWithoutCommit()) {

            StringRandom.getInstance().init(null, null, null);
            MetaDataMapper mapper = MetaDataMapper.getInstance();
            mapper.init(connection);

            TableManager manager = mapper.collectTables();
            MetadataConfigurator configurator = new MetadataConfigurator();
            configurator.configureInitial(manager);

            Table rating = manager.getTableByName("rating");
            Column user_id = rating.getColumnByOrder(2);
            Column answer_id = rating.getColumnByOrder(3);
            user_id.getType().setMaxValue(100);
            answer_id.getType().setMaxValue(100);

            AutoFillerConfigurator autoFillerConfigurator = new AutoFillerConfigurator();
            autoFillerConfigurator.setRowCount(200);

            AutoFillerDB fillerDB = new AutoFillerDB(autoFillerConfigurator);
            fillerDB.init(connection, manager);
            fillerDB.fillOneTable(rating);
////            fillerDB.updateByRandom();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (StringRandomizeInitException e) {
            e.printStackTrace();
        } catch (MetaDataException e) {
            e.printStackTrace();
        }
    }
}
