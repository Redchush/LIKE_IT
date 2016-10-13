package by.epam.like_it.model.criteria_to.core.constriction;


import java.util.Set;

public class LikeConstriction<T> extends Constriction<T> {

    public enum LikeType{
        STARTS_WITH, ENDS_WITH, CONTAINS
    }

    private Class<T> beanClass;
    private String field;
    private Set<String> orValues;
    private LikeType likeType;


    public LikeConstriction(){
        type = ConstrictionType.LIKE;
        likeType = LikeType.CONTAINS;
    }

    public LikeConstriction(Class<T> beanClass, String field, Set<String> orValues) {
        this();
        this.beanClass = beanClass;
        this.field = field;
        this.orValues = orValues;
    }

    public LikeConstriction(Class<T> beanClass, String field, Set<String> orValues,
                            LikeType likeType) {
        this.beanClass = beanClass;
        this.field = field;
        this.orValues = orValues;
        this.likeType = likeType;
    }

    public LikeType getLikeType() {
        return likeType;
    }

    public void setLikeType(LikeType likeType) {
        this.likeType = likeType;
    }

    public Class<T> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<T> beanClass) {
        this.beanClass = beanClass;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Set<String> getOrValues() {
        return orValues;
    }

    public void setOrValues(Set<String> orValues) {
        this.orValues = orValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LikeConstriction<?> that = (LikeConstriction<?>) o;

        if (beanClass != null ? !beanClass.equals(that.beanClass) : that.beanClass != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (field != null ? !field.equals(that.field) : that.field != null) {
            return false;
        }
        return orValues != null ? orValues.equals(that.orValues) : that.orValues == null;

    }

    @Override
    public int hashCode() {
        int result = beanClass != null ? beanClass.hashCode() : 0;
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (orValues != null ? orValues.hashCode() : 0);
        return result;
    }
}
