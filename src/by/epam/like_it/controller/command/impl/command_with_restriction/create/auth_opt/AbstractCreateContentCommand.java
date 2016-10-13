package by.epam.like_it.controller.command.impl.command_with_restriction.create.auth_opt;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.controller.command.impl.AbstractPostAuthCommand;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.adapter.Content;
import by.epam.like_it.service.EntityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract class AbstractCreateContentCommand extends AbstractPostAuthCommand {

    protected static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    protected final static String CONTENT_PARAM = "content";
    protected final static String PARENT_ID_PARAM = "parent_id";

    @Override
    public String reallyExecute(HttpServletRequest request, HttpServletResponse response, Long who)
            throws CommandException, ValidationInfoException, ServiceSystemException {
        String contentVal = request.getParameter(CONTENT_PARAM);
        if (contentVal == null){
            throw new CommandException();
        } else {
            contentVal = contentVal.trim();
        }
        executeCreateContent(request, who, contentVal);
        return null;
    }

    protected abstract Content getContent(HttpServletRequest request, Long who, String content) throws CommandException;

    protected long executeCreateContent(HttpServletRequest request, Long who, String contentVal)
            throws CommandException, ValidationInfoException, ServiceSystemException {
        EntityService createContentService = SERVICE_FACTORY.getEntityService();
        Long newId = null;
        Content content = getContent(request, who, contentVal);
        newId = createContentService.createContent(content);
        return newId;
    }

}
