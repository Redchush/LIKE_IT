package by.epam.like_it.service.validator.impl.user_validator;


import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;
import by.epam.like_it.service.validator.Validator;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class UserValidator implements Validator<User> {

    private final static Pattern PATTERN_EMAIL;
    private final static Pattern PATTERN_LOGIN;
    private final static Pattern PATTERN_PASSWORD;

    static {
        final  String EMAIL_REG = NAVIGATOR.getPattern("User", "email");
        final  String PASS_PATTERN = NAVIGATOR.getPattern("User", "password");
        final  String LOGIN_PATTERN = NAVIGATOR.getPattern("User", "login");
        PATTERN_EMAIL = Pattern.compile(EMAIL_REG);
        PATTERN_LOGIN = Pattern.compile(LOGIN_PATTERN);
        PATTERN_PASSWORD = Pattern.compile(PASS_PATTERN);
    }

    protected boolean validateLogin(String login) {
        final int LOGIN_MAX_LENGTH = (int) NAVIGATOR.getMaxValue(User.class, "login");
        final int LOGIN_MIN_LENGTH = (int) NAVIGATOR.getMinValue(User.class, "login");
        if (login==null || login.isEmpty() || login.length() < LOGIN_MIN_LENGTH || login.length() > LOGIN_MAX_LENGTH){
            return false;
        }
        Matcher matcher = PATTERN_LOGIN.matcher(login);
        return matcher.matches();
    }

    protected  boolean validateEmail(String email){
        final int EMAIL_MIN_LENGTH = (int) NAVIGATOR.getMinValue(User.class, "email");
        final int EMAIL_MAX_LENGTH = (int) NAVIGATOR.getMaxValue(User.class, "email");
        if(email == null || email.isEmpty() || email.length() < EMAIL_MIN_LENGTH || email.length() > EMAIL_MAX_LENGTH){
            return false;
        }
        Matcher matcher = PATTERN_EMAIL.matcher(email);
        return matcher.matches();
    }

    protected boolean validatePassword(String password){
        final int PASSWORD_MAX_LENGTH = (int) NAVIGATOR.getMaxValue(User.class, "password");
        final int PASSWORD_MIN_LENGTH = (int) NAVIGATOR.getMinValue(User.class, "password");

        if(password == null || password.isEmpty() || password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH){
            return false;
        }
        Matcher matcher = PATTERN_PASSWORD.matcher(password);
        return matcher.matches();
    }

    @Override
    public void isValidForUpdate(User entity) throws ValidationInfoException {
        if (entity == null || entity.getId() == null){
            throw new ValidationInfoException(new InvalidInfo("User", Collections.singletonList("id")));
        }
    }
}
