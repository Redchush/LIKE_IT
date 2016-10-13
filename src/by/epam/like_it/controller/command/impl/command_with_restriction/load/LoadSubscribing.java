package by.epam.like_it.controller.command.impl.command_with_restriction.load;


import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.controller.command.util.MsgHandler;
import by.epam.like_it.controller.command.util.TagResponsible;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.criteria_to.core.Limit;
import by.epam.like_it.model.criteria_to.core.Order;
import by.epam.like_it.model.criteria_to.facade.PostTagCriteria;
import by.epam.like_it.model.to.HistoryArrayTransfer;
import by.epam.like_it.model.to.impl.TagHistoryTransfer;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import by.epam.like_it.service.TagService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.epam.like_it.controller.command.util.CommandConstants.*;

public class LoadSubscribing extends AbstractLoadEntityCommand {

    private static LoadSubscribing instance;

    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());

    private static final MsgHandler MSG_HANDLER = MsgHandler.getInstance();

    private static final Order<Tag> orderByName = new Order<>(Tag.class, "name");

    private final TagResponsible TAG_RESPONSIBLE = TagResponsible.getInstance();
    private static final int DEFAULT_LIMIT = 20;

    private LoadSubscribing(){}

    public static LoadSubscribing getInstance(){

        if (instance == null)
            synchronized (LoadSubscribing.class){
                if (instance == null)
                    instance = new LoadSubscribing();
            }
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        TagService entityService = SERVICE_FACTORY.getTagService();
        HttpSession session = request.getSession();
        PrevQuery prevQuery = (PrevQuery) session.getAttribute(CommandConstants.PREV_QUERY_SESS_ATTR);
        String resultPage = getPrevQuery(prevQuery);

        Long currentUser = (Long) session.getAttribute(AuthSecurityConstants.USER_ID_ATTR);
        PostTagCriteria userTagCriteria = new PostTagCriteria();

        PostTagCriteria commonPostTagCriteria = new PostTagCriteria();
        int limit = DEFAULT_LIMIT;
        String tagLim = request.getParameter(CommandConstants.TAG_LIMIT);
        if (tagLim!=null){
            limit = Integer.parseInt(tagLim);
        }
        commonPostTagCriteria.setLimit(new Limit(0, limit));

        String sortValue = request.getParameter(CommandConstants.SORT_TAG_PARAM);
        if (sortValue != null && sortValue.equals(SORT_BY_NAME)){
            commonPostTagCriteria.setOrder(orderByName);
            request.setAttribute(CommandConstants.SORT_DIRECTION, CommandConstants.SORT_DIRECTION_UP);
        }

        String letter = request.getParameter(CommandConstants.SORT_BY_LETTER);
        if (letter != null){
            commonPostTagCriteria.setNameStartWith(letter);
        }
        String[] prevSort = prevQuery.getParams().get(SORT_TAG_PARAM);
        String[] prevDirection = prevQuery.getParams().get(CommandConstants.SORT_DIRECTION);

        if (prevSort != null && prevSort.length != 0
                && prevDirection != null && prevDirection.length != 0
                && prevSort[0].equals(sortValue)){
            TAG_RESPONSIBLE.toggleOrder(request, prevDirection[0]);
            commonPostTagCriteria.toggleOrder();
        }

        HistoryArrayTransfer<String> transfer = TAG_RESPONSIBLE.getTransfer(request);
        try {
            TagHistoryTransfer userTagTransfer = entityService
                    .getUserTagTransfer(currentUser, userTagCriteria, commonPostTagCriteria, transfer);
            request.setAttribute(ENTITY_BEAN_LOADED, userTagTransfer);
            TAG_RESPONSIBLE.savePrevIds(request, userTagTransfer);
        } catch (ServiceSystemException e) {
            throw new CommandException("Internal service exception ", e);
        }
        return resultPage;
    }
}
