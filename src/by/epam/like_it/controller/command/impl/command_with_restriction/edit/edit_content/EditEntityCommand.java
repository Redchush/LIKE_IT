package by.epam.like_it.controller.command.impl.command_with_restriction.edit.edit_content;

import by.epam.like_it.controller.command.impl.AbstractPostAuthCommand;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.exception.command.CommandException;

import by.epam.like_it.exception.service.action.ServiceActionDetectableException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import by.epam.like_it.model.vo.system_vo.prev_query.PrevQuery;
import by.epam.like_it.service.EntityService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;


public abstract class EditEntityCommand extends AbstractPostAuthCommand {

    @Override
    public String reallyExecute(HttpServletRequest request, HttpServletResponse response, Long who)
            throws CommandException, ValidationInfoException, ServiceSystemException, ServiceActionDetectableException {
        HttpSession session = request.getSession();
        PrevQuery prevQuery = (PrevQuery) session.getAttribute(CommandConstants.PREV_QUERY_SESS_ATTR);

        boolean b = executeUpdate(request, who);
        return prevQuery.getBackQuery();
    }

    protected boolean executeUpdate(HttpServletRequest request, Long who)
            throws CommandException, ValidationInfoException, ServiceSystemException, ServiceActionDetectableException {
        EntityService contentService = SERVICE_FACTORY.getEntityService();
        RealEntity entity = getEntity(request, who);
        return contentService.updateRealEntity(entity);
    }

    protected RealEntity getEntity(HttpServletRequest request, Long who)
            throws CommandException {
        return (RealEntity) request.getAttribute(CommandConstants.ENTITY_BEAN_LOADED);
    }

    protected boolean ifReferrerValid(PrevQuery prevQuery, String idVal){
        Map<String, String[]> params = prevQuery.getParams();
        String[] strings = params.get(CommandConstants.ID_FROM_GET_PARAM);
        return !(strings.length == 0 || strings[0].equals(idVal));
    }

}
