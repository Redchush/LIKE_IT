package testUtil.metadata_api.metaData.handler.type;


import testUtil.metadata_api.metaData.handler.type.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TypeHandlerFactory {

    private static TypeHandlerFactory instance;

    private TypeHandlerFactory(){}

    private static final Map<String, Supplier<TypeHandler<?>>> STORAGE = new HashMap<>();

    static {
        STORAGE.put("Boolean", BooleanHandler::getInstance);
        STORAGE.put("Byte", ByteHandler::getInstance);
        STORAGE.put("Decimal", DecimalHandler::getInstance);
        STORAGE.put("Double", DoubleHandler::getInstance);
        STORAGE.put("Float", FloatHandler::getInstance);
        STORAGE.put("Integer", IntegerHandler::getInstance);
        STORAGE.put("Long", LongHandler::getInstance);
        STORAGE.put("Short", ShortHandler::getInstance);
        STORAGE.put("String", StringHandler::getInstance);
        STORAGE.put("Timestamp", TimeStampHandler::getInstance);
    }

    public static TypeHandlerFactory getInstance(){

        if (instance == null)
            synchronized (TypeHandlerFactory.class){
                if (instance == null)
                    instance = new TypeHandlerFactory();
            }
        return instance;
    }

    public TypeHandler<?> getWrapperByClassName(String className){
        return STORAGE.get(className).get();
    }



}
