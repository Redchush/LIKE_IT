package by.epam.like_it.service.impl;

import by.epam.like_it.dao.mysql.util.query.QueryMaker;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.criteria_to.core.Limit;
import by.epam.like_it.model.criteria_to.facade.PostTagCriteria;
import by.epam.like_it.model.to.impl.StringBinaryTransfer;
import by.epam.like_it.model.to.impl.TagHistoryTransfer;
import by.epam.like_it.model.vo.db_vo.util.ListCounterResponse;
import by.epam.like_it.model.vo.db_vo.TagVO;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.TagService;
import by.epam.like_it.model.to.TransferChosen;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class TagServiceImplTest {
    @Test
    public void findPostTagWithInfo() throws Exception {

    }

    @Test
    public void getUserSubscribingWithInfo() throws Exception {

    }

    @Test
    public void getUserSubscribing() throws Exception {

    }


    public static TagService service;
    public static List<TagVO> tagVOs;
    public static List<TagVO> chosenVOs;
    public static List<TagVO> possibleVOs;

    public static Integer[] chosenInd;
    public static Integer[] remainInd;

    @BeforeClass
    public static void logIn(){
        service = ServiceFactory.getInstance().getTagService();
        TagVO tagVO0 = new TagVO(new Tag(1L, "willChosen"));
        TagVO tagVO1 = new TagVO(new Tag(2L, "willChosen"));
        TagVO tagVO2 = new TagVO(new Tag(3L, "willChosen"));
        TagVO tagVO3 = new TagVO(new Tag(4L, "willNOTChosen"));
        TagVO tagVO4 = new TagVO(new Tag(5L, "willNOTChosen"));
        TagVO tagVO5 = new TagVO(new Tag(6L, "willNOTChosen"));
        tagVOs = Arrays.asList(tagVO0, tagVO1, tagVO2, tagVO3, tagVO4, tagVO5);
        chosenVOs = Arrays.asList(tagVO0, tagVO1, tagVO2);
        possibleVOs = Arrays.asList(tagVO3, tagVO4, tagVO5);

        chosenInd = new Integer[]{0 ,1, 2};
        remainInd = new Integer[]{3 ,4, 5};
    }

    @Test
    public void newTagIndexes() throws Exception {
        Map<Integer, TagVO> chosenMap =
                chosenVOs.stream().collect(Collectors.toMap(x -> (Integer) chosenVOs.indexOf(x), s -> s));

        String[] allIndexes = {"0", "1", "2", "3", "4"};
        List<Integer> actual = service.newTagIndexes(allIndexes, new TreeMap<>(chosenMap));
        List<Integer> expected = Arrays.asList(3, 4);
        assertEquals(expected, actual);
        System.out.println(actual);
    }

    @Test
    public void splitForChosen() throws Exception {
        TransferChosen<TagVO> tagVOTransferChosen = service.splitForChosen(tagVOs, chosenInd);

        NavigableMap<Integer, TagVO> chosen = tagVOTransferChosen.getChosen();
        Set<Integer> testChosen = chosen.keySet();
        String expected = "[0, 1, 2]";
        assertEquals("ChosenTest ", expected, testChosen.toString());

        NavigableMap<Integer, TagVO> remain = tagVOTransferChosen.getRemain();
        Set<Integer> testReamain = remain.keySet();

        assertEquals("Remain test", Arrays.toString(remainInd), testReamain.toString());

        System.out.println(tagVOTransferChosen);
    }

    @Test
    public void findTagWithInfo() throws Exception {

    }


    /**
     * database 7.1
     * @throws Exception
     */
    @Test
    public void findAllTags() throws Exception {
        PostTagCriteria tagCriteria = new PostTagCriteria();
        tagCriteria.setLimit( new Limit(0, 20));
        System.out.println(tagCriteria);

        ListCounterResponse<TagVO> allTags = service.findPostTagWithInfo(tagCriteria);

        List<TagVO> items = allTags.getItems();
        Long total = allTags.getTotal();
        System.out.println(allTags);

        assertEquals(20, items.size());
        Long countPostTag = items.get(0).getInfo().getCountPostTag();
        assertEquals(4L, (long) countPostTag);
    }

    @Test
    public void getUserTagTransfer() throws Exception {
        Long userIdTested = 1L;

        PostTagCriteria tagCriteria = new PostTagCriteria();
        tagCriteria.setLimit( new Limit(0, 20));

        TagHistoryTransfer userTagTransfer = service.getUserTagTransfer(1L, tagCriteria, tagCriteria);
        System.out.println(userTagTransfer);

        ArrayList<TagVO> chosen = userTagTransfer.getOldChosen();
        ArrayList<TagVO> remain = userTagTransfer.getRemain();
        ArrayList<TagVO> remainCopy = new ArrayList<>(remain);
        ArrayList<Long> idInitial = userTagTransfer.getIdInitial();
        remain.removeAll(chosen);

        assertEquals(remain, remainCopy);
        List<Long> testId = chosen.stream().map(s -> s.getTag().getId()).collect(Collectors.toList());
        testId.sort(Comparator.naturalOrder());
        assertEquals(testId, idInitial);
    }

    @Test
    public void getUserFullTagTransfer() throws Exception {
        TagHistoryTransfer initHistory = service.getUserTagTransfer(1L, getEmptyCriteria(), getEmptyCriteria());
        ArrayList<TagVO> oldChosen = initHistory.getOldChosen();
        ArrayList<TagVO> remain = initHistory.getRemain();
        System.out.println("_____________");

        StringBinaryTransfer transfer = trasform(initHistory);
        Long tagIdTestedAsAdded =remain.get(0).getTag().getId();
        String[] testedNewAdded = new String[] {tagIdTestedAsAdded.toString()};
        transfer.setNewChosen(testedNewAdded);
        /*user get one tag from possible list*/
        TagHistoryTransfer userTagTransfer2 = service.getUserTagTransfer(1L, getEmptyCriteria(), getEmptyCriteria(), transfer);
        System.out.println(userTagTransfer2);
        TagVO tagVO = userTagTransfer2.getNewChosen().get(0);

        assertEquals((long) tagVO.getTag().getId(), (long) tagIdTestedAsAdded);
        assertEquals(oldChosen, userTagTransfer2.getOldChosen());
        ArrayList<TagVO> remain1 = userTagTransfer2.getRemain();
        boolean contains = true;
        long count = remain1.stream().filter(s -> s.getTag().getId().equals(tagIdTestedAsAdded)).count();
        assertEquals(0L, count);

        System.out.println("______________");
             /*both delete and new*/
        String[] old = transfer.getOldChosen();
        Long idDeleted = Long.parseLong(old[0]);
        System.out.println("WE delete id " + idDeleted);
        String[] deleteFirst = Arrays.copyOfRange(old, 1, old.length);
        transfer.setOldChosen(deleteFirst);
        TagHistoryTransfer userTagTransfer3 = service.getUserTagTransfer(1L, getEmptyCriteria(), getEmptyCriteria(), transfer);
        System.out.println(userTagTransfer3);

        ArrayList<Long> initial3 = userTagTransfer3.getIdInitial();
        boolean contains1 = initial3.contains(idDeleted);

        assertTrue(contains1);
        boolean ifOldContainsDeleted = checkOldChosenNotContains(userTagTransfer3, idDeleted);
        assertTrue(ifOldContainsDeleted);

       /*only delete*/
        System.out.println("____________");
        StringBinaryTransfer withDeleted = trasform(initHistory);
        withDeleted.setOldChosen(deleteFirst);
        TagHistoryTransfer userTagTransfer4 = service.getUserTagTransfer(1L, getEmptyCriteria(), getEmptyCriteria(), withDeleted);

        boolean ifOldContainsDeleted_2 = checkOldChosenNotContains(userTagTransfer3, idDeleted);
        assertTrue(ifOldContainsDeleted_2);

        System.out.println("Transfer without " + idDeleted + ": " + userTagTransfer4);

    }

    private PostTagCriteria getEmptyCriteria(){
        PostTagCriteria tagCriteria = new PostTagCriteria();
        tagCriteria.setLimit( new Limit(0, 20));
        return tagCriteria;
    }

    private boolean checkOldChosenNotContains(TagHistoryTransfer transfer, Long idDeleted){
        long count3 =
                transfer.getOldChosen().stream().filter(s -> s.getTag().getId().equals(idDeleted)).count();
        return count3 == 0;
    }

    private StringBinaryTransfer trasform(TagHistoryTransfer transfer){
        ArrayList<TagVO> oldChosen = transfer.getOldChosen();
        ArrayList<Long> idInitial = transfer.getIdInitial();

        StringBinaryTransfer result = new StringBinaryTransfer();

        String[] initial = getArrayFromList(idInitial);
        result.setInitial(initial);

        String[] old = getArrayIDFromList(oldChosen);
        result.setOldChosen(old);
        return result;
    }

    private String[] getArrayFromList(List<Long> list){
        String[] result = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).toString();
        }
        return result;
    }

    private String[] getArrayIDFromList(List<TagVO> list){
        String[] result = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).getTag().getId().toString();
        }
        return result;
    }

}