package by.epam.like_it.dao.mysql.util;


import by.epam.like_it.dao.mysql.util.criteriaChain.AbstractCriteriaChain;
import by.epam.like_it.dao.mysql.util.criteriaChain.ConstrictionChain;
import by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.prepared.EqPreparedChain;
import by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.simple.EqChain;
import by.epam.like_it.dao.mysql.util.criteriaChain.LimitChain;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.Limit;
import by.epam.like_it.model.criteria_to.core.constriction.Constriction;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;

import java.util.Arrays;
import java.util.List;

public class CriteriaHandler {
    public enum Mode{
        USUAL, PREPARED;
    }

    public static final String pattern = "%s";
    private static CriteriaHandler instance;

    private final AbstractCriteriaChain tail;

    private CriteriaHandler(){
        tail = ConstrictionChain.getInstance();
    }

    public static CriteriaHandler getInstance(){

        if (instance == null)
            synchronized (CriteriaHandler.class){
                if (instance == null)
                    instance = new CriteriaHandler();
            }
        return instance;
    }

    public String processCriteria(String mainPart, Criteria criteria){
        if (criteria == null){
            return mainPart;
        }
        StringBuilder builder = getNormalizedMainPart(mainPart);

        tail.doChain(builder, criteria);
        return builder.toString();
    }

    public String processSinglePart(String mainPart, Limit limit){
        if (limit == null){
            return mainPart;
        }
        StringBuilder builder = getNormalizedMainPart(mainPart);
        LimitChain.getInstance().processSingleOnePart(builder, limit);
        return builder.toString();
    }

    public String processSinglePart(String mainPart, CriteriaHandler.Mode mode, EqConstriction... eq){
        StringBuilder builder = getNormalizedMainPart(mainPart);
        List<Constriction> eqs = Arrays.asList(eq);
        switch (mode){
            case USUAL:
                ConstrictionChain.getInstance().processSingleOnePart(builder, EqChain.getInstance(), eqs);
            case PREPARED:
                ConstrictionChain.getInstance().processSingleOnePart(builder, EqPreparedChain.getInstance(), eqs);
        }
        return builder.toString();
    }

    /**
     * All CriteriaChains append "\n" criteria_to their line, so for beautifying the other one will be cropped.
     * @param mainPart
     * @return
     */

    private StringBuilder getNormalizedMainPart(String mainPart){
        String toAdd = mainPart.endsWith("\n") ? mainPart.substring(0, mainPart.length() - 1) : mainPart;
        return  new StringBuilder(toAdd);
    }
}
