package by.epam.like_it.controller.command.impl.command_with_restriction.create.auth_opt;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.model.adapter.AnswerContent;
import by.epam.like_it.model.adapter.Content;
import by.epam.like_it.model.bean.Answer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CreateAnswer extends AbstractCreateContentCommand {

    protected static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static CreateAnswer instance;

    private CreateAnswer(){}

    public static CreateAnswer getInstance(){

        if (instance == null)
            synchronized (CreateAnswer.class){
                if (instance == null)
                    instance = new CreateAnswer();
            }
        return instance;
    }

    @Override
    protected Content getContent(HttpServletRequest request, Long who, String content) throws CommandException {
        String parent_id = request.getParameter(PARENT_ID_PARAM);
        AnswerContent result;
        if (parent_id == null){
            throw new CommandException("No parent!!!");
        } else {
            long l = Long.parseLong(parent_id);
            Answer comment = new Answer();
            comment.setUserId(who);
            comment.setContent(content);
            comment.setPostId(l);
            result = new AnswerContent(comment);
        }
        return result;
    }
}
