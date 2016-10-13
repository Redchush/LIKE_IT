package by.epam.like_it.controller.command.impl.search;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.PageNavigator;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.controller.command.util.PostPagePaginator;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;
import by.epam.like_it.service.PostService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.TagService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.TreeMap;

import static by.epam.like_it.controller.command.util.CommandConstants.*;


public abstract class SearchPost implements Command {

    protected static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    protected static final String SEARCH_VALUE = "value";


    private static final PageNavigator NAVIGATOR;
    private static final PostService POST_SERVICE;
    private static final PostPagePaginator PAGINATOR;
    protected static final TagService TAG_SERVICE;

    static {
        ServiceFactory factory = ServiceFactory.getInstance();
        POST_SERVICE = factory.getPostService();
        PAGINATOR = PostPagePaginator.getInstance();
        NAVIGATOR = PageNavigator.getInstance();
        TAG_SERVICE = factory.getTagService();
    }


    protected void setDefaultOrderAttributes(HttpSession session) {
        session.setAttribute(CURRENT_ORDER_CLASS, DEFAULT_ORDER_CLASS);
        session.setAttribute(IS_SORT_TYPE_ASC, DEFAULT_IS_SORT_TYPE_ASC);
    }

    protected void setDefaultTagAttributes(HttpSession session){
        TreeMap<Integer, Tag> chosen =
                (TreeMap<Integer, Tag>) session.getAttribute(CommandConstants.CHOSEN_TAGS_MAP);
        TreeMap<Integer, Tag> possible =
                (TreeMap<Integer, Tag>) session.getAttribute(CommandConstants.POSSIBLE_TAGS_MAP);
        possible.putAll(chosen);
        chosen.clear();
    }

    protected void clearSessionAndCriteria(HttpSession session, InitialPostCriteria criteria){
        setDefaultOrderAttributes(session);
        setDefaultTagAttributes(session);
        criteria.setOrderAsDefaultOrder();
        criteria.setConstrictionsAsDefaultConstrictions();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        InitialPostCriteria criteria = PAGINATOR.getCriteria(request,session);
        boolean b = refactorCriteria(request, criteria);
        PAGINATOR.findAndPaginate(request);
        return NAVIGATOR.getPage(PageNavigator.Pages.MAIN);
    }

    protected abstract boolean refactorCriteria(HttpServletRequest request, InitialPostCriteria criteria)
            throws CommandException;

}
