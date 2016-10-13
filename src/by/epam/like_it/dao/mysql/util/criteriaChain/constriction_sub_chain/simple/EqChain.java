package by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.simple;


import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.AbstractConstrictionSubChain;
import by.epam.like_it.model.criteria_to.core.constriction.Constriction;
import by.epam.like_it.model.criteria_to.core.constriction.ConstrictionType;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EqChain extends AbstractConstrictionSubChain {

    private static final String PATTERN = "%s.%s = '%s'";

    private static EqChain instance;

    protected EqChain(){
        next = LikeChain.getInstance();
    }

    public static EqChain getInstance(){

        if (instance == null)
            synchronized (EqChain.class){
                if (instance == null)
                    instance = new EqChain();
            }
        return instance;
    }

    @Override
    public StringBuilder doSubChain(StringBuilder subPart,
                                    Map<ConstrictionType, List<Constriction>> constrictions,
                                    boolean needAnd) {
        List<Constriction> eqs = constrictions.get(ConstrictionType.EQ);
        if (eqs == null || eqs.isEmpty()){
            return next.doSubChain(subPart, constrictions, false);
        }
        processSingleOnePart(subPart, eqs, needAnd);
        if (next != null){
            next.doSubChain(subPart, constrictions, true);
        }
        return subPart;
    }
    @Override
    public StringBuilder processSingleOnePart(StringBuilder subPart, List<Constriction> eqs , boolean needAnd){
        String collectedConstrictions = collectAll(eqs);
        subPart.append(collectedConstrictions);
        return subPart;
    }


    private String collectAll(List<Constriction> eqs){
        return eqs.stream().map(constriction -> {
            return collectOne(constriction);
        }).collect(Collectors.joining(" AND ", "\n", ""));
    }

    /**
     * collect one constriction, joining Eq values with OR between them. If there presents more than one value, wrap
     * result in parenthesis
     * @param constriction
     * @return
     */

    private String collectOne(Constriction constriction){
        EqConstriction eq = (EqConstriction) constriction;
        Class beanClass = eq.getBeanClass();
        String field = eq.getField();
        String tableName = ResourceNavigator.getRefTable(beanClass);
        String fieldName = ResourceNavigator.getRefTableField(beanClass.getSimpleName(), field);
        Set<Object> ors = new HashSet<>();
        ors.addAll(eq.getOrValues());

        Stream<String> stringStream = ors.stream().map(s -> String.format(getPattern(), tableName, fieldName, s.toString()));
        return ors.size() > 1 ? stringStream.collect(Collectors.joining(" OR ", "(", ")"))
                              : stringStream.collect(Collectors.joining(" OR "));
    }

    protected String getPattern(){
        return PATTERN;
    }
}
