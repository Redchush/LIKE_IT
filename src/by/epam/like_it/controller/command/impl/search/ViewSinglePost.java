package by.epam.like_it.controller.command.impl.search;


import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.PageNavigator;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.controller.command.util.MsgHandler;
import by.epam.like_it.exception.service.action.ServiceActionDetectableException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.ReadedPost;
import by.epam.like_it.model.criteria_to.core.Limit;
import by.epam.like_it.model.vo.db_vo.AnswerVO;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.service.*;
import by.epam.like_it.exception.service.action.ServiceEntityBannedException;
import by.epam.like_it.exception.service.ServiceException;
import by.epam.like_it.service.EntityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ViewSinglePost implements Command {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static final ServiceFactory SERVICE_FACTORY;

    static {
        SERVICE_FACTORY = ServiceFactory.getInstance();
    }

    private static ViewSinglePost instance;

    private ViewSinglePost(){}

    public static ViewSinglePost getInstance(){

        if (instance == null)
            synchronized (ViewSinglePost.class){
                if (instance == null)
                    instance = new ViewSinglePost();
            }
        return instance;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String  resultPage =  PageNavigator.getInstance().getPage(PageNavigator.Pages.POST);
        String parameter = request.getParameter(CommandConstants.ID_FROM_GET_PARAM);
        PostService postService = SERVICE_FACTORY.getPostService();
        Long postId = Long.parseLong(parameter);

        PostVO singlePost;
        try {
            singlePost = postService.findSinglePostVOWithInfo(postId);
        } catch (ServiceEntityBannedException e){
            LOGGER.error("User try to get non-existent or banned entity");
            MsgHandler.getInstance().handleEntityBanned(request, Post.class);
            return resultPage;
        } catch (ServiceException e) {
            LOGGER.error("Fail find single PostVO With Info", e);
            throw new CommandException("Fail findSinglePostVOWithInfo", e);
        }
        request.setAttribute(CommandConstants.CURRENT_POST_VO,singlePost);

        List<Post>  related = findRelatedPosts(singlePost, postService);
        request.setAttribute(CommandConstants.RELATED_POST, related);

        LinkedHashMap<Long, AnswerVO> answerVOMap = findAnswerVoMap(postId);
        request.setAttribute(CommandConstants.CURRENT_ANSWERS_LIST_ATTR, answerVOMap.values());

        LOGGER.debug("Complete post Vo : " + singlePost);
        LOGGER.debug("Complete answer Vo : " + answerVOMap.values().stream().map(AnswerVO::toString).collect(Collectors.joining("\n")));

        HttpSession session = request.getSession();
        Long idCurrentUser = (Long) session.getAttribute(AuthSecurityConstants.USER_ID_ATTR);
        String userRole = (String) session.getAttribute(AuthSecurityConstants.ROLE_ATTR);

        if (idCurrentUser != null && idCurrentUser > 0L && userRole.equals(AuthSecurityConstants.CLIENT_VALUE)) {
            findPersonalInfo(singlePost, answerVOMap, idCurrentUser);
            createReadPost(postId, idCurrentUser);
        }
        return resultPage;
    }

    private void findPersonalInfo(PostVO singlePost, LinkedHashMap<Long, AnswerVO> answerVOMap, Long idCurrentUser){
        ClientService clientService = SERVICE_FACTORY.getClientService();
        try{
            clientService.attachUserMarksToAnswers(answerVOMap, idCurrentUser);
        } catch (ServiceSystemException e) {
            LOGGER.error("Fail attachUserMarksToAnswers criteria_to " + answerVOMap + "\nby userId " + idCurrentUser + "" +
                    ".Proceed without user marks.");
        }
        try{
            clientService.attachFavoriteUserPostToPostVO(singlePost, idCurrentUser);
        } catch (ServiceSystemException e) {
            LOGGER.error("Fail attachUserMarksToAnswers criteria_to " + singlePost + "\n by userId " + idCurrentUser);
        }

    }

    private List<Post> findRelatedPosts(PostVO vo, PostService service) {
        List<Post> related = null;
        try {
            related = service.findSimilarPostsList(vo, new Limit(0, 20));
        } catch (ServiceException e) {
            LOGGER.error("Fail find findSimilarPostsList by PostVo " + vo + ". The post will be shown without " +
                    "related");
        }
        return related;
    }

    private LinkedHashMap<Long, AnswerVO> findAnswerVoMap(Long postId){
        AnswerService answerService = SERVICE_FACTORY.getAnswerService();
        try{
            return answerService.getAnswerVoMapByPostId(postId);
        } catch (ServiceSystemException e) {
            LOGGER.error("Fail find AnswersListByPostId. The post will be shown without answers");
            return new LinkedHashMap<>();
        }
    }


    private void createReadPost(Long postId, Long idCurrentUser){
        ReadedPost readedPost = new ReadedPost();
        readedPost.setPostId(postId);
        readedPost.setUserId(idCurrentUser);
        EntityService contentService = SERVICE_FACTORY.getEntityService();
        try {
            contentService.createEntity(readedPost);
        } catch (ServiceSystemException e) {
            LOGGER.error("Some system exception occured");
        } catch (ServiceActionDetectableException e) {
            LOGGER.debug("This post was readed by user yet. ");
        }
    }

}
