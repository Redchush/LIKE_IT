package by.epam.like_it.model.criteria_to.core.constriction;


import java.util.Set;

/**
 *
 * @param <T> - class to be restricted
 * @param <D> - type of field to be restricted
 *
 */
public class InConstriction<T, D> extends Constriction<T>{

    public enum InType{
        IN, NOT_IN;
    }

    private Class<T> beanClass;
    private String field;
    private Set<D> orValues;
    private InType inType;

    public InConstriction(){
        type = ConstrictionType.IN;
        this.inType = InType.IN;
    }

    public InConstriction(Class<T> beanClass, String field, Set<D> orValues) {
        this();
        this.beanClass = beanClass;
        this.field = field;
        this.orValues = orValues;
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

    public Set<D> getOrValues() {
        return orValues;
    }

    public void setOrValues(Set<D> orValues) {
        this.orValues = orValues;
    }

    public InType getInType() {
        return inType;
    }

    public void setInType(InType inType) {
        this.inType = inType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InConstriction<?, ?> that = (InConstriction<?, ?>) o;

        if (beanClass != null ? !beanClass.equals(that.beanClass) : that.beanClass != null) {
            return false;
        }
        if (field != null ? !field.equals(that.field) : that.field != null) {
            return false;
        }
        if (orValues != null ? !orValues.equals(that.orValues) : that.orValues != null) {
            return false;
        }
        return inType == that.inType;

    }

    @Override
    public int hashCode() {
        int result = beanClass != null ? beanClass.hashCode() : 0;
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (orValues != null ? orValues.hashCode() : 0);
        result = 31 * result + (inType != null ? inType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InConstriction{");
        sb.append("beanClass=").append(beanClass);
        sb.append(", field='").append(field).append('\'');
        sb.append(", orValues=").append(orValues);
        sb.append(", inType=").append(inType);
        sb.append('}');
        return sb.toString();
    }

}
