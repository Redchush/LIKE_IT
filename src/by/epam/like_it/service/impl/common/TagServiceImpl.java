package by.epam.like_it.service.impl.common;


import by.epam.like_it.dao.FavoriteUserTagDao;
import by.epam.like_it.dao.MySqlDaoFactory;
import by.epam.like_it.dao.TagDao;
import by.epam.like_it.exception.persistence.PersistenceException;
import by.epam.like_it.exception.persistence.action.PersistenceNotUniqueException;
import by.epam.like_it.exception.persistence.system.PersistenceSystemException;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.FavoriteUserTag;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.constriction.InConstriction;
import by.epam.like_it.model.criteria_to.facade.PostTagCriteria;
import by.epam.like_it.model.to.HistoryArrayTransfer;
import by.epam.like_it.model.to.impl.TagHistoryTransfer;
import by.epam.like_it.model.to.impl.TagChosenTransfer;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.model.vo.db_vo.TagVO;
import by.epam.like_it.service.TagService;
import by.epam.like_it.model.to.TransferChosen;
import by.epam.like_it.exception.service.ServiceException;
import by.epam.like_it.service.helper.HistoryHandler;
import by.epam.like_it.service.validator.impl.content_validator.EntityValidator;

import java.util.*;
import java.util.stream.Collectors;

public class TagServiceImpl implements TagService {

    private static TagServiceImpl instance;
    private final MySqlDaoFactory FACTORY = MySqlDaoFactory.getInstance();
    private final HistoryHandler HISTORY_HANDLER = HistoryHandler.getInstance();

    private TagServiceImpl() {}

    public static TagServiceImpl getInstance() {

        if (instance == null)
            synchronized (TagServiceImpl.class) {
                if (instance == null)
                    instance = new TagServiceImpl();
            }
        return instance;
    }

    @Override
    public ListCounterResponse<TagVO> findPostTagWithInfo(PostTagCriteria definition) throws ServiceSystemException{
        TagDao dao = FACTORY.getTagDao();
        try {
            return dao.getPostTagsResponse(definition);
        } catch (PersistenceException e) {
            throw new ServiceSystemException("Can't access the tags");
        }
    }

    @Override
    public TagHistoryTransfer getUserTagTransfer(Long id, PostTagCriteria userTagDef, PostTagCriteria commonDef,
                                                 HistoryArrayTransfer<String> tagHistory) throws ServiceSystemException{


        TagHistoryTransfer result;

        if (HISTORY_HANDLER.isHistoryEmpty(tagHistory) || HISTORY_HANDLER.isHasNewTags(tagHistory)) {
            return getUserTagTransfer(id, userTagDef, commonDef);
        }
                /*process only deleted tags*/
        ArrayList<Long> init = HISTORY_HANDLER.getInitLong(tagHistory);
        Set<Long> deleted = HISTORY_HANDLER.getDeletedIds(tagHistory, init);

       if (deleted.size() > 0) {
            InConstriction<Tag, Long> userNotIn = new InConstriction<>(Tag.class, "id", deleted);
            userNotIn.setInType(InConstriction.InType.NOT_IN);
            userTagDef.putConstriction(userNotIn);
        }
        Set<Long> news = HISTORY_HANDLER.getNewIds(tagHistory);
        InConstriction<Tag, Long> commonNotIt = null;
        if (!news.isEmpty()) {
            commonNotIt = new InConstriction<>(Tag.class, "id", news);
            commonNotIt.setInType(InConstriction.InType.NOT_IN);
            commonDef.putConstriction(commonNotIt);
        }
        result = getUserTagTransfer(id, userTagDef, commonDef);
        if (!news.isEmpty()) {
            commonNotIt.setInType(InConstriction.InType.IN);
            PostTagCriteria criteria = new PostTagCriteria();
            criteria.putConstriction(commonNotIt);
            ListCounterResponse<TagVO> postTagWithInfo = findPostTagWithInfo(criteria);
            result.setNewChosen(new ArrayList<>(postTagWithInfo.getItems()));
        }

        result.setIdInitial(new ArrayList<>(init));
        return result;
    }

    /**
     *
     * @param id
     * @param userTagDef - criteria to find all favorite user tags, on which he subscribed
     * @param commonDef - criteria to find tags for user choose
     * @return TagHistoryTransfer - contains info about initial user tag (always sorted),
     *                              new chosen tags and remain user tags
     * @throws ServiceSystemException
     */
    @Override
    public TagHistoryTransfer getUserTagTransfer(Long id, PostTagCriteria userTagDef, PostTagCriteria commonDef)
            throws ServiceSystemException {

        ListCounterResponse<TagVO> userTags = findUserSubscribingWithInfo(id, userTagDef);
        ArrayList<TagVO> chosen = new ArrayList<>(userTags.getItems());

        Set<Long> collect = chosen.stream().map(s -> s.getTag().getId())
                                  .sorted(Comparator.naturalOrder())
                                  .collect(Collectors.toSet());
        InConstriction<Tag, Long> inConstriction = new InConstriction<>(Tag.class, "id", collect);
        inConstriction.setInType(InConstriction.InType.NOT_IN);
        commonDef.putConstriction(inConstriction);

        ListCounterResponse<TagVO> total =findPostTagWithInfo(commonDef);
        ArrayList<TagVO> remain = new ArrayList<>(total.getItems());

        TagHistoryTransfer transfer = new TagHistoryTransfer(chosen, remain);
        transfer.setTotalChosen(userTags.getTotal());
        transfer.setTotalRemain(total.getTotal());
        transfer.setIdInitial(new ArrayList<>(collect));
        return transfer;
    }


    /**
     * never return null -
     * @param id
     * @param criteria
     * @return ListCounterResponse, in which total - count of user's tags, items - TagVO
     * @throws ServiceSystemException
     */

    @Override
    public ListCounterResponse<TagVO> findUserSubscribingWithInfo(Long id, PostTagCriteria criteria) throws
                                                                                                   ServiceSystemException{
        TagDao dao = FACTORY.getTagDao();
        try {
            return dao.getUserTagsResponseWithInfo(id, criteria);
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Problem during finding user by id " + id);
        }
    }



    @Override
    public ListCounterResponse<Tag> findUserSubscribing(Long id, Criteria<Tag> criteria) throws
                                                                                        ServiceSystemException{
        TagDao dao = FACTORY.getTagDao();
        ListCounterResponse<Tag> userTagsResponse;
        try {
            userTagsResponse = dao.getUserTagsResponse(id, criteria);
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Problem during finding user by id " + id);
        }
        return userTagsResponse;
    }

    @Override
    public void createTagsAndPostTags(PostVO content) throws ValidationInfoException, ServiceSystemException {
        Long id = content.getPost().getId();

        List<Tag> tags = content.getTags();
        if (tags.isEmpty()){
            return;
        }
        EntityValidator instance = EntityValidator.getInstance();
        Iterator<Tag> iterator = tags.iterator();
        while (iterator.hasNext()){
            Tag next = iterator.next();
            String name = next.getName().trim();
            if (name.isEmpty()){
                iterator.remove();
            }
        }
        if (tags.isEmpty()){
            return;
        }
        for (Tag tag : tags) {
            instance.isValidForCreate(tag);
        }
        TagDao dao = FACTORY.getTagDao();
        try {
            dao.createTagAndPostTag(tags, id);
        } catch (PersistenceSystemException e) {
            throw new ServiceSystemException("Problem during createTagsAndPostTags", e);
        } catch (PersistenceNotUniqueException e) {
            throw new ServiceSystemException("Problem during createTagsAndPostTags. Dao method not maintain integrity",
                    e);
        }

    }

    @Override
    public List<Integer> newTagIndexes(String[] tagsIndexes, NavigableMap<Integer, TagVO> prevChosen){

        List<Integer> currentIndexes = Arrays.stream(tagsIndexes).map(Integer::parseInt)
                                             .collect(Collectors.toList());
        List<Integer> prevIndexes = new LinkedList<>(prevChosen.keySet());
        currentIndexes.removeAll(prevIndexes);
        return currentIndexes;
    }

    @Override
    public void changeUserSubscribing(Long who, HistoryArrayTransfer<String> transfer) throws ServiceSystemException {
        Set<Long> deletedIds = HISTORY_HANDLER.getDeletedIds(transfer);
        FavoriteUserTagDao favoriteUserTagDao = FACTORY.getFavoriteUserTagDao();
        if (!deletedIds.isEmpty()){
            try {
                favoriteUserTagDao.delete(who, deletedIds);
            } catch (PersistenceSystemException e) {
                throw new ServiceSystemException("Some internal error during executing changeUserSubscribing while " +
                        "delete",e);
            }
        }
        Set<Long> newIds = HISTORY_HANDLER.getNewIds(transfer);
        if (!newIds.isEmpty()){
            List<FavoriteUserTag> tags = new ArrayList<>();
            for (Long newId: newIds){
                tags.add(new FavoriteUserTag(who, newId));
            }
            try {
                favoriteUserTagDao.create(tags);
            } catch (PersistenceNotUniqueException e) {
                throw new ServiceSystemException("User has this tag yet", e);
            } catch (PersistenceSystemException e) {
                throw new ServiceSystemException("Some internal error during executing changeUserSubscribing while " +
                        "create", e);
            }
        }

    }


    /**
     * Refactor common list of TagVO : split it in two maps that contained TagVO objects and index from list.
     * So they saved initial order of TagVO and can be safely moved from one map criteria_to other map not risking loose
     * predefined order.
     * @param list
     * @param chosenIndex
     * @return TransferChosen<TagVO>, that contains tags maps of chosen and not chosen tags
     * @throws ServiceException
     */

    @Override
    public TransferChosen<TagVO> splitForChosen(List<TagVO> list, Integer... chosenIndex) throws ServiceSystemException{
        if (list == null || chosenIndex == null){
            return new TagChosenTransfer();
        }
        Map<Integer, TagVO>
                remain = list.stream().collect(Collectors.toMap(x -> (Integer) list.indexOf(x), s -> s));
        if (chosenIndex.length == 0){
            return new TagChosenTransfer(new TreeMap<>(), new TreeMap<>(remain));
        }
        TreeMap<Integer, TagVO> chosen = new TreeMap<>();

        for(Integer index : chosenIndex){
            TagVO tagVO = remain.remove(index);
            chosen.put(index, tagVO);
        }
        return new TagChosenTransfer(chosen, new TreeMap<>(remain));
    }

}
