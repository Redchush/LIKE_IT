package by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.simple;

import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.AbstractConstrictionSubChain;
import by.epam.like_it.model.criteria_to.core.constriction.Constriction;
import by.epam.like_it.model.criteria_to.core.constriction.ConstrictionType;
import by.epam.like_it.model.criteria_to.core.constriction.InConstriction;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InChain extends AbstractConstrictionSubChain {

    private static final String PATTERN_IN = "%s.%s IN (%s) ";
    private static final String PATTERN_NOT_IN = "%s.%s NOT IN (%s) ";

    private static final String SINGLE_ITEM_PATTERM = "'%s'";

    private static InChain instance;

    public InChain(){}

    public static InChain getInstance(){

        if (instance == null)
            synchronized (InChain.class){
                if (instance == null)
                    instance = new InChain();
            }
        return instance;
    }

    @Override
    public StringBuilder doSubChain(StringBuilder subPart, Map<ConstrictionType,
            List<Constriction>> constrictions, boolean needAnd) {
        List<Constriction> ins = constrictions.get(ConstrictionType.IN);
        if (ins == null || ins.isEmpty()){
            return subPart;
        }
        String collectedConstrictions = collectAll(ins);
        if (needAnd){
            subPart.append(" AND ");
        }
        subPart.append(collectedConstrictions);
        if (next != null){
            next.doSubChain(subPart, constrictions, true);
        }
        return subPart;
    }

    @Override
    public StringBuilder processSingleOnePart(StringBuilder subPart,
                                              List<Constriction> constrictions, boolean needAnd) {
        String collectedConstrictions = collectAll(constrictions);
        if (!collectedConstrictions.isEmpty()){
            if (needAnd){
                subPart.append(" AND ");
            }
            subPart.append(collectedConstrictions);
        }
        return subPart;
    }

    private String collectAll(List<Constriction> constrictions){
        return constrictions.stream()
                            .filter(this::checkSize)
                            .map(this::collectOne)
                            .collect(Collectors.joining(" AND ", "\n", ""));
    }

    private boolean checkSize(Constriction constriction){
        InConstriction in = (InConstriction) constriction;
        return !(in.getOrValues().size() == 0);
    }

    /**
     * collect one constriction, joining Eq values with OR between them. If there presents more than one value, wrap
     * result in parenthesis
     * @param constriction
     * @return
     */
    private String collectOne(Constriction constriction){
        InConstriction in = (InConstriction) constriction;
        Set orValues = in.getOrValues();
        if (orValues.size() == 0){
            return "";
        }
        Class beanClass = in.getBeanClass();
        String field = in.getField();
        String tableName = ResourceNavigator.getRefTable(beanClass);
        String fieldName = ResourceNavigator.getRefTableField(beanClass.getSimpleName(), field);

        Stream<String> collect = orValues.stream()
                           .map(s -> String.format(SINGLE_ITEM_PATTERM, s.toString()));
        String result = collect.collect(Collectors.joining(", "));
        return applyFormat(in.getInType(), tableName, fieldName, result);
    }


    private String applyFormat(InConstriction.InType type, String tableName, String fieldName, String value){
        switch (type){
            case NOT_IN:
                return String.format(PATTERN_NOT_IN, tableName, fieldName, value);
            default:
                return String.format(PATTERN_IN, tableName, fieldName, value);
        }
    }
    protected String getPattern(){
        return PATTERN_IN;
    }
}
