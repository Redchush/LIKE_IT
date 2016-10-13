package by.epam.like_it.model.vo.system_vo.error_bean;


import java.util.ArrayList;
import java.util.List;

/**
 * as transport between service and view layer
 */
public class InvalidInfo {

    private static final int errorCode = 1;

    private List<String> failedFields;
    private String failedBean;

    public InvalidInfo() {
        failedFields = new ArrayList<>();
    }

    public InvalidInfo(String failedBean) {
        super();
        this.failedBean = failedBean;
    }

    public InvalidInfo(String failedBean,  List<String>  failedFields) {
        this.failedFields = failedFields;
        this.failedBean = failedBean;
    }

    public  List<String>  getFailedFields() {
        return failedFields;
    }

    public void setFailedFields( List<String>  failedFields) {
        this.failedFields = failedFields;
    }

    public String getFailedBean() {
        return failedBean;
    }

    public void setFailedBean(String failedBean) {
        this.failedBean = failedBean;
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvalidInfo{");
        sb.append(" failedBean='").append(failedBean).append('\'');
        sb.append(", failedFields=").append(failedFields);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvalidInfo info = (InvalidInfo) o;

        //noinspection SimplifiableIfStatement
        if (failedFields != null ? !failedFields.equals(info.failedFields) : info.failedFields != null) {
            return false;
        }
        return failedBean != null ? failedBean.equals(info.failedBean) : info.failedBean == null;

    }

    @Override
    public int hashCode() {
        int result = failedFields != null ? failedFields.hashCode() : 0;
        result = 31 * result + (failedBean != null ? failedBean.hashCode() : 0);
        return result;
    }
}
