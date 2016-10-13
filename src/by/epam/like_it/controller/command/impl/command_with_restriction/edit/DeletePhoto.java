package by.epam.like_it.controller.command.impl.command_with_restriction.edit;


import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.controller.command.impl.AbstractPostAuthCommand;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.exception.service.action.ServiceActionDetectableException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.PhotoVo;
import by.epam.like_it.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeletePhoto extends AbstractPostAuthCommand {

    private static DeletePhoto instance;

    private DeletePhoto(){}

    public static DeletePhoto getInstance(){

        if (instance == null)
            synchronized (DeletePhoto.class){
                if (instance == null)
                    instance = new DeletePhoto();
            }
        return instance;
    }


    @Override
    public String reallyExecute(HttpServletRequest request, HttpServletResponse response, Long who)
            throws ServiceSystemException, CommandException, ServiceActionDetectableException {
        UserService userService = SERVICE_FACTORY.getUserService();
        HttpSession session = request.getSession();
        String attribute = (String) session.getAttribute(AuthSecurityConstants.PHOTO_PATH);
        User user = new User();
        user.setId(who);
        PhotoVo vo = new PhotoVo();
        vo.setUser(user);
        vo.setPrevPhoto(attribute);
        String realPath = request.getServletContext().getRealPath("/");
        vo.setRealPath(realPath);
        boolean result = userService.deletePhoto(vo);
        if (result){
            session.setAttribute(AuthSecurityConstants.PHOTO_PATH, AuthSecurityConstants.DEFAULT_PHOTO_PATH);
        }

        return null;
    }
}
