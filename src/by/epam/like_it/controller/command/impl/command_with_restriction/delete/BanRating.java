package by.epam.like_it.controller.command.impl.command_with_restriction.delete;

import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.model.bean.Rating;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class BanRating extends AbstractDeleteResponsibleCommand {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static BanRating instance;

    private BanRating(){}

    public static BanRating getInstance(){

        if (instance == null)
            synchronized (BanRating.class){
                if (instance == null)
                    instance = new BanRating();
            }
        return instance;
    }


    @Override
    protected DeletableByBan getDeletableByBan(HttpServletRequest request, Long who, Long what) {
        Rating rating = new Rating();
        rating.setId(what);
        rating.setUserId(who);
        return rating;
    }
}
