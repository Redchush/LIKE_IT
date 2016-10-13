package by.epam.like_it.service.validator.impl.content_validator;


import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.exception.service.validation.info.ValidatorRequiredFieldIsNullException;
import by.epam.like_it.model.adapter.Content;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;
import by.epam.like_it.service.validator.Validator;

import java.util.ArrayList;
import java.util.List;

public class ContentValidator implements Validator<Content>{

    private static ContentValidator instance;
    private final ValidatorReflectionUtil UTIL;

    private ContentValidator(){
        UTIL = ValidatorReflectionUtil.getInstance();
    }

    public static ContentValidator getInstance(){

        if (instance == null)
            synchronized (ContentValidator.class){
                if (instance == null)
                    instance = new ContentValidator();
            }
        return instance;
    }
    /**
     * @param contentAdapter
     * @return
     * @throws ValidationInfoException
     */

    @Override
    public void isValidForCreate(Content contentAdapter) throws ValidationInfoException {
        if (contentAdapter == null) {
            throw new ValidatorRequiredFieldIsNullException(new InvalidInfo());
        }
        RealEntity realEntity = contentAdapter.getRealEntity();
        UTIL.validateFieldsExceptIdOnNull(realEntity);
        reallyValidate(contentAdapter);
    }


    @Override
    public void isValidForUpdate(Content contentAdapter) throws ValidationInfoException {
        if (contentAdapter == null) {
            throw new ValidatorRequiredFieldIsNullException(new InvalidInfo());
        }
        RealEntity realEntity = contentAdapter.getRealEntity();
        if (contentAdapter.getContent() == null || UTIL.validateId(realEntity)||
            (contentAdapter.getTitle() == null && contentAdapter.getTitleFieldName() == null)){
        }
        reallyValidate(contentAdapter);
    }

    private void reallyValidate(Content contentAdapter) throws ValidationInfoException {
        Class clazz = contentAdapter.getRealEntity().getClass();
        List<String>  result = new ArrayList<>();

        String contentFieldName = contentAdapter.getContentFieldName();
        boolean b = validateOneField(clazz, contentFieldName, contentAdapter.getContent());
        if (!b){
            result.add(contentFieldName);
        }
        String title = contentAdapter.getTitle();
        if (title != null && contentAdapter.getTitleFieldName() != null){
            String titleFieldName = contentAdapter.getTitleFieldName();
            boolean b1 = validateOneField(clazz, titleFieldName, contentAdapter.getTitle());
            if (!b1){
                result.add(titleFieldName);
            }
        }
        if (!result.isEmpty()){
            InvalidInfo info = new InvalidInfo(clazz.getSimpleName(), result);
            throw new ValidationInfoException(info);
        }
    }


    private boolean validateOneField(Class clazz, String name, String value){
        double maxValue = NAVIGATOR.getMaxValue(clazz, name);
        double minValue = NAVIGATOR.getMinValue(clazz, name);
        return !(value.length() > maxValue || value.length() < minValue);
    }


    public String escapeXml(String s) {
        return s.replaceAll("&", "&amp;")
                .replaceAll(">", "&gt;").replaceAll("<", "&lt;")
                .replaceAll("\"", "&quot;").replaceAll("'", "&apos;");
    }
}
