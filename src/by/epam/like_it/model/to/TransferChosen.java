package by.epam.like_it.model.to;


import java.util.NavigableMap;

/**
 *
 * @param <T> - transferred bean/vo object
 */
public interface TransferChosen<T> {

    NavigableMap<Integer, T> getChosen();

    NavigableMap<Integer, T> getRemain();
}
