package by.epam.like_it.dao.mysql.util.criteriaChain;


import by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.AbstractConstrictionSubChain;
import by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.simple.EqChain;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.constriction.Constriction;
import by.epam.like_it.model.criteria_to.core.constriction.ConstrictionType;

import java.util.List;
import java.util.Map;

public class ConstrictionChain extends AbstractCriteriaChain{

    private static final String PATTERN = " WHERE ";
    private static final String GROUP_BY = "GROUP BY";
    private static final String ORDER_BY = "ORDER BY";
    private static final String LIMIT = "LIMIT";

    private static ConstrictionChain instance;
    private final AbstractConstrictionSubChain subChain;

    private ConstrictionChain(){
        next = OrderChain.getInstance();
        subChain = EqChain.getInstance();
    }

    public static ConstrictionChain getInstance(){

        if (instance == null)
            synchronized (LimitChain.class){
                if (instance == null)
                    instance = new ConstrictionChain();
            }
        return instance;
    }

    @Override
    public StringBuilder doChain(StringBuilder mainPart, Criteria criteria) {
        Map<ConstrictionType, List<Constriction>> constrictions = criteria.getConstrictions();
        processOnePart(mainPart, constrictions);
        return next.doChain(mainPart, criteria);
    }

    public StringBuilder processOnePart(StringBuilder mainPart, Map<ConstrictionType, List<Constriction>> constrictions) {
        if (constrictions != null && !constrictions.isEmpty()) {
            StringBuilder wherePart = new StringBuilder("");
            createWherePart(mainPart, wherePart);

            this.subChain.doSubChain(wherePart, constrictions, false);
            if (wherePart.length() > 0){
                wherePart.append("\n");
            }
            adjustSubPart(mainPart, wherePart);
        }
        return mainPart;
    }

    public StringBuilder processSingleOnePart(StringBuilder mainPart, AbstractConstrictionSubChain concreteSubChain,
                                              List<Constriction> constrictions){
        if (constrictions != null && !constrictions.isEmpty()) {
            StringBuilder wherePart = new StringBuilder("");
            createWherePart(mainPart, wherePart);

            concreteSubChain.processSingleOnePart(wherePart, constrictions, false);
            if (wherePart.length() > 0){
                wherePart.append("\n");
            }
            adjustSubPart(mainPart, wherePart);
        }
        return mainPart;
    }



    private void adjustSubPart(StringBuilder mainPart, StringBuilder subPart){
        int i = mainPart.lastIndexOf(GROUP_BY);
        if (i == -1){
            int hasOrder = mainPart.lastIndexOf(ORDER_BY);
            if (hasOrder != -1){
                mainPart.insert(hasOrder, subPart);
                return;
            } else {
                int hasLimit = mainPart.lastIndexOf(LIMIT);
                if (hasLimit != -1){
                    mainPart.insert(hasLimit, subPart);
                    return;
                }
            }
            mainPart.append(subPart);
        } else {
            mainPart.insert(i, subPart);
        }

    }

    private void createWherePart(StringBuilder mainPart, StringBuilder subPart){
        int i = mainPart.lastIndexOf("WHERE");
        if (i == -1){
            subPart.append(PATTERN);
        } else {
            subPart.append(" AND ");
        }
    }
}
