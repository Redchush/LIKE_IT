package by.epam.like_it.model.criteria_to.facade;

import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.criteria_to.core.Criteria;
import by.epam.like_it.model.criteria_to.core.constriction.Constriction;
import by.epam.like_it.model.criteria_to.core.constriction.ConstrictionType;
import by.epam.like_it.model.criteria_to.core.constriction.EqConstriction;

import java.util.*;


public class SimilarPostCriteria extends Criteria<Post> {

    public SimilarPostCriteria() {
        setConstrictionsAsDefaultConstrictions();
    }

    public void setConstrictionsAsDefaultConstrictions(){
        Map<ConstrictionType, List<Constriction>> defaultConstrictions = new HashMap<>();
        Set<Boolean> set = new HashSet<>();
        set.add(false);
        EqConstriction<Post, Boolean> postEqConstriction= new EqConstriction<>(Post.class, "banned", set);
        List<Constriction> constrictions = new ArrayList<>();
        constrictions.add(postEqConstriction);
        defaultConstrictions.put(ConstrictionType.EQ, constrictions);
        this.constrictions = defaultConstrictions;
    }

    public boolean putTagConstriction(Constriction<Tag> constriction){
        return super.putOtherConstriction(constriction);
    }
}
