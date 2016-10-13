package by.epam.like_it.controller.command.impl.command_with_restriction.edit;


import by.epam.like_it.controller.command.impl.AbstractPostAuthCommand;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.controller.command.util.TagResponsible;
import by.epam.like_it.exception.service.action.ServiceDuplicateException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.to.HistoryArrayTransfer;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import by.epam.like_it.service.TagService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditSubscribing extends AbstractPostAuthCommand{

    private static EditSubscribing instance;
    private final TagResponsible PREV_ID_RESPONSIBLE = TagResponsible.getInstance();

    private EditSubscribing(){}

    public static EditSubscribing getInstance(){

        if (instance == null)
            synchronized (EditSubscribing.class){
                if (instance == null)
                    instance = new EditSubscribing();
            }
        return instance;
    }

    @Override
    public String reallyExecute(HttpServletRequest request, HttpServletResponse response, Long who)
            throws ServiceSystemException, ServiceDuplicateException {
        HistoryArrayTransfer<String> transfer = PREV_ID_RESPONSIBLE.getTransfer(request);
        TagService tagService = SERVICE_FACTORY.getTagService();
        tagService.changeUserSubscribing(who, transfer);
        PrevQuery prevQuery = (PrevQuery) request.getSession().getAttribute(CommandConstants.PREV_QUERY_SESS_ATTR);
        return prevQuery.getBackQuery();
    }
}
