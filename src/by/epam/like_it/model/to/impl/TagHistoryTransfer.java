package by.epam.like_it.model.to.impl;


import by.epam.like_it.model.to.BinaryTransfer;
import by.epam.like_it.model.vo.db_vo.TagVO;

import java.io.Serializable;
import java.util.ArrayList;

public class TagHistoryTransfer implements BinaryTransfer<TagVO>, Serializable {

    private ArrayList<TagVO> oldChosen;
    private ArrayList<TagVO> newChosen;
    private ArrayList<TagVO> remain;
    private ArrayList<Long> idInitial;

    private long totalChosen;
    private long totalRemain;

    public TagHistoryTransfer() {}

    public TagHistoryTransfer(ArrayList<TagVO> chosen, ArrayList<TagVO> remain) {
        this.oldChosen = chosen;
        this.remain = remain;
    }

    public ArrayList<TagVO> getOldChosen() {
        return oldChosen;
    }

    @Override
    public ArrayList<TagVO> getRemain() {
        return remain;
    }

    public void setOldChosen(ArrayList<TagVO> oldChosen) {
        this.oldChosen = oldChosen;
    }

    public void setRemain(ArrayList<TagVO> remain) {
        this.remain = remain;
    }

    public long getTotalChosen() {
        return totalChosen;
    }

    public void setTotalChosen(long totalChosen) {
        this.totalChosen = totalChosen;
    }

    public long getTotalRemain() {
        return totalRemain;
    }

    public void setTotalRemain(long totalRemain) {
        this.totalRemain = totalRemain;
    }

    public ArrayList<TagVO> getNewChosen() {
        return newChosen;
    }

    public void setNewChosen(ArrayList<TagVO> newChosen) {
        this.newChosen = newChosen;
    }

    public ArrayList<Long> getIdInitial() {
        return idInitial;
    }

    public void setIdInitial(ArrayList<Long> idInitial) {
        this.idInitial = idInitial;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TagHistoryTransfer transfer = (TagHistoryTransfer) o;

        if (totalChosen != transfer.totalChosen) {
            return false;
        }
        if (totalRemain != transfer.totalRemain) {
            return false;
        }
        if (oldChosen != null ? !oldChosen.equals(transfer.oldChosen) : transfer.oldChosen != null) {
            return false;
        }
        if (newChosen != null ? !newChosen.equals(transfer.newChosen) : transfer.newChosen != null) {
            return false;
        }
        if (remain != null ? !remain.equals(transfer.remain) : transfer.remain != null) {
            return false;
        }
        return idInitial != null ? idInitial.equals(transfer.idInitial) : transfer.idInitial == null;

    }

    @Override
    public int hashCode() {
        int result = oldChosen != null ? oldChosen.hashCode() : 0;
        result = 31 * result + (newChosen != null ? newChosen.hashCode() : 0);
        result = 31 * result + (remain != null ? remain.hashCode() : 0);
        result = 31 * result + (idInitial != null ? idInitial.hashCode() : 0);
        result = 31 * result + (int) (totalChosen ^ (totalChosen >>> 32));
        result = 31 * result + (int) (totalRemain ^ (totalRemain >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TagHistoryTransfer{");

        sb.append("oldChosen=").append(oldChosen);
        sb.append(", newChosen=").append(newChosen);
        sb.append(", remain=").append(remain);
        sb.append(", idInitial=").append(idInitial);
        sb.append(", totalChosen=").append(totalChosen);
        sb.append(", totalRemain=").append(totalRemain);
        sb.append('}');
        return sb.toString();
    }


}
