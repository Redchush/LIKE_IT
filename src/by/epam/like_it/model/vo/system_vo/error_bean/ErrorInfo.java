package by.epam.like_it.model.vo.system_vo.error_bean;

/**
 * as transport between dao, service and view layer : bear information about failed action
 */
public class ErrorInfo {

    public static final String PRIMARY_VALUE = "PRIMARY";

    private String failedBean;
    private String failedField;
    private String failedAction;
    private String valueViolated;

    public ErrorInfo() {}

    public ErrorInfo(String failedBean, String failedField) {
        this.failedBean = failedBean;
        this.failedField = failedField;
    }

    public String getFailedField() {
        return failedField;
    }

    public void setFailedField(String failedField) {
        this.failedField = failedField;
    }

    public String getFailedBean() {
        return failedBean;
    }

    public void setFailedBean(String failedBean) {
        this.failedBean = failedBean;
    }

    public String getFailedAction() {
        return failedAction;
    }

    public void setFailedAction(String failedAction) {
        this.failedAction = failedAction;
    }

    public String getValueViolated() {
        return valueViolated;
    }

    public void setValueViolated(String valueViolated) {
        this.valueViolated = valueViolated;
    }


}
