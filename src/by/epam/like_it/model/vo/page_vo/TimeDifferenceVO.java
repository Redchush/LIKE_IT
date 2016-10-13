package by.epam.like_it.model.vo.page_vo;

import java.time.LocalDateTime;


public class TimeDifferenceVO {

    private String pattern;
    private LocalDateTime dateTime;

    public TimeDifferenceVO() {}

    public TimeDifferenceVO(String pattern, LocalDateTime dateTime) {
        this.pattern = pattern;
        this.dateTime = dateTime;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
