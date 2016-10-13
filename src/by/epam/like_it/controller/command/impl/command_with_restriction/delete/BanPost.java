package by.epam.like_it.controller.command.impl.command_with_restriction.delete;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class BanPost extends AbstractDeleteResponsibleCommand {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static BanPost instance;

    private BanPost(){}

    public static BanPost getInstance(){

        if (instance == null)
            synchronized (BanPost.class){
                if (instance == null)
                    instance = new BanPost();
            }
        return instance;
    }


    @Override
    protected DeletableByBan getDeletableByBan(HttpServletRequest request, Long who, Long what) {
        Post post = new Post();
        post.setUserId(who);
        post.setId(what);
        return post;
    }
}
