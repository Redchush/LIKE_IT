package by.epam.like_it.controller.command.impl.command_with_restriction.create.auth_opt;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.model.adapter.CommentContent;
import by.epam.like_it.model.adapter.Content;
import by.epam.like_it.model.bean.Comment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CreateComment extends AbstractCreateContentCommand {

    protected static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static CreateComment instance;

    private CreateComment(){}

    public static CreateComment getInstance(){

        if (instance == null)
            synchronized (CreateComment.class){
                if (instance == null)
                    instance = new CreateComment();
            }
        return instance;
    }

    @Override
    protected Content getContent(HttpServletRequest request, Long who, String content) throws CommandException {
        String parameter = request.getParameter(PARENT_ID_PARAM);
        CommentContent result;
        if (parameter == null){
            throw new CommandException("No parent!!!");
        } else {
            long l = Long.parseLong(parameter);
            Comment comment = new Comment();
            comment.setUserId(who);
            comment.setContent(content);
            comment.setAnswerId(l);
            result = new CommentContent(comment);
        }
        return result;
    }
}
