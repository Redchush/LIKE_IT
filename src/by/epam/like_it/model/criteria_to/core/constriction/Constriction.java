package by.epam.like_it.model.criteria_to.core.constriction;


import java.io.Serializable;

public abstract class Constriction<T> implements Serializable{

    protected ConstrictionType type;

    public ConstrictionType getType() {
        return type;
    }
}
