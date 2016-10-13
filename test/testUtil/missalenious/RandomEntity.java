package testUtil.missalenious;


import by.epam.like_it.model.bean.util_interface.Entity;
import sun.util.calendar.BaseCalendar;
import testUtil.metadata_api.metaData.handler.type.TypeHandlerFactory;
import testUtil.metadata_api.randomizer.RandomHandler;
import testUtil.metadata_api.randomizer.exception.StringRandomizeInitException;

import java.lang.reflect.Field;
import java.security.Timestamp;
import java.util.Arrays;
import java.util.Date;

public class RandomEntity {

    private static RandomEntity instance;

    private final TypeHandlerFactory TYPE_HANDLER;
    private final RandomHandler RANDOM_HANDLER;


    private RandomEntity() throws StringRandomizeInitException {
        TYPE_HANDLER = TypeHandlerFactory.getInstance();
        RANDOM_HANDLER = RandomHandler.getInstance();
        RANDOM_HANDLER.initDefaultStringRandomizer();
    }

    public static RandomEntity getInstance() throws StringRandomizeInitException {

        if (instance == null)
            synchronized (RandomEntity.class){
                if (instance == null)
                    instance = new RandomEntity();
            }
        return instance;
    }


    public Entity getRandomEntity(Class<? extends Entity> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        Entity object = null;
        try {
            final Entity object1  = clazz.newInstance();
            Arrays.stream(declaredFields).forEach(s->{
                applyRandomToEntity(object1, s);
            });
            object = object1;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void applyRandomToEntity(Entity object, Field field){
        field.setAccessible(true);
        String simpleName =field.getType().getSimpleName();
        Object randomByType;
        if (simpleName.equals("Timestamp")){
            randomByType =   RANDOM_HANDLER.getRandomByType(0d, new Double(new Date().getTime()), simpleName);
        } else {
            randomByType =   RANDOM_HANDLER.getRandomByType(0d, 20d, simpleName);
        }
        try {
            field.set(object, randomByType);
        } catch (IllegalAccessException e) {
            System.out.println("No bean");
        }
    }


}
