package by.epam.like_it.service.validator.impl;


import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.service.validator.Validator;

public class SearchDefinitionValidator implements Validator<Criteria> {

    private static SearchDefinitionValidator instance;

    private SearchDefinitionValidator(){}

    public static SearchDefinitionValidator getInstance(){

        if (instance == null)
            synchronized (SearchDefinitionValidator.class){
                if (instance == null)
                    instance = new SearchDefinitionValidator();
            }
        return instance;
    }


    @Override
    public void isValidForCreate(Criteria entity) throws ValidationInfoException {

    }

    @Override
    public void isValidForUpdate(Criteria entity) throws ValidationInfoException {

    }


}
