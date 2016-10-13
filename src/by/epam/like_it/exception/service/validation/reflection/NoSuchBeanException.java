package by.epam.like_it.exception.service.validation.reflection;


public class NoSuchBeanException extends ValidationReflectionException{

    private String failedBean;

    public NoSuchBeanException() {
    }

    public NoSuchBeanException(String message) {
        super(message);
    }

    public NoSuchBeanException(String message, Exception e) {
        super(message, e);
    }

    public String getFailedBean() {
        return failedBean;
    }

    public void setFailedBean(String failedBean) {
        this.failedBean = failedBean;
    }
}
