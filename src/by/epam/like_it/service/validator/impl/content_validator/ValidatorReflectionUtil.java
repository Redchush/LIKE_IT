package by.epam.like_it.service.validator.impl.content_validator;


import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;
import by.epam.like_it.service.validator.util.ValidationNavigator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ValidatorReflectionUtil {

    private static ValidatorReflectionUtil instance;
    public static ValidationNavigator NAVIGATOR = ValidationNavigator.getInstance();

    public static final String ID_FIELD_NAME = "id";

    private ValidatorReflectionUtil(){}

    public static ValidatorReflectionUtil getInstance(){

        if (instance == null)
            synchronized (ValidatorReflectionUtil.class){
                if (instance == null)
                    instance = new ValidatorReflectionUtil();
            }
        return instance;
    }

    public List<String> validateFieldsExceptIdOnNull(RealEntity realEntity) throws ValidationInfoException {
        List<String> invalidFields = new ArrayList<>();
        Class clazz = realEntity.getClass();

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            String name = declaredField.getName();
            if (!name.equals(ID_FIELD_NAME)) {
                try {
                    Object object = declaredField.get(realEntity);
                    boolean b = NAVIGATOR.canBeNull(clazz, name);
                    if (object == null && !b) {
                        invalidFields.add(name);
                    }
                } catch (IllegalAccessException e) {
                    throw new ValidationInfoException(new InvalidInfo());
                }
            }
            declaredField.setAccessible(false);
        }
        return invalidFields;
    }

    public boolean validateId(RealEntity entity){
        return entity.getId() == null || entity.getId().longValue() < 1;
    }
}
