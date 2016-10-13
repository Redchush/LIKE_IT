package by.epam.like_it.controller.command.impl.command_with_restriction.delete;


import by.epam.like_it.common_util.ClassName;
import by.epam.like_it.exception.command.CommandException;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BanUser extends AbstractDeleteResponsibleCommand {

    private static Logger LOGGER = LogManager.getLogger(ClassName.getClassName());
    private static BanUser instance;

    private BanUser(){}

    public static BanUser getInstance(){

        if (instance == null)
            synchronized (BanUser.class){
                if (instance == null)
                    instance = new BanUser();
            }
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ServiceFactory.getInstance();
        return null;
    }

    @Override
    protected DeletableByBan getDeletableByBan(HttpServletRequest request, Long who, Long what) {
        User user = new User();
        user.setId(who);
        return user;
    }
}
