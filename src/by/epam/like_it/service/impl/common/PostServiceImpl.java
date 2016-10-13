package by.epam.like_it.service.impl.common;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.PostDao;
import by.epam.like_it.dao.PostTagDao;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.PostTag;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.Limit;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;
import by.epam.like_it.model.criteria_to.core.constriction.LikeConstriction;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;
import by.epam.like_it.model.vo.util.EmptyVoManager;
import by.epam.like_it.service.PostService;
import by.epam.like_it.exception.service.action.ServiceEntityBannedException;
import by.epam.like_it.exception.service.validation.info.ValidatorRequiredFieldIsNullException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class PostServiceImpl implements PostService {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static MySqlDaoFactory FACTORY = MySqlDaoFactory.getInstance();
    private static PostServiceImpl instance;

    private PostServiceImpl(){}

    public static PostServiceImpl getInstance(){

        if (instance == null)
            synchronized (PostServiceImpl.class){
                if (instance == null)
                    instance = new PostServiceImpl();
            }
        return instance;
    }

    @Override
    public ListCounterResponse<PostVO> findListCounterResponse(InitialPostCriteria definition) throws ServiceSystemException {
        PostDao dao = FACTORY.getPostDao();
        ListCounterResponse<PostVO> response = null;
        try {
            response = dao.getPostsVoListCounter(definition);
            response.getItems().stream().map(PostVO::getPost).forEach(Post::initAllDefault);
        } catch (PersistenceException e) {
            throw new ServiceSystemException("Can't access posts with count files");
        }
        return response;
    }

    @Override
    public List<PostVO> findPostsWithInfo(InitialPostCriteria definition) throws ServiceSystemException {
        PostDao dao = FACTORY.getPostDao();
        List<PostVO> response;
        try {
            response = dao.getPostsVo(definition);
            response.stream().map(PostVO::getPost).forEach(Post::initAllDefault);
        } catch (PersistenceException e) {
            throw new ServiceSystemException("Can't access posts");
        }
        return response;
    }


    @Override
    public PostVO findSinglePostVOWithInfo(Long id) throws ServiceEntityBannedException, ServiceSystemException {
        PostDao dao = FACTORY.getPostDao();
        InitialPostCriteria criteria = new InitialPostCriteria();
        PostVO vo;
        try{
            vo = dao.getSinglePostVo(id);
            LOGGER.debug("Post found: " + vo);
            if (vo.equals(EmptyVoManager.EMPTY_POST_VO) || vo.getPost() == null || vo.getPost().getBanned() || vo
                    .getPost().getId() == null){
                throw new ServiceEntityBannedException();
            }
            vo.getPost().initAllDefault();
            User author = vo.getAuthor();
            if (author.getBanned()){
                author.setId(-2L);
            } else {
                author.initAllDefault();
            }
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Can't find post");
        }
        return vo;
    }


    @Override
    public List<Post> findSimilarPostsList(PostVO vo, Limit limit) throws ServiceSystemException, ValidationInfoException {
        String title;
        if (vo == null || limit == null || vo.getPost() == null || (title = vo.getPost().getTitle()) == null){
            throw new ValidationInfoException(new InvalidInfo("Post"));
        }

        Set<String> wordsInTitle = Arrays.stream(title.split(" ")).filter(s -> s.length() > 4)
                                         .collect(Collectors.toSet());

        Criteria<Post> postCriteria = new Criteria<>();
        LikeConstriction<Post> likeConstriction = new LikeConstriction<>(Post.class, "title", wordsInTitle);
        EqConstriction<Post, Boolean> eqConstriction = new EqConstriction<Post, Boolean>(Post.class, "banned", Collections
                .singleton(false));
        postCriteria.putConstriction(likeConstriction);
        postCriteria.setLimit(limit);
        PostDao dao = FACTORY.getPostDao();
        List<Post> result;
        try{
            result = dao.getShortPostByCriteria(postCriteria);
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Can't find post");
        }
        return result;
    }


    /**
     * @param userId
     * @return   PostVO vo. Never return null or banned entity -> in this case the ServiceEntityBannedException will
     * be thrown
     * @throws ServiceEntityBannedException
     * @throws ServiceSystemException
     */
    @Override
    public ListCounterResponse<PostVO> findFavoriteUserPost(Long userId) throws ServiceEntityBannedException, ServiceSystemException {
        PostDao dao = FACTORY.getPostDao();
        ListCounterResponse<PostVO> response = null;
        try {
            response = dao.getFavoritePostVoListCounter(userId);
            response.getItems().stream().map(PostVO::getPost).forEach(Post::initAllDefault);
        } catch (PersistenceException e) {
            throw new ServiceSystemException("Can't access posts with count files");
        }
        return response;
    }


    @Override
    public boolean banPost(Long id) throws ServiceSystemException{
        PostDao dao = FACTORY.getPostDao();
        boolean delete = false;
        try {
            delete = dao.delete(id);
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Can't deleteByBanIfPossible post");
        }
        return delete;
    }

    @Override
    public List<Long> createPostTags(Long postId, String[] tagsId) throws
                                                                   ValidationInfoException, ServiceSystemException {
        if (tagsId.length == 0){
            return Collections.emptyList();
        }
        List<PostTag> postTags = new ArrayList<>();
        for (int i = 0; i < tagsId.length; i++) {
            Long l;
            try {
                l = Long.parseLong(tagsId[i]);
            } catch (NumberFormatException e){
                throw new ServiceSystemException("Invalid tag data");
            }
            PostTag postTag = new PostTag(postId, l);
            postTags.add(postTag);
        }
        PostTagDao dao = FACTORY.getPostTagDao();
        List<Long> longs;
        try {
            longs = dao.create(postTags);
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException();
        } catch (PersistenceNotUniqueException e) {
           LOGGER.error("some tags not created");
           throw new ServiceSystemException();
        }
        return longs;
    }
}

//        Set<String> collect = vo.getTags().stream().map((tag) -> tag.getId().toString()).collect(BeanCollectors.toSet());
//        SimilarPostCriteria criteria = new SimilarPostCriteria();
//        EqConstriction<Tag> eqConstriction = new EqConstriction<Tag>(Tag.class, "id", collect);