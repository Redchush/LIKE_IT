package by.epam.like_it.controller.command.impl.command_with_restriction.delete;


import by.epam.like_it.model.bean.Answer;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;

import javax.servlet.http.HttpServletRequest;

public class BanAnswer extends AbstractDeleteResponsibleCommand {

    private static BanAnswer instance;

    private BanAnswer(){}

    public static BanAnswer getInstance(){

        if (instance == null)
            synchronized (BanAnswer.class){
                if (instance == null)
                    instance = new BanAnswer();
            }
        return instance;
    }

    @Override
    protected DeletableByBan getDeletableByBan(HttpServletRequest request, Long who, Long what) {
        Answer answer = new Answer();
        answer.setId(what);
        answer.setUserId(who);
        return answer;
    }
}
