package by.epam.like_it.controller.command.impl.command_with_restriction.load;


import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.PageNavigator;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.controller.command.util.MsgHandler;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.exception.service.ServiceException;
import by.epam.like_it.exception.service.action.ServiceEntityBannedException;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.model.vo.db_vo.UserVO;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.service.PostService;
import by.epam.like_it.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoadFavoritePost extends AbstractLoadEntityCommand {

    private static final Logger LOGGER = LogManager.getLogger(ClassName.getClassName());


    private static LoadFavoritePost instance;

    private LoadFavoritePost(){}

    public static LoadFavoritePost getInstance(){

        if (instance == null)
            synchronized (LoadFavoritePost.class){
                if (instance == null)
                    instance = new LoadFavoritePost();
            }
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        Long currentUserId = (Long) session.getAttribute(AuthSecurityConstants.USER_ID_ATTR);
        String resultPage = getPrevQuery(request);
        PostService postService = SERVICE_FACTORY.getPostService();
        UserService userService = SERVICE_FACTORY.getUserService();
        ListCounterResponse<PostVO> posts = null;
        UserVO currentUserVo;
        try {
            posts = postService.findFavoriteUserPost(currentUserId);
            currentUserVo = userService.getUserProfile(currentUserId);
        } catch (ServiceException e) {
            LOGGER.error("Fail find single PostVO With Info", e);
            throw new CommandException("Fail findSinglePostVOWithInfo", e);
        }
        request.setAttribute(CommandConstants.CURRENT_POSTS_ATTR, posts.getItems());  //post will be shown on page
        request.setAttribute(CommandConstants.TOTAL_POSTS_COUNT, posts.getTotal());  //count of posts found
        request.setAttribute(CommandConstants.USER_VO, currentUserVo);
        return resultPage;
    }

}
