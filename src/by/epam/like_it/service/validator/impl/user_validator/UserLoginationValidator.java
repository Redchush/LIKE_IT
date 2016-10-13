package by.epam.like_it.service.validator.impl.user_validator;


import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;

import java.util.ArrayList;
import java.util.List;

public class UserLoginationValidator extends UserValidator{

    private static UserLoginationValidator instance;

    private UserLoginationValidator(){}

    public static UserLoginationValidator getInstance(){

        if (instance == null)
            synchronized (UserLoginationValidator.class){
                if (instance == null)
                    instance = new UserLoginationValidator();
            }
        return instance;
    }

    @Override
    public void isValidForCreate(User entity) throws ValidationInfoException {
        if (entity == null) {
            throw new ValidationInfoException(new InvalidInfo("User"));
        }
        String login = entity.getLogin();
        String password = entity.getPassword();
        List<String> result = new ArrayList<>();

        if (!validatePassword(password)){
            result.add("password");
        }
        if (!validateLogin(login)){
            result.add("login");
        }
        if (!result.isEmpty()){
            throw new ValidationInfoException(new InvalidInfo("User", result));
        }
    }

}
