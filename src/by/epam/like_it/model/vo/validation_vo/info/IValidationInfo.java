package by.epam.like_it.model.vo.validation_vo.info;


public interface IValidationInfo<T extends Number> {
    /**
     * If it's :
     *  String field - return max length
     *  Number field - return max value
     *  Timestamp field - return value using a milliseconds time value.
     * @return max value of some field.
     *
     */
    T getMax();

    void setMax(T max);

    /**
     * If it's :
     *  String field - return max length
     *  Number field - return max value
     *  Timestamp field - return value using a milliseconds time value.
     * @return min value of some field.
     *
     */
    T getMin();

    void setMin(T min);

    /**
     * @return pattern by which value of some field must to be validated
     */
    String getPattern();

    void setPattern(String pattern);
}
