package by.epam.like_it.controller.command.impl.command_with_restriction.create.auth_opt;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.common_util.PageNavigator;
import by.epam.like_it.common_util.TimeUtil;
import by.epam.like_it.controller.command.impl.AbstractPostAuthCommand;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.exception.service.action.ServiceActionDetectableException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.service.TagService;
import by.epam.like_it.service.EntityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CreatePost extends AbstractPostAuthCommand {

    protected static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static final String TITLE_PARAM = "title";
    private static final String TAG_PARAM = "tag";
    protected final static String CONTENT_PARAM = "content";

    private static CreatePost instance;

    private CreatePost(){}

    public static CreatePost getInstance(){

        if (instance == null)
            synchronized (CreatePost.class){
                if (instance == null)
                    instance = new CreatePost();
            }
        return instance;
    }

    @Override
    public String reallyExecute(HttpServletRequest request, HttpServletResponse response, Long who)
            throws ServiceSystemException, ValidationInfoException, CommandException, ServiceActionDetectableException {
        PostVO content = getContent(request, who);
        boolean postCreatedYet = isPostCreatedYet(request, content);
        request.setAttribute(CommandConstants.ENTITY_BEAN_LOADED, content);
        if (!postCreatedYet) {
            EntityService entityService = SERVICE_FACTORY.getEntityService();
            long entityId = entityService.createRealEntity(content.getPost());
            content.getPost().setId(entityId);
        }
        TagService tagService = SERVICE_FACTORY.getTagService();
        tagService.createTagsAndPostTags(content);
        return PageNavigator.getInstance().getPage(PageNavigator.Pages.POST) + "?id=" + content.getPost().getId();
    }

    private boolean isPostCreatedYet(HttpServletRequest request, PostVO vo){
        String parameter = request.getParameter(CommandConstants.ID_FROM_POST_PARAM);
        if (parameter != null){
            long l = Long.parseLong(parameter);
            vo.getPost().setId(l);
            return true;
        }
        return false;
    }

    private PostVO getContent(HttpServletRequest request, Long who) throws CommandException {
        String[] tags = request.getParameterValues(TAG_PARAM);
        String title = request.getParameter(TITLE_PARAM);
        String content = request.getParameter(CONTENT_PARAM);

        PostVO result = new PostVO();
        Timestamp currentTimestampSt = TimeUtil.getCurrentTimestampSt();

        Post post = new Post();
        post.setUserId(who);
        post.setContent(content);
        post.setTitle(title);
        post.setCreatedDate(currentTimestampSt);
        result.setPost(post);

        ArrayList<Tag> list = new ArrayList<>();
        if (tags != null){
            for (String name : tags){
                Tag tag = new Tag();
                tag.setName(name);
                list.add(tag);
            }
        }
        result.setTags(list);
        return result;
    }
}
