package by.epam.like_it.controller.command.impl.search;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.PageNavigator;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.controller.command.util.PostPagePaginator;
import by.epam.like_it.service.PostService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.TagService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewPostsList implements Command{

    private static final PageNavigator NAVIGATOR = PageNavigator.getInstance();
    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    private static final PostService POST_SERVICE;
    private static final TagService TAG_SERVICE;

    static {
        ServiceFactory factory = ServiceFactory.getInstance();
        POST_SERVICE = factory.getPostService();
        TAG_SERVICE = factory.getTagService();
    }

    private static ViewPostsList instance;

    private ViewPostsList(){}

    public static ViewPostsList getInstance(){

        if (instance == null)
            synchronized (ViewPostsList.class){
                if (instance == null)
                    instance = new ViewPostsList();
            }
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        PostPagePaginator.getInstance().findAndPaginate(request);
        return NAVIGATOR.getPage(PageNavigator.Pages.MAIN);
    }


}
