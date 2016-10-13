package by.epam.like_it.controller.command.impl.search;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.PageNavigator;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.controller.command.util.PostPagePaginator;
import by.epam.like_it.model.to.TransferChosen;
import by.epam.like_it.model.vo.db_vo.SimpleCategoryVO;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;
import by.epam.like_it.model.criteria_to.facade.PostTagCriteria;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.model.vo.db_vo.TagVO;
import by.epam.like_it.service.*;
import by.epam.like_it.exception.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import static by.epam.like_it.controller.command.util.CommandConstants.*;

public class LoadMain implements Command {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    private static final String TAG_LIMIT = "tag_lim_per_page";
    private static final String POST_LIMIT = "post_lim_per_page";
    private static final String CATEGORY_LIMIT = "category_lim_per_page";

    private static final int ACCESSIBLE_PAGES = 4;

    private static final String START_SEARCH = "start";
    private static final String START_SEARCH_VALUE = "true";

    private static final PageNavigator NAVIGATOR = PageNavigator.getInstance();
    private static final PostService POST_SERVICE;
    private static final TagService TAG_SERVICE;
    private static final CategoryService CATEGORY_SERVICE;
    private static final PostPagePaginator PAGINATOR;

    static {
        ServiceFactory factory = ServiceFactory.getInstance();
        POST_SERVICE = factory.getPostService();
        TAG_SERVICE = factory.getTagService();
        CATEGORY_SERVICE = factory.getCategoryService();
        PAGINATOR = PostPagePaginator.getInstance();
    }

    private static LoadMain instance;

    private LoadMain(){}

    public static LoadMain getInstance(){

        if (instance == null)
            synchronized (LoadMain.class){
                if (instance == null)
                    instance = new LoadMain();
            }
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        LOGGER.trace("Executing load_main");
        String parameter = request.getParameter(START_SEARCH);
        if (parameter != null){
            return NAVIGATOR.getPage(PageNavigator.Pages.MAIN);
        }
        InitialPostCriteria postCriteria = PAGINATOR.findAndPaginate(request);

        HttpSession session = request.getSession(true);
        PostTagCriteria tagCriteria = processTag(request, session);

//        processCategory(session);

        session.setAttribute(CURRENT_ORDER_CLASS, DEFAULT_ORDER_CLASS);
        session.setAttribute(IS_SORT_TYPE_ASC, DEFAULT_IS_SORT_TYPE_ASC);
        session.setAttribute(MAIN_POST_CRITERIA, postCriteria);

        LOGGER.trace("Tag and category loaded. Wait for posts will be found");
        return NAVIGATOR.getPage(PageNavigator.Pages.MAIN);
    }


    private PostTagCriteria processTag(HttpServletRequest request, HttpSession session) throws CommandException{
        PostTagCriteria tagCriteria = new PostTagCriteria();

        PAGINATOR.processInitialLimit(request, TAG_LIMIT, tagCriteria);

        ListCounterResponse<TagVO> listCounterResponse = null;
        ArrayList<TagVO> allLists = null;
        Long total = null;
        TransferChosen<TagVO> tagVOTransferChosen;
        try {
            listCounterResponse = TAG_SERVICE.findPostTagWithInfo(tagCriteria);
            allLists = new ArrayList<>(listCounterResponse.getItems());

            total = listCounterResponse.getTotal();
            tagVOTransferChosen = TAG_SERVICE.splitForChosen(allLists, new Integer[0]);
            NavigableMap<Integer, TagVO> remain = tagVOTransferChosen.getRemain();
            NavigableMap<Integer, TagVO> chosen = tagVOTransferChosen.getChosen();

            session.setAttribute(ALL_TAGS_LIST, allLists);

            session.setAttribute(POSSIBLE_TAGS_MAP, new TreeMap<Integer, TagVO>(remain));
            session.setAttribute(CHOSEN_TAGS_MAP, new TreeMap<Integer, TagVO>(chosen));
            session.setAttribute(TOTAL_TAGS_COUNT, total);

        } catch (ServiceException e) {
            LOGGER.info("Fail find tags for main page. Proceed without it ");
        }
         return tagCriteria;
    }

    private int getSpareCount(int limit){
        return limit*ACCESSIBLE_PAGES;
    }

    @Deprecated
    /*category deleted*/
    private void processCategory(HttpSession session){
        List<SimpleCategoryVO> categoryTree =null;
        try {
            categoryTree = CATEGORY_SERVICE.findCategoryTree();
        } catch (ServiceException e) {
            LOGGER.info("Fail find categories for main page. Proceed without it ");
        }
        session.setAttribute(CURRENT_CATEGORIES_ATTR, categoryTree);
    }
}
