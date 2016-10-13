package by.epam.like_it.model.vo.util;


import by.epam.like_it.common_util.AuthSecurityConstants;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.db_vo.AnswerVO;
import by.epam.like_it.model.vo.db_vo.CommentVO;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.model.vo.db_vo.UserVO;

public class EmptyVoManager {

    public static final long BANNED_MARKER = AuthSecurityConstants.ID_BANNED_VALUE;
    public static final long NOT_EXIST_MARKER = AuthSecurityConstants.ID_NOT_EXIST_VALUE;

    public static PostVO EMPTY_POST_VO;
    public static UserVO EMPTY_USER_VO;
    public static CommentVO EMPTY_COMMENT_VO;
    public static AnswerVO EMPTY_ANSWER_VO;

    static {
        EMPTY_POST_VO = new PostVO();
        Post post =new Post();
        post.setId(NOT_EXIST_MARKER);
        EMPTY_POST_VO.setPost(post);
    }

    static {
        EMPTY_USER_VO = new UserVO();
        User user = new User();
        user.setId(NOT_EXIST_MARKER);
        EMPTY_USER_VO.setUser(user);
    }

}
