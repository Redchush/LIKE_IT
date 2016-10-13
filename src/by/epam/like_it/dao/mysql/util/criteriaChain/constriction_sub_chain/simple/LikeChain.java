package by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.simple;


import by.epam.like_it.dao.mysql.util.ResourceNavigator;
import by.epam.like_it.dao.mysql.util.criteriaChain.constriction_sub_chain.AbstractConstrictionSubChain;
import by.epam.like_it.model.criteria_to.core.constriction.Constriction;
import by.epam.like_it.model.criteria_to.core.constriction.ConstrictionType;
import by.epam.like_it.model.criteria_to.core.constriction.LikeConstriction;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LikeChain extends AbstractConstrictionSubChain {

    private static final String PATTERN = "%s.%s LIKE '%s%s%s'";

    private static LikeChain instance;

    public LikeChain(){
        next = InChain.getInstance();
    }

    public static LikeChain getInstance(){

        if (instance == null)
            synchronized (LikeChain.class){
                if (instance == null)
                    instance = new LikeChain();
            }
        return instance;
    }

    @Override
    public StringBuilder doSubChain(StringBuilder subPart, Map<ConstrictionType,
                                     List<Constriction>> constrictions, boolean needAnd) {
        List<Constriction> likes = constrictions.get(ConstrictionType.LIKE);
        if ( (likes == null || likes.isEmpty()) && next != null){
            return next.doSubChain(subPart, constrictions, false);
        }
        String collectedConstrictions = collectAll(likes);
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
        if (needAnd){
            subPart.append(" AND ");
        }
        return subPart.append(collectedConstrictions);
    }

    private String collectAll(List<Constriction> likes){
        return likes.stream().map(constriction -> {
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
        LikeConstriction like = (LikeConstriction) constriction;
        Class beanClass = like.getBeanClass();
        String field = like.getField();
        String tableName = ResourceNavigator.getRefTable(beanClass);
        String fieldName = ResourceNavigator.getRefTableField(beanClass.getSimpleName(), field);
        Set<String> ors = like.getOrValues();

        Stream<String> stringStream = ors.stream().map(s -> applyFormat(like.getLikeType(), tableName, fieldName, s));
        return ors.size() > 1 ? stringStream.collect(Collectors.joining(" OR ", "(", ")"))
                              : stringStream.collect(Collectors.joining(" OR "));
    }


    private String applyFormat(LikeConstriction.LikeType type, String tableName, String fieldName, String value){
        switch (type){
            case ENDS_WITH:
                return String.format(getPattern(), tableName, fieldName, "%", value, "");
            case STARTS_WITH:
                return String.format(getPattern(), tableName, fieldName, "", value, "%");
            default:
                return String.format(getPattern(), tableName, fieldName, "%", value, "%");
        }
    }

    protected String getPattern(){
        return PATTERN;
    }

}
