package by.epam.like_it.exception.service.validation.reflection;


public class NoSuchFieldException  extends ValidationReflectionException{

    private String failedBean;
    private String failedField;

    public NoSuchFieldException() {
    }

    public NoSuchFieldException(String message) {
        super(message);
    }

    public NoSuchFieldException(String message, Exception e) {
        super(message, e);
    }

    public String getFailedBean() {
        return failedBean;
    }

    public void setFailedBean(String failedBean) {
        this.failedBean = failedBean;
    }

    public String getFailedField() {
        return failedField;
    }

    public void setFailedField(String failedField) {
        this.failedField = failedField;
    }
}
