package by.epam.like_it.controller.command.impl.command_with_restriction.create.client_opt;


import by.epam.like_it.controller.command.impl.AbstractPostAuthCommand;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.bean.FavoriteUserPost;
import by.epam.like_it.service.ClientService;
import by.epam.like_it.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.epam.like_it.controller.command.util.CommandConstants.ID_FROM_POST_PARAM;

public class DeleteFavorite extends AbstractPostAuthCommand{

    private static DeleteFavorite instance;

    private DeleteFavorite(){}

    public static DeleteFavorite getInstance(){

        if (instance == null)
            synchronized (DeleteFavorite.class){
                if (instance == null)
                    instance = new DeleteFavorite();
            }
        return instance;
    }

    @Override
    public String reallyExecute(HttpServletRequest request, HttpServletResponse response, Long who)
            throws ServiceSystemException {
        ClientService service = ServiceFactory.getInstance().getClientService();
        FavoriteUserPost favPost = new FavoriteUserPost();
        favPost.setUserId(who);
        String favPostId = request.getParameter(ID_FROM_POST_PARAM);
        Long favPostIdLong = Long.parseLong(favPostId);
        favPost.setId(favPostIdLong);
        boolean b = service.deleteFavorite(favPost);
        LOGGER.debug("Favorite deleted: "  + favPostIdLong);
        return null;
    }
}
