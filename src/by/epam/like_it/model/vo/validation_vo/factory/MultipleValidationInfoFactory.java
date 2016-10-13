package by.epam.like_it.model.vo.validation_vo.factory;


import by.epam.like_it.model.vo.validation_vo.info.ValidationInfoLong;
import by.epam.like_it.service.MessageService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.validator.util.ValidationNavigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Produce ValidationInfo by bean name and it's fields
 */

public class MultipleValidationInfoFactory {


    public enum Format{
        LONG, DOUBLE
    }

    private String beanName;
    private String[] fields;

    private Format format;

    public MultipleValidationInfoFactory() {
        this.format = Format.LONG;
    }

    public MultipleValidationInfoFactory(String beanName, ArrayList<String>  fields) {
        this();
        this.beanName = beanName;
        this.fields = (String[]) fields.toArray();
    }

    public MultipleValidationInfoFactory(String beanName, String[] fields) {
        this();
        this.beanName = beanName;
        this.fields = fields;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public HashMap<String, ValidationInfoLong> getInfo(){
        if (beanName == null || fields == null || fields.length == 0){
            return null;
        } else {
            return new HashMap<>(ValidationNavigator.getInstance()
                                                    .getValidationMapLong(beanName, fields));
        }
    }






    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MultipleValidationInfoFactory{");
        sb.append("beanName='").append(beanName).append('\'');
        sb.append(", fields=").append(Arrays.toString(fields));
        sb.append(", format=").append(format);
        sb.append('}');
        return sb.toString();
    }
}
