package by.epam.like_it.model.to;


public interface HistoryArrayTransfer<T> {

    T[] getOldChosen();
    T[] getNewChosen();
    T[] getInitial();
}
