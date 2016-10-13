package by.epam.like_it.controller.command.impl.command_with_restriction.edit.edit_content;


public class EditComment extends EditEntityCommand {

    private static EditComment instance;

    private EditComment(){}

    public static EditComment getInstance(){

        if (instance == null)
            synchronized (EditComment.class){
                if (instance == null)
                    instance = new EditComment();
            }
        return instance;
    }


}
