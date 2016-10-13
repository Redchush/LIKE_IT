package by.epam.like_it.service.validator;

import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.util_interface.Entity;
import by.epam.like_it.service.validator.util.ValidationNavigator;

/**
 * Provides interface to validate input parameters
 * @param <T> - param of entity to be validated
 */
public interface Validator<T> {

    ValidationNavigator NAVIGATOR = ValidationNavigator.getInstance();


    /**
     * Validates the values in the fields of the object entity. Throw an ValidationInfoException with the InvalidInfo
     * object in case  of  failing validation
     * @param entity object type T which is need vo validate.
     *
     */
    void isValidForCreate(T entity) throws ValidationInfoException;

    void isValidForUpdate(T entity) throws ValidationInfoException;
}
