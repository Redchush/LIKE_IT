package by.epam.like_it.model.to.impl;

import by.epam.like_it.model.to.HistoryArrayTransfer;

import java.util.Arrays;


public class StringBinaryTransfer implements HistoryArrayTransfer<String> {

    private String[] oldChosen;
    private String[] newChosen;
    private String[] initial;

    public StringBinaryTransfer() {
        oldChosen = new String[0];
        newChosen = new String[0];
        initial = new String[0];
    }

    public StringBinaryTransfer(String[] oldChosen, String[] newChosen, String[] initial) {
        this.oldChosen = oldChosen;
        this.newChosen = newChosen;
        this.initial = initial;
    }

    @Override
    public String[] getOldChosen() {
        return oldChosen;
    }

    public void setOldChosen(String[] oldChosen) {
        this.oldChosen = oldChosen;
    }

    @Override
    public String[] getNewChosen() {
        return newChosen;
    }

    public void setNewChosen(String[] newChosen) {
        this.newChosen = newChosen;
    }

    @Override
    public String[] getInitial() {
        return initial;
    }

    public void setInitial(String[] initial) {
        this.initial = initial;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StringBinaryTransfer{");

        sb.append("oldChosen=").append(Arrays.toString(oldChosen));
        sb.append(", newChosen=").append(Arrays.toString(newChosen));
        sb.append(", initial=").append(Arrays.toString(initial));
        sb.append('}');
        return sb.toString();
    }
}
