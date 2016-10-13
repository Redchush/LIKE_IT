package by.epam.like_it.service;


import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.facade.PostTagCriteria;
import by.epam.like_it.model.to.HistoryArrayTransfer;
import by.epam.like_it.model.to.impl.TagHistoryTransfer;
import by.epam.like_it.model.to.TransferChosen;
import by.epam.like_it.model.vo.db_vo.PostVO;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.model.vo.db_vo.TagVO;



import java.util.List;
import java.util.NavigableMap;

public interface TagService {

    ListCounterResponse<TagVO> findPostTagWithInfo(PostTagCriteria definition) throws ServiceSystemException;

    TagHistoryTransfer getUserTagTransfer(Long id, PostTagCriteria userTagDef, PostTagCriteria commonDef,
                                          HistoryArrayTransfer<String> tags) throws ServiceSystemException;

    TagHistoryTransfer getUserTagTransfer(Long id, PostTagCriteria userTagDef, PostTagCriteria commonDef)
            throws ServiceSystemException;

    ListCounterResponse<TagVO> findUserSubscribingWithInfo(Long id, PostTagCriteria criteria) throws
                                                                                        ServiceSystemException;

    List<Integer> newTagIndexes(String[] tagsIndexes, NavigableMap<Integer, TagVO> prevChosen);

    void changeUserSubscribing(Long who, HistoryArrayTransfer<String> transfer) throws ServiceSystemException;

    TransferChosen<TagVO> splitForChosen(List<TagVO> list, Integer[] chosenIndex) throws ServiceSystemException;

    ListCounterResponse<Tag> findUserSubscribing(Long id, Criteria<Tag> criteria) throws ServiceSystemException;

    void createTagsAndPostTags(PostVO content) throws ValidationInfoException, ServiceSystemException;
}
