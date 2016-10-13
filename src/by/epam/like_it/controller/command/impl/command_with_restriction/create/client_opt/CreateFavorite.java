package by.epam.like_it.controller.command.impl.command_with_restriction.create.client_opt;


import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.controller.command.impl.AbstractPostAuthCommand;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.exception.service.action.ServiceDuplicateException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.bean.FavoriteUserPost;
import by.epam.like_it.service.ClientService;
import by.epam.like_it.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateFavorite extends AbstractPostAuthCommand {

    private static CreateFavorite instance;

    private CreateFavorite(){}

    public static CreateFavorite getInstance(){

        if (instance == null)
            synchronized (CreateFavorite.class){
                if (instance == null)
                    instance = new CreateFavorite();
            }
        return instance;
    }

    @Override
    public String reallyExecute(HttpServletRequest request, HttpServletResponse response, Long who)
            throws CommandException, ServiceDuplicateException, ServiceSystemException {
        ClientService service = ServiceFactory.getInstance().getClientService();
        FavoriteUserPost favPost = new FavoriteUserPost();
        favPost.setUserId(who);
           /* not deleteByBanIfPossible */
        String postId = request.getParameter(CommandConstants.POST_PARENT_ID_PARAM);
        Long postIdLong = Long.parseLong(postId);
        favPost.setPostId(postIdLong);
        Long favorite = service.createFavorite(favPost);
        LOGGER.debug("Favorite created " + favorite);
        return null;
    }
}
