package by.epam.like_it.service;


import by.epam.like_it.exception.service.action.ServiceEntityBannedException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.criteria_to.core.Limit;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.model.vo.db_vo.PostVO;

import java.util.List;

public interface PostService {


    /**
     *
     * @param definition - definiton on search in persistence with components allowed by type InitialPostCriteria
     * @return ListCounterResponse<PostVO>
     * @throws ServiceSystemException - If some internal exception occured
     * @see ListCounterResponse
     */

    ListCounterResponse<PostVO> findListCounterResponse(InitialPostCriteria definition) throws ServiceSystemException;


    List<PostVO> findPostsWithInfo(InitialPostCriteria definition) throws ServiceSystemException;

    /**
     * Never return null or vo with Post banned entity or Post without id -> in this case the
     * ServiceEntityBannedException  will  be thrown. Other fields fulfilled according to corresponding values in
     * persistence
     * @param id - id of post requested
     * @return   PostVO vo.
     * @throws ServiceEntityBannedException - if entity found is banned
     * @throws ServiceSystemException - If some internal exception occured
     */
    PostVO findSinglePostVOWithInfo(Long id) throws ServiceEntityBannedException, ServiceSystemException;


    /**
     * Return posts list with size from null to limit, that contains all posts
     * in persistence that has has the same words in title as Post in PostVO
     * @param vo - vo, that contains Post with info. Arguments can't be eq null;
     * @param limit - limit constriction
     * @return List<Post>
     * @throws ValidationInfoException - If current PostVO not valid for using as example for searching
     * @throws ServiceSystemException - If some internal exception occured
     * @see Limit
     */
    List<Post> findSimilarPostsList(PostVO vo, Limit limit) throws ServiceSystemException, ValidationInfoException;

    ListCounterResponse<PostVO> findFavoriteUserPost(Long userId) throws ServiceEntityBannedException, ServiceSystemException;

    /**
     *
     * @param id - id of entity to be banned. In case of invalid id - throws ValidationInfoException
     * @return whether the post was successfully marked as banned
     * @throws ServiceSystemException - If some internal exception occured
     */
    boolean banPost(Long id) throws ServiceSystemException;

    List<Long> createPostTags(Long postId, String[] tagsId) throws ValidationInfoException, ServiceSystemException;
}
