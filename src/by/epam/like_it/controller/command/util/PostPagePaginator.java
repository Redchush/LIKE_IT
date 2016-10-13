package by.epam.like_it.controller.command.util;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.model.criteria_to.core.Limit;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.service.PostService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.exception.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.epam.like_it.controller.command.util.CommandConstants.MAIN_POST_CRITERIA;

public class PostPagePaginator extends PagePaginator {

    private static final String POST_LIMIT = "post_lim_per_page";

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static final PostService POST_SERVICE;

    static {
        ServiceFactory factory = ServiceFactory.getInstance();
        POST_SERVICE = factory.getPostService();
    }

    private PostPagePaginator() {}

    private static PostPagePaginator instance;

    public static PostPagePaginator getInstance(){

        if (instance == null)
            synchronized (PostPagePaginator.class){
                if (instance == null)
                    instance = new PostPagePaginator();
            }
        return instance;
    }

    /**
     * Get criteria from session. If there no session - create it, if no creteria - create it and storage in session
     *
     * @param request
     * @return
     */
    public InitialPostCriteria getCriteria(HttpServletRequest request, HttpSession currentSession){
        HttpSession session = currentSession;
        if (session == null){
            session = request.getSession(true);
        }
        InitialPostCriteria criteria = (InitialPostCriteria)
                session.getAttribute(MAIN_POST_CRITERIA);
        if (criteria == null){
            criteria = new InitialPostCriteria();
            session.setAttribute(MAIN_POST_CRITERIA, criteria);
            super.processInitialLimit(request, POST_LIMIT, criteria);
        }
        return criteria;
    }

    /**
     *
     * @param request
     * @return InitialPostCriteria after processing
     * @throws CommandException
     */

    public InitialPostCriteria findAndPaginate(HttpServletRequest request) throws CommandException {

        InitialPostCriteria postCriteria = getCriteria(request, null);
        int currentPage = super.getCurrentPage(request);

        int start = (currentPage  - 1)*postCriteria.getPerPage();
        Limit limit = postCriteria.getLimit();
        if (limit != null){
            limit.setStart(start);
        }
        /*new request criteria_to db for next posts*/
        ListCounterResponse<PostVO> listCounterResponse = null;
        List<PostVO> allLists = null;
        Long total = null;
        try {
            listCounterResponse = POST_SERVICE.findListCounterResponse(postCriteria);
            allLists = listCounterResponse.getItems();
            total = listCounterResponse.getTotal();
            LOGGER.debug("Was found allLists " + allLists + ",\n total  " + total);
        } catch (ServiceException e) {
            LOGGER.error("Fail execute finding post by criteria " + postCriteria);
            throw new CommandException("Can't find the posts by request");
        }
        setAccessiblePages(request, total, postCriteria.getPerPage(), currentPage);
        request.setAttribute(CommandConstants.CURRENT_POSTS_ATTR, allLists);  //post will be shown on page
        request.setAttribute(CommandConstants.TOTAL_POSTS_COUNT, total);  //count of posts found
        LOGGER.debug("Was found allLists " + allLists + ",\n total  " + total);
        return postCriteria;
    }
}
