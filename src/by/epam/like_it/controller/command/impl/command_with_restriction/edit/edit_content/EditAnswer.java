package by.epam.like_it.controller.command.impl.command_with_restriction.edit.edit_content;

public class EditAnswer extends EditEntityCommand {

    private static EditAnswer instance;
    private EditAnswer(){}

    public static EditAnswer getInstance(){
        if (instance == null)
            synchronized (EditAnswer.class){
                if (instance == null)
                    instance = new EditAnswer();
            }
        return instance;
    }
}
