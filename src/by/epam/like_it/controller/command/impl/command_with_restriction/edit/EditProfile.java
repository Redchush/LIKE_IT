package by.epam.like_it.controller.command.impl.command_with_restriction.edit;


import by.epam.like_it.controller.command.impl.command_with_restriction.edit.edit_content.EditEntityCommand;

public class EditProfile extends EditEntityCommand {

    private static EditProfile instance;

    private EditProfile(){}

    public static EditProfile getInstance(){

        if (instance == null)
            synchronized (EditProfile.class){
                if (instance == null)
                    instance = new EditProfile();
            }
        return instance;
    }

//    @Override
//    protected RealEntity getEntity(HttpServletRequest request, Long who) throws CommandException {
//        User entity = (User) super.getEntity(request, who);
//        boolean isAboutMeEmpty = entity.getAboutMe().isEmpty();
//        boolean isFirstNameIsEmpty = entity.getFirstName().isEmpty();
//        boolean isLastNameIsEmpty = entity.getLastName().isEmpty();
//        if (isAboutMeEmpty && isFirstNameIsEmpty && isLastNameIsEmpty){
//            throw new InvalidDataCommandException();
//        }
//        return entity;
//    }
}
