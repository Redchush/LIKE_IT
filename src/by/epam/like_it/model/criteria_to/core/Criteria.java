package by.epam.like_it.model.criteria_to.core;


import by.epam.like_it.model.criteria_to.core.constriction.Constriction;
import by.epam.like_it.model.criteria_to.core.constriction.ConstrictionType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Criteria<T> implements Serializable {

    protected Integer perPage;
    protected Order order;
    protected Limit limit;
    protected Map<ConstrictionType, List<Constriction>> constrictions;

    public Criteria() {
        constrictions = new HashMap<>();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order<T> order) {
        this.order = order;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public Map<ConstrictionType, List<Constriction>> getConstrictions() {
        return constrictions;
    }

    public void setConstrictions(
            Map<ConstrictionType, List<Constriction>> constrictions) {
        this.constrictions = constrictions;
    }

    public boolean putConstriction(Constriction<T> constriction){
        return putOtherConstriction(constriction);
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    protected boolean putOtherConstriction(Constriction constriction){
        boolean result = false;
        ConstrictionType type = constriction.getType();
        List<Constriction> constrictionList = this.constrictions.get(type);
        if (constrictionList != null){
            if (!constrictionList.contains(constriction)){
                constrictionList.add(constriction);
                result = true;
            }
        } else {
            constrictionList = new ArrayList<>();
            constrictionList.add(constriction);
            constrictions.put(type, constrictionList);
            result = true;
        }
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Criteria{");
        sb.append("order=").append(order);
        sb.append(", limit=").append(limit);
        sb.append(", constrictions=").append(constrictions);
        sb.append('}');
        return sb.toString();
    }
}
