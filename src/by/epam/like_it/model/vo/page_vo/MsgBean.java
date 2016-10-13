package by.epam.like_it.model.vo.page_vo;


import java.io.Serializable;
import java.util.List;

public class MsgBean implements Serializable {


    private int code;
    private List<String> msgKeys;

    public MsgBean() {}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getMsgKeys() {
        return msgKeys;
    }

    public void setMsgKeys(List<String> msgKeys) {
        this.msgKeys = msgKeys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MsgBean bean = (MsgBean) o;

        //noinspection SimplifiableIfStatement
        if (code != bean.code) {
            return false;
        }
        return msgKeys != null ? msgKeys.equals(bean.msgKeys) : bean.msgKeys == null;

    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (msgKeys != null ? msgKeys.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MsgBean{");

        sb.append("code=").append(code);
        sb.append(", msgKeys=").append(msgKeys);
        sb.append('}');
        return sb.toString();
    }
}