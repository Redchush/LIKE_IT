package by.epam.like_it.dao.mysql.util.criteriaChain;


import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.Limit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LimitChain extends AbstractCriteriaChain {

    private static final String PATTERN = "\nLIMIT %s, %s ";
    private static final Pattern HAS_LIMIT_PATTERN = Pattern.compile("[Ll][iI][mM][iI][tT]");

    private static LimitChain instance;

    private LimitChain(){}

    public static LimitChain getInstance(){

        if (instance == null)
            synchronized (LimitChain.class){
                if (instance == null)
                    instance = new LimitChain();
            }
        return instance;
    }

    @Override
    public StringBuilder doChain(StringBuilder mainPart, Criteria criteria) {
        Limit limit = criteria.getLimit();
        return processSingleOnePart(mainPart, limit);
    }

    public StringBuilder processSingleOnePart(StringBuilder mainPart, Limit limit){
        if (limit != null) {
            Matcher matcher = HAS_LIMIT_PATTERN.matcher(mainPart);
            if (!matcher.matches()) {
                String limitPart = getPattern(limit);
                return mainPart.append(limitPart);
            }
        }
        return mainPart;
    }

    private String getPattern(Limit limit){
        Integer count = limit.getCount();
        Integer start = limit.getStart();
        return String.format(PATTERN, start, count);
    }
}
