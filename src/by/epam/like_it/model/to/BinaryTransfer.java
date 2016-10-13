package by.epam.like_it.model.to;


import java.util.List;

public interface BinaryTransfer<T> {

    List<T> getOldChosen();

    List<T> getRemain();

}
