package by.epam.like_it.service.validator.util;


import by.epam.like_it.common_util.ResourceManager;
import by.epam.like_it.exception.service.validation.reflection.ValidationReflectionException;
import by.epam.like_it.model.vo.validation_vo.info.ValidationInfoLong;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ValidationNavigator {

    private static final String MIN_PATTERN = "%s.%s.min";
    private static final String MAX_PATTERN = "%s.%s.max";
    private static final String PATTERN_PATTERN = "%s.%s.pattern";
    private static final String DEFAULT_PATTERN = "%s.%s.default";
    private static final String IS_CAN_BE_NULL_PATTERN = "%s.%s.isCanBeNull";

    private static final ResourceManager INFO = ResourceManager.VALIDATION_INFO;

    private static ValidationNavigator instance;

    private ValidationNavigator(){}

    public static ValidationNavigator getInstance(){

        if (instance == null)
            synchronized (ValidationNavigator.class){
                if (instance == null)
                    instance = new ValidationNavigator();
            }
        return instance;
    }

    public boolean canBeNull(Class clazz, String fieldName){
        return canBeNull(clazz.getSimpleName(), fieldName);
    }

    public double getMaxValue(Class clazz, String fieldName){
        return getMaxValue(clazz.getSimpleName(), fieldName);
    }

    public double getMinValue(Class clazz, String fieldName){
        return getMinValue(clazz.getSimpleName(), fieldName);
    }

    public boolean hasPattern(Class clazz, String fieldName){
        return hasPattern(clazz.getSimpleName(), fieldName);
    }

    public String getPattern(Class clazz, String fieldName){
        return getPattern(clazz.getSimpleName(), fieldName);
    }

    private String getValue(String pattern, String simpleClassName, String fieldName){
        try {
            String key = String.format(pattern, simpleClassName, fieldName);
            return INFO.getString(key);
        } catch (MissingResourceException e) {
            throw new ValidationReflectionException("Bean " + simpleClassName + " or it's field " + fieldName + " isn't " +
                            "exist", e);
        }
    }

    private double getNumberValue(String pattern, String simpleClassName, String fieldName){
        try {
           String value =  getValue(pattern, simpleClassName, fieldName);
            return Double.parseDouble(value);
        } catch (NumberFormatException e){
            throw new ValidationReflectionException("Invalid bean mapping for " + simpleClassName, e);
        }
    }

    public double getMaxValue(String simpleClassName, String fieldName) throws ValidationReflectionException{
        return getNumberValue(MAX_PATTERN, simpleClassName, fieldName);
    }

    public double getMinValue(String simpleClassName, String fieldName) throws ValidationReflectionException{
        return getNumberValue(MIN_PATTERN, simpleClassName, fieldName);
    }

    public boolean hasPattern(String simpleClassName, String fieldName){
        String key = String.format(PATTERN_PATTERN, simpleClassName, fieldName);
        return INFO.containsKey(key);
    }

    public boolean canBeNull(String simpleClassName, String fieldName){
         String value = getValue(IS_CAN_BE_NULL_PATTERN, simpleClassName, fieldName);
         return Boolean.parseBoolean(value);
    }

    public String getPattern(String simpleClassName, String fieldName){
        String key = String.format(PATTERN_PATTERN, simpleClassName,fieldName);
        try {
            return INFO.getString(key);
        } catch (MissingResourceException e){
            return "";
        }
    }

    public String getDefault(String simpleClassName, String fieldName) {
        String key = String.format(DEFAULT_PATTERN, simpleClassName, fieldName);
        try {
            return INFO.getString(key);
        } catch (MissingResourceException e){
            return "";
        }
    }

    public ValidationInfoLong getValidationInfoLong(String simpleClassName, String fieldName){
        Double maxValue = getMaxValue(simpleClassName, fieldName);
        Double minValue = getMinValue(simpleClassName, fieldName);
        String pattern = getPattern(simpleClassName, fieldName);

        ValidationInfoLong validationInfo = new ValidationInfoLong(minValue.longValue(), maxValue.longValue());
        validationInfo.setFieldName(fieldName);
        validationInfo.setBeanName(simpleClassName);
        if (!pattern.isEmpty()){
            validationInfo.setPattern(pattern);
        }
        return validationInfo;
    }

    public Map<String, ValidationInfoLong> getValidationMapLong(String simpleClassName, String... fieldName){
        HashMap<String, ValidationInfoLong> result = new HashMap<>();
        for (String field: fieldName){
            ValidationInfoLong validationInfoLong = getValidationInfoLong(simpleClassName, field);
            validationInfoLong.setBeanName(simpleClassName);
            result.put(field, validationInfoLong);
        }
        return result;
    }

    public Map<String, ValidationInfoLong> getValidationMapLong(String simpleClassName, List<String> fieldNames){
        String[] arrays = new String[fieldNames.size()];
        return getValidationMapLong(simpleClassName, fieldNames.toArray(arrays));
    }

    public Map<String, ValidationInfoLong> getValidationMapLong(Class clazz){
        String simpleClassName = clazz.getSimpleName();
        List<String> allFieldsName =
                Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());

        HashMap<String, ValidationInfoLong> result = new HashMap<>();
        for (String field: allFieldsName){
            ValidationInfoLong validationInfoLong = getValidationInfoLong(simpleClassName, field);
            validationInfoLong.setBeanName(simpleClassName);
            result.put(field, validationInfoLong);
        }
        return result;
    }
}
