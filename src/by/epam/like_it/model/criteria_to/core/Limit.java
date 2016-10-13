package by.epam.like_it.model.criteria_to.core;


import java.io.Serializable;

public class Limit implements Serializable{

    private Integer start;
    private Integer count;

    public Limit() {
        this.start = 0;
    }

    public Limit(Integer start, Integer count) {
        this.start = start;
        this.count = count;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Limit{");

        sb.append("start=").append(start);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
