package by.epam.like_it.model.to.impl;

import by.epam.like_it.model.to.TransferChosen;
import by.epam.like_it.model.vo.db_vo.TagVO;

import java.util.TreeMap;

public class TagChosenTransfer implements TransferChosen<TagVO> {

    private TreeMap<Integer, TagVO> chosen;
    private TreeMap<Integer, TagVO> remain;

    public TagChosenTransfer() {
        chosen = new TreeMap<>();
        remain = new TreeMap<>();
    }

    public TagChosenTransfer(TreeMap<Integer, TagVO> chosen,
                             TreeMap<Integer, TagVO> remain) {
        this.chosen = chosen;
        this.remain = remain;
    }

    @Override
    public TreeMap<Integer, TagVO> getChosen() {
        return chosen;
    }

    @Override
    public TreeMap<Integer, TagVO> getRemain() {
        return remain;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TagChosenTransfer{");
        sb.append("chosen=").append(chosen);
        sb.append(", remain=").append(remain);
        sb.append('}');
        return sb.toString();
    }
}
