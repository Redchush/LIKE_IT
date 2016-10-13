package by.epam.like_it.model.vo.validation_vo.factory;


import by.epam.like_it.model.vo.validation_vo.info.ValidationInfoLong;
import by.epam.like_it.service.validator.util.ValidationNavigator;

/**
 * for usage on jsp
 */
public class SingleValidationInfoFactory {

    public enum Format{
        LONG, DOUBLE
    }

    private String beanName;
    private String field;

    private MultipleValidationInfoFactory.Format format;

    public SingleValidationInfoFactory() {
        this.format = MultipleValidationInfoFactory.Format.LONG;
    }

    public SingleValidationInfoFactory(String beanName, String fields) {
        this();
        this.beanName = beanName;
        this.field = fields;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ValidationInfoLong getInfo(){
        if (beanName == null || field == null){
            return null;
        } else {
            return ValidationNavigator.getInstance().getValidationInfoLong(beanName, field);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MultipleValidationInfoFactory{");
        sb.append("beanName='").append(beanName).append('\'');
        sb.append(", field=").append(field);
        sb.append(", format=").append(format);
        sb.append('}');
        return sb.toString();
    }
}
