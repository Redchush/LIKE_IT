package by.epam.like_it.model.vo.validation_vo.info;


public class ValidationInfoGen<T extends Number> implements IValidationInfo<T>{

    private T max;
    private T min;
    private String pattern;

    public ValidationInfoGen() {}

    public ValidationInfoGen(T minValue, T maxValue) {
        this.min = minValue;
        this.max = maxValue;
    }

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValidationInfoGen<?> that = (ValidationInfoGen<?>) o;

        if (max != null ? !max.equals(that.max) : that.max != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (min != null ? !min.equals(that.min) : that.min != null) {
            return false;
        }
        return pattern != null ? pattern.equals(that.pattern) : that.pattern == null;

    }
    @Override
    public int hashCode() {
        int result = max != null ? max.hashCode() : 0;
        result = 31 * result + (min != null ? min.hashCode() : 0);
        result = 31 * result + (pattern != null ? pattern.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ValidationInfoGen{");
        sb.append("max=").append(max);
        sb.append(", min=").append(min);
        sb.append(", pattern='").append(pattern).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
