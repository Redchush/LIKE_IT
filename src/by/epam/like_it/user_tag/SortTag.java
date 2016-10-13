package by.epam.like_it.user_tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class SortTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;

    private String checkedOut;
    private String classToAdd;
    private String valueToFind;


    public void setCheckedOut(String checkedOut) {
        this.checkedOut = checkedOut;
    }

    public void setClassToAdd(String classToAdd) {
        this.classToAdd = classToAdd;
    }

    public void setValueToFind(String valueToFind) {
        this.valueToFind = valueToFind;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print("");
        } catch (IOException e) {
            throw new JspException("Error", e);
        }
        return SKIP_BODY;
    }

    private String getFormattedBody(){
        return null;
    }
}
