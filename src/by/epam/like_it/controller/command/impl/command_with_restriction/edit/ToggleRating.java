package by.epam.like_it.controller.command.impl.command_with_restriction.edit;


import by.epam.like_it.common_util.TimeUtil;
import by.epam.like_it.controller.command.impl.AbstractPostAuthCommand;
import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.exception.service.system.ServiceSystemException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.bean.Rating;
import by.epam.like_it.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

public class ToggleRating extends AbstractPostAuthCommand {

    private static final String RATING_PARAM = "rating";
    private static final String HAS_RATING_PARAM = "has_previous";


    private static ToggleRating instance;

    private ToggleRating(){}

    public static ToggleRating getInstance(){

        if (instance == null)
            synchronized (ToggleRating.class){
                if (instance == null)
                    instance = new ToggleRating();
            }
        return instance;
    }


    @Override
    public String reallyExecute(HttpServletRequest request, HttpServletResponse response, Long who)
            throws ServiceSystemException, ValidationInfoException {
        String ratingVal = request.getParameter(RATING_PARAM);
        String answerID = request.getParameter(CommandConstants.POST_PARENT_ID_PARAM);
        String parameter = request.getParameter(HAS_RATING_PARAM);

        Byte ratByte = Byte.parseByte(ratingVal);
        Long answerIdLong = Long.parseLong(answerID);

        Rating rating = new Rating();
        rating.setUserId(who);
        rating.setAnswerId(answerIdLong);
        rating.setRating(ratByte);

        if (Boolean.valueOf(parameter)){
            Timestamp currentTimeStamp = TimeUtil.getCurrentTimestampSt();
            rating.setUpdatedDate(currentTimeStamp);
        }

        ClientService userService = SERVICE_FACTORY.getClientService();
        userService.toggleRating(rating);
        return null;
    }
}
