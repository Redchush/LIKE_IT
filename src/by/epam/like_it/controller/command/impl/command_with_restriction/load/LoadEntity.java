package by.epam.like_it.controller.command.impl.command_with_restriction.load;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.PageNavigator;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.controller.command.util.MsgHandler;
import by.epam.like_it.exception.service.ServiceException;
import by.epam.like_it.exception.service.action.ServiceEntityBannedException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import by.epam.like_it.service.PrevQueryService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.EntityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.epam.like_it.controller.command.util.CommandConstants.ENTITY_BEAN_LOADED;
import static by.epam.like_it.controller.command.util.CommandConstants.ENTITY_BEAN_TO_LOAD;

public class LoadEntity extends AbstractLoadEntityCommand {

    private static LoadEntity instance;

    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static final ServiceFactory SERVICE_FACTORY = ServiceFactory.getInstance();
    private static final MsgHandler MSG_HANDLER = MsgHandler.getInstance();


    private LoadEntity(){}

    public static LoadEntity getInstance(){

        if (instance == null)
            synchronized (LoadEntity.class){
                if (instance == null)
                    instance = new LoadEntity();
            }
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        DeletableByBan entity = (DeletableByBan) request.getAttribute(ENTITY_BEAN_TO_LOAD);
        LOGGER.debug(entity);
        EntityService entityService = SERVICE_FACTORY.getEntityService();
        String resultPage = getPrevQuery(request);
        try {
            DeletableByBan oneDeletableByBan = entityService.findOneDeletableByBan(entity);
            request.setAttribute(ENTITY_BEAN_LOADED, oneDeletableByBan);
        } catch (ServiceEntityBannedException e){
            MSG_HANDLER.handleEntityBanned(request, entity.getClass());
            resultPage = PageNavigator.getInstance().getPage(PageNavigator.Pages.NOT_FOUND);
        } catch (ServiceSystemException e) {
            throw new CommandException("Internal error during finding entity " + entity);
        }
        return resultPage;
    }
}
