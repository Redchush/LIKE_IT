package by.epam.like_it.service.validator.impl.content_validator;


import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.util_interface.Entity;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;
import by.epam.like_it.service.validator.Validator;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityValidator implements Validator<RealEntity>{

    private static EntityValidator instance;

    private EntityValidator(){}

    public static EntityValidator getInstance(){

        if (instance == null)
            synchronized (EntityValidator.class){
                if (instance == null)
                    instance = new EntityValidator();
            }
        return instance;
    }

    private static final String ID_FIELD_NAME = "id";
    public void isValidForCreate(Entity entity) throws ValidationInfoException {
        reallyValidate(entity, true);
    }

    /**
     * Check
     * 1) all required fields not null
     * 2) all required fields and all not null fields restriction on min and max values
     * Trim all string fields.
     * @param entity
     * @throws ValidationInfoException
     */
    @Override
    public void isValidForCreate(RealEntity entity) throws ValidationInfoException {
       reallyValidate(entity, true);
    }

    /**
     * Check valid id and restriction on min and max values on not null fields.
     * Trim all string fields.
     * @param entity
     * @throws ValidationInfoException
     */
    @Override
    public void isValidForUpdate(RealEntity entity) throws ValidationInfoException {
        boolean b = isIdValid(entity);
        if (!b) {
            throw new ValidationInfoException(new InvalidInfo(entity.getClass().getSimpleName(),
                    Collections.singletonList("id")));
        }
        reallyValidate(entity, false);
    }

    public boolean validateOneStringField(String classSimpleName, String name, String value){
        double maxValue = NAVIGATOR.getMaxValue(classSimpleName, name);
        double minValue = NAVIGATOR.getMinValue(classSimpleName, name);
        boolean isValid = value.length() <= maxValue && value.length() >= minValue;
        if (isValid){
            String pattern = NAVIGATOR.getPattern(classSimpleName, name);
            if (!pattern.isEmpty()) {
                isValid = value.matches(pattern);
            }
        }
        return isValid;

    }

    public boolean validateOneNumberField(String classSimpleName, String name, Number value){
        double maxValue = NAVIGATOR.getMaxValue(classSimpleName, name);
        double minValue = NAVIGATOR.getMinValue(classSimpleName, name);
        return !(value.doubleValue() > maxValue || value.doubleValue() < minValue);
    }

    private void reallyValidate(Entity entity, boolean isForCreate) throws ValidationInfoException {
        List<String> invalidFields = new ArrayList<>();
        Class clazz = entity.getClass();
        String className = clazz.getSimpleName();

        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            declaredField.setAccessible(true);
            String name = declaredField.getName();
            if (!name.equals(ID_FIELD_NAME)) {
                try {
                    Object value = declaredField.get(entity);
                    boolean canBeNull = NAVIGATOR.canBeNull(className, name);
                    if (value == null){
                        if (isForCreate) {
                            if (!canBeNull) {
                                invalidFields.add(name);
                            }
                        }
                    } else {
                        Class<?> type = declaredField.getType();
                        boolean result = true;
                        if (type == String.class) {
                            value = ((String) value).trim();
                            declaredField.set(entity, value);
                            result = validateOneStringField(className, name, (String) value);
                        } else {
                            if (type != Timestamp.class && type != Boolean.class) {
                                result = validateOneNumberField(className, name, (Number) value);
                            }
                        }
                        if (!result){
                            invalidFields.add(name);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new ValidationInfoException(new InvalidInfo(className, Collections.singletonList("unnamed")));
                }
            }
            declaredField.setAccessible(false);
        }
        if (!invalidFields.isEmpty()){
            throw new ValidationInfoException(new InvalidInfo(className, invalidFields));
        }
    }

    public boolean isIdValid(RealEntity entity){
        return !(entity.getId() == null || entity.getId().longValue() < 1);
    }
}
