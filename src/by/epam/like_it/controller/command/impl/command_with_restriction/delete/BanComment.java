package by.epam.like_it.controller.command.impl.command_with_restriction.delete;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.model.bean.Comment;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class BanComment extends AbstractDeleteResponsibleCommand {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static BanComment instance;

    private BanComment(){}

    public static BanComment getInstance(){

        if (instance == null)
            synchronized (BanComment.class){
                if (instance == null)
                    instance = new BanComment();
            }
        return instance;
    }


    @Override
    protected DeletableByBan getDeletableByBan(HttpServletRequest request, Long who, Long what) {
        Comment comment = new Comment();
        comment.setId(what);
        comment.setUserId(who);
        return comment;
    }
}
