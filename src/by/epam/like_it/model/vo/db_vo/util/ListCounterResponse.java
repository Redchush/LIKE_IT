package by.epam.like_it.model.vo.db_vo.util;


import java.util.List;

public class ListCounterResponse<T> {

    private final List<T> items;
    private final Long total;

    public ListCounterResponse(List<T> items, Long total) {
        this.items = items;
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public Long getTotal() {
        return total;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ListCounterResponse{");
        sb.append("items=").append(items);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
