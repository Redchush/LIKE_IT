package by.epam.like_it.service.helper;


import by.epam.like_it.model.to.HistoryArrayTransfer;
import by.epam.like_it.model.to.impl.TagHistoryTransfer;

import java.util.*;
import java.util.stream.Collectors;

public class HistoryHandler {

    private static HistoryHandler instance;

    private HistoryHandler(){}

    public static HistoryHandler getInstance(){

        if (instance == null)
            synchronized (HistoryHandler.class){
                if (instance == null)
                    instance = new HistoryHandler();
            }
        return instance;
    }

    public boolean isHistoryEmpty(HistoryArrayTransfer transfer){
        return transfer.getNewChosen().length ==0
                && transfer.getOldChosen().length == 0
                && transfer.getInitial().length == 0;
    }

    public boolean isHasNewTags(HistoryArrayTransfer transfer){
        Object[] oldChosen = transfer.getOldChosen();
        Object[] initial = transfer.getInitial();
        TagHistoryTransfer result;
        Arrays.sort(oldChosen);
        Arrays.sort(initial);
        boolean equals = Arrays.equals(oldChosen, initial);
        return transfer.getNewChosen().length ==0 && equals;
    }

    public Set<Long> getDeletedIds(HistoryArrayTransfer<String> transfer, List<Long> initialIds){
        String[] newChosen = transfer.getNewChosen();
        List<Long> old = Arrays.stream(transfer.getOldChosen())
                               .map(Long::parseLong)
                               .collect(Collectors.toList());
        Set<Long> deleted = new HashSet<>(initialIds);
        deleted.removeAll(old);
        return deleted;
    }

    public Set<Long> getDeletedIds(HistoryArrayTransfer<String> transfer){
        ArrayList<Long> initLong = getInitLong(transfer);
        return getDeletedIds(transfer, initLong);
    }

    public ArrayList<Long> getInitLong(HistoryArrayTransfer<String> transfer){
        List<Long> collect = Arrays.stream(transfer.getInitial()).map(Long::parseLong).collect(Collectors.toList());
        return new ArrayList<>(collect);
    }

    public Set<Long> getNewIds(HistoryArrayTransfer<String> transfer){
        return Arrays.stream(transfer.getNewChosen()).map(Long::parseLong).collect(Collectors.toSet());
    }

    public void normalise(HistoryArrayTransfer<String> transfer){
        String[] newChosen = transfer.getNewChosen();
        String[] initial = transfer.getInitial();
        String[] oldChosen = transfer.getOldChosen();
    }



}
