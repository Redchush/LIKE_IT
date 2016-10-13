package by.epam.like_it.controller.command.util;


import by.epam.like_it.model.to.HistoryArrayTransfer;
import by.epam.like_it.model.to.impl.StringBinaryTransfer;
import by.epam.like_it.model.to.impl.TagHistoryTransfer;
import by.epam.like_it.model.vo.db_vo.TagVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static by.epam.like_it.controller.command.util.CommandConstants.PREV_IDS;

public class TagResponsible {

    private static final String DELIMITER = ",";
    private static TagResponsible instance;

    private TagResponsible(){}

    public static TagResponsible getInstance(){

        if (instance == null)
            synchronized (TagResponsible.class){
                if (instance == null)
                    instance = new TagResponsible();
            }
        return instance;
    }

    public void savePrevIds(HttpServletRequest request, List<TagVO> currentTags){
        String prevIDs = currentTags.stream().map(s -> s.getTag().getId().toString())
                                        .collect(Collectors.joining(DELIMITER));
        request.setAttribute(PREV_IDS, prevIDs);
    }

    public void savePrevIds(HttpServletRequest request, TagHistoryTransfer transfer){
        String prevIDs = transfer.getIdInitial().stream()
                                 .map(Object::toString)
                                 .collect(Collectors.joining(DELIMITER));
        request.setAttribute(PREV_IDS, prevIDs);
    }

    public List<Integer> extractPrevIds(HttpServletRequest request){
        String prevIDs = request.getParameter(CommandConstants.PREV_IDS);
        if (prevIDs == null){
            prevIDs = (String) request.getAttribute(CommandConstants.PREV_IDS);
        }
        String[] split;
        if (prevIDs == null || (split = prevIDs.split(DELIMITER)).length == 0){
            return Collections.emptyList();
        }
        return Arrays.stream(split).map(Integer::parseInt).collect(Collectors.toList());

    }
    public String[] extractPrevIdsAsArray(HttpServletRequest request){
        String prevIDs = request.getParameter(CommandConstants.PREV_IDS);
        if (prevIDs == null){
            prevIDs = (String) request.getAttribute(CommandConstants.PREV_IDS);
        }
        String[] split;
        if (prevIDs == null || (split = prevIDs.split(DELIMITER)).length == 0){
            split = new String[0];
        }
        return split;
    }

    public HistoryArrayTransfer<String> getTransfer(HttpServletRequest request){
        String[] initial = extractPrevIdsAsArray(request);
        String[] newTags = request.getParameterValues(CommandConstants.NEW_TAG_ID_PARAM);
        String[] oldTags = request.getParameterValues(CommandConstants.OLD_TAG_ID_PARAM);
        newTags = newTags !=null ? newTags : new String[0];
        oldTags = oldTags !=null ? oldTags : new String[0];
        Arrays.sort(newTags);
        Arrays.sort(oldTags);
        return new StringBinaryTransfer(oldTags, newTags, initial);
    }

    public void toggleOrder(HttpServletRequest request, String prevDirection){
        switch (prevDirection){
            case  CommandConstants.SORT_DIRECTION_UP:
                request.setAttribute(CommandConstants.SORT_DIRECTION, CommandConstants.SORT_DIRECTION_DOWN);
                break;
            case CommandConstants.SORT_DIRECTION_DOWN :
                request.setAttribute(CommandConstants.SORT_DIRECTION, CommandConstants.SORT_DIRECTION_UP);
                break;
        }

    }

}
