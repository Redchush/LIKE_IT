package by.epam.like_it.controller.command.impl.command_with_restriction.delete;

import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.controller.command.impl.AbstractPostAuthCommand;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.service.EntityService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.epam.like_it.common_util.AuthSecurityConstants.ADMIN_VALUE;
import static by.epam.like_it.common_util.AuthSecurityConstants.IS_ADMIN_ATTR;


public abstract class AbstractDeleteResponsibleCommand extends AbstractPostAuthCommand {

    @Override
    public String reallyExecute(HttpServletRequest request, HttpServletResponse response, Long who)
            throws ServiceSystemException, CommandException {

        String id = request.getParameter(CommandConstants.ID_FROM_POST_PARAM);
        Long longId = Long.parseLong(id);
        DeletableByBan deletableByBan = getDeletableByBan(request, who, longId);
        Boolean isAdmin = (Boolean) request.getAttribute(IS_ADMIN_ATTR);
        if (isAdmin == null){
            isAdmin = checkOneMoreTime(request);
        }
        EntityService banService = SERVICE_FACTORY.getEntityService();
        if (isAdmin){
            try{
                boolean b = banService.deleteEntity(deletableByBan);
            } catch (ServiceSystemException e){
                throw new CommandException();
            }
        } else {
            boolean b = banService.deleteEntityByOwner(deletableByBan);
        }
        return null;
    }

    protected Boolean checkOneMoreTime(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null){
            return false;
        }
        Object attribute = session.getAttribute(AuthSecurityConstants.ROLE_ATTR);
        return ADMIN_VALUE.equals(attribute);
    }

    protected abstract DeletableByBan getDeletableByBan(HttpServletRequest request, Long who, Long what);

}
