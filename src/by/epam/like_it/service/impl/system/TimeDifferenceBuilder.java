package by.epam.like_it.service.impl.system;


import by.epam.like_it.model.bean.util_interface.TimeDependent;
import by.epam.like_it.model.vo.page_vo.TimeDifferenceVO;

import java.sql.Timestamp;
import java.time.*;

public class TimeDifferenceBuilder {

    private static final String years = ""; //more 5 years
    private static final String minorYears = ""; // 2 -5 years
    private static final String yearsMonths = "";  // 1 year - 2year
    private static final String months = ""; // less 1 year
    private static final String days = ""; // less 1 month
    private static final String hours = ""; // less 1 day

    private static TimeDifferenceBuilder instance;

    private TimeDifferenceBuilder(){}

    public static TimeDifferenceBuilder getInstance(){

        if (instance == null)
            synchronized (TimeDifferenceBuilder.class){
                if (instance == null)
                    instance = new TimeDifferenceBuilder();
            }
        return instance;
    }

    public TimeDifferenceVO buildTimeTimeDifferenceVO(TimeDependent dependent){
        Timestamp updatedDate = dependent.getUpdatedDate();
        LocalDateTime dateTime;
        if (updatedDate == null){
            dateTime = dependent.getCreatedDate().toLocalDateTime();
        } else{
            dateTime = updatedDate.toLocalDateTime();
        }
        Period between = Period.between(LocalDate.now(), dateTime.toLocalDate());
        between.toString();

        return new TimeDifferenceVO();
    }

    private String getPattern(Period period){
        int years = period.getYears();
        int months = period.getMonths();
        return null;
    }

    public static void main(String[] args) {
        LocalDateTime dateTime = LocalDateTime.of(2015, 1, 1, 1, 0);
        System.out.println(dateTime);
        System.out.println(Period.between(LocalDate.now(), dateTime.toLocalDate()));
        LocalTime localTime = dateTime.toLocalTime();
    }



}
