package by.epam.like_it.controller.command.impl.command_with_restriction.edit.edit_content;


public class EditPost extends EditEntityCommand {

    private static EditPost instance;

    private EditPost(){}

    public static EditPost getInstance(){

        if (instance == null)
            synchronized (EditPost.class){
                if (instance == null)
                    instance = new EditPost();
            }
        return instance;
    }
}
