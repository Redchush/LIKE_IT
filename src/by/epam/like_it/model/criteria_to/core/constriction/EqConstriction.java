package by.epam.like_it.model.criteria_to.core.constriction;


import java.util.Set;

/**
 *
 * @param <T> - type of field to be restricted
 * @param <D> - class to be restricted
 */
public class EqConstriction<T, D> extends Constriction<T>  {

    private Class<T> beanClass;
    private String field;
    private Set<D> orValues;

    public EqConstriction(){
        type = ConstrictionType.EQ;
    }

    public EqConstriction(Class<T> beanClass, String field, Set<D> orValues) {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EqConstriction{");
        sb.append("beanClass=").append(beanClass);
        sb.append(", field='").append(field).append('\'');
        sb.append(", orValues=").append(orValues);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EqConstriction<?, ?> that = (EqConstriction<?, ?>) o;

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
