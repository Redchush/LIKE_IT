package by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain;


import by.epam.like_it.model.criteria_to.core.constriction.Constriction;
import by.epam.like_it.model.criteria_to.core.constriction.ConstrictionType;

import java.util.List;
import java.util.Map;

public abstract class AbstractConstrictionSubChain {

    protected AbstractConstrictionSubChain next;

    public abstract StringBuilder doSubChain(StringBuilder subPart,
                                             Map<ConstrictionType,
                                             List<Constriction>> constrictions, boolean needAnd);

    public abstract StringBuilder processSingleOnePart(StringBuilder subPart,List<Constriction> constrictions,
                                                       boolean needAnd);

}
