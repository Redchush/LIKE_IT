package by.epam.like_it.exception.service.validation.info;


import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;

public class ValidatorRequiredFieldIsNullException extends ValidationInfoException {



    public ValidatorRequiredFieldIsNullException(InvalidInfo invalidBean) {
        super(invalidBean);
    }

    public ValidatorRequiredFieldIsNullException(String message) {
        super(message);
    }

    public ValidatorRequiredFieldIsNullException(String message, Exception ex) {
        super(message, ex);
    }
}
