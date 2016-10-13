package by.epam.like_it.service.validator.impl.user_validator;


import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;

import java.util.ArrayList;
import java.util.List;

public class UserRegistrationValidator extends UserValidator{

    private static UserRegistrationValidator instance;

    private UserRegistrationValidator(){}

    public static UserRegistrationValidator getInstance(){

        if (instance == null)
            synchronized (UserRegistrationValidator.class){
                if (instance == null)
                    instance = new UserRegistrationValidator();
            }
        return instance;
    }


    @Override
    public void isValidForCreate(User entity) throws ValidationInfoException {
        if (entity == null) {
            throw new ValidationInfoException(new InvalidInfo("User"));
        }

        String login = entity.getLogin();
        String email = entity.getEmail();
        String password = entity.getPassword();
        List<String> result = new ArrayList<>();
        if (!validatePassword(password)){
            result.add("password");
        }
        if (!validateLogin(login)){
            result.add("login");
        }
        if (!validateEmail(email)){
            result.add("email");
        }
        if (!result.isEmpty()){
            throw new ValidationInfoException(new InvalidInfo("User", result));
        }

    }
}
