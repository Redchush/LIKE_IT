package by.epam.like_it.controller.command.impl.command_with_restriction.edit;


import by.epam.like_it.controller.command.impl.command_with_restriction.edit.edit_content.EditEntityCommand;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.exception.command.InvalidDataCommandException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.bean.util_interface.RealEntity;

import javax.servlet.http.HttpServletRequest;

public class EditSettings extends EditEntityCommand{


    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String CONFIRM ="confirm_pas";
    private static EditSettings instance;

    private EditSettings(){}

    public static EditSettings getInstance(){

        if (instance == null)
            synchronized (EditSettings.class){
                if (instance == null)
                    instance = new EditSettings();
            }
        return instance;
    }


    @Override
    protected RealEntity getEntity(HttpServletRequest request, Long who) throws CommandException {
        String confirmation = request.getParameter(CONFIRM);
        String password = request.getParameter(PASSWORD);
        if (!confirmation.equals(password)){
            MSG_HANDLER.handleFailedValidation(request, User.class, "confirm");
            throw new InvalidDataCommandException();
        }
        return super.getEntity(request, who);
    }
}
