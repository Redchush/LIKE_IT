package by.epam.like_it.controller.command.impl.search;


import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.exception.service.ServiceException;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;
import by.epam.like_it.model.criteria_to.facade.InitialPostCriteria;
import by.epam.like_it.model.to.TransferChosen;
import by.epam.like_it.model.vo.db_vo.TagVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class SearchPostByAddConstriction extends SearchPost{

    private static SearchPostByAddConstriction instance;

    private SearchPostByAddConstriction(){}

    public static SearchPostByAddConstriction getInstance(){

        if (instance == null)
            synchronized (SearchPostByAddConstriction.class){
                if (instance == null)
                    instance = new SearchPostByAddConstriction();
            }
        return instance;
    }

    @Override
    protected boolean refactorCriteria(HttpServletRequest request, InitialPostCriteria criteria)
            throws CommandException {

        HttpSession session = request.getSession(true);
        setDefaultOrderAttributes(session);
        criteria.setOrderAsDefaultOrder();
        criteria.setConstrictionsAsDefaultConstrictions();

        String[] tagsIDs = request.getParameterValues(CommandConstants.ID_TAG);
        String[] tagsIndexes = request.getParameterValues(CommandConstants.ENTITY_INDEX_PARAM);

        if (tagsIDs== null || tagsIndexes == null || tagsIDs.length == 0 || tagsIndexes.length == 0){
            setDefaultTagAttributes(session);
            return true;
        }

        ArrayList<TagVO> tags = (ArrayList<TagVO>) session.getAttribute(CommandConstants.ALL_TAGS_LIST);
        List<String> tagsIDsList = Arrays.asList(tagsIDs);
        Set<String> tagSet = new HashSet<>(tagsIDsList);
        LOGGER.debug("Chosen ids " + tagSet);
        LOGGER.debug("Its indexes " + Arrays.toString(tagsIndexes));

        EqConstriction<Tag, String> tagConstriction = new EqConstriction<>(Tag.class, "id", tagSet);
        boolean b = criteria.putTagConstriction(tagConstriction);
        if (!b){
            return false;
        }
        Integer[] tagsIntIndexes = new Integer[tagsIndexes.length];
        for (int i = 0; i < tagsIndexes.length; i++) {
            tagsIntIndexes[i] = Integer.parseInt(tagsIndexes[i]);
        }
        TransferChosen<TagVO> transfer;
        try {
            transfer = TAG_SERVICE.splitForChosen(tags, tagsIntIndexes);
        } catch (ServiceException e) {
            throw new CommandException();
        }
        session.setAttribute(CommandConstants.CHOSEN_TAGS_MAP, new TreeMap<>(transfer.getChosen()));
        session.setAttribute(CommandConstants.POSSIBLE_TAGS_MAP, new TreeMap<>(transfer.getRemain()));
        return true;
    }




}
