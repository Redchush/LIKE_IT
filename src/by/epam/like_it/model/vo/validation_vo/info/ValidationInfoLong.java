package by.epam.like_it.model.vo.validation_vo.info;

/**
 * contains basic information about validation of one field
 */
public class ValidationInfoLong implements IValidationInfo<Long> {

    private Long max;
    private Long min;
    private String pattern;
    private String fieldName;
    private String beanName;

    public ValidationInfoLong() {}

    public ValidationInfoLong(Long min, Long max) {
        this.max = max;
        this.min = min;
    }

    public Long getMax() {
        return max;
    }


    public void setMax(Long max) {
        this.max = max;
    }


    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }


    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValidationInfoLong that = (ValidationInfoLong) o;

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
        final StringBuilder sb = new StringBuilder("ValidationInfoLong{");

        sb.append("max=").append(max);
        sb.append(", min=").append(min);
        sb.append(", pattern='").append(pattern).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
