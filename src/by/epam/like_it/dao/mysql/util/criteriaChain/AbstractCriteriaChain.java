package by.epam.like_it.dao.mysql.util.criteriaChain;


import by.epam.like_it.model.criteria_to.core.Criteria;

public abstract class AbstractCriteriaChain {

    protected AbstractCriteriaChain next;

    public abstract StringBuilder doChain(StringBuilder mainPart, Criteria criteria);

}
