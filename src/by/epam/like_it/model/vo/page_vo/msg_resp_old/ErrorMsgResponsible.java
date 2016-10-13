package by.epam.like_it.model.vo.page_vo.msg_resp_old;


import java.util.Locale;

public class ErrorMsgResponsible {

    private String failedField;
    private String action;
    private String result;
    private Locale locale;

    public String getFailedField() {
        return failedField;
    }

    public void setFailedField(String failedField) {
        this.failedField = failedField;
    }
}
