package by.epam.like_it.exception.service.validation.info;


import by.epam.like_it.exception.service.validation.ValidationException;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;

/**
 * The class is used vo create objects of the validation-level exceptions
 */
public class ValidationInfoException extends ValidationException {

    private static final long serialVersionUID = 1L;

    private InvalidInfo invalidInfo;

    public ValidationInfoException() {}

    public ValidationInfoException(InvalidInfo invalidBean) {
        this.invalidInfo = invalidBean;
    }

    public ValidationInfoException(String message){
        super(message);
    }

    public ValidationInfoException(String message, Exception ex){
        super(message, ex);
    }

    public InvalidInfo getInvalidInfo() {
        return invalidInfo;
    }

    public void setInvalidInfo(InvalidInfo invalidInfo) {
        this.invalidInfo = invalidInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ValidationInfoException{");
        sb.append(super.toString()).append(" ");
        sb.append('}');
        return sb.toString();
    }
}
