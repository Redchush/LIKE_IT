package by.epam.like_it.controller.command.util;


import by.epam.like_it.exception.service.action.ServiceActionDetectableException;
import by.epam.like_it.exception.service.validation.info.ValidationInfoException;
import by.epam.like_it.model.vo.page_vo.MsgBean;
import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;
import by.epam.like_it.service.MessageService;
import by.epam.like_it.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

public class MsgHandler {

    private final MessageService msgService;
    private static MsgHandler instance;

    private MsgHandler(){
        msgService = ServiceFactory.getInstance().getMessageService();
    }

    public static MsgHandler getInstance(){

        if (instance == null)
            synchronized (MsgHandler.class){
                if (instance == null)
                    instance = new MsgHandler();
            }
        return instance;
    }

    public void handleFailedValidation(HttpServletRequest request, ValidationInfoException e){
        MsgBean beanForInvalid = msgService.getBeanForInvalid(e.getInvalidInfo());
        request.setAttribute(CommandConstants.MSG_BEAN, beanForInvalid);
    }

    public void handleFailedValidation(HttpServletRequest request, Class clazz, String field){
        MsgBean beanForInvalid = msgService.getBeanForInvalid(clazz, field);
        request.setAttribute(CommandConstants.MSG_BEAN, beanForInvalid);
    }

    public void handleFailedAction(HttpServletRequest request, ServiceActionDetectableException e) {
        String parameter = request.getParameter(CommandConstants.COMMAND_ATTR);
        ErrorInfo errorInfo = e.getErrorInfo();
        errorInfo.setFailedAction(parameter);
        MsgBean errorBean = msgService.getBeanForError(e.getErrorInfo());
        request.setAttribute(CommandConstants.MSG_BEAN, errorBean);
    }

    public void handleFailedAction(HttpServletRequest request, String field){
        String command = request.getParameter(CommandConstants.COMMAND_ATTR);
        MsgBean beanForInvalid = msgService.getBeanForError(command, field);
        request.setAttribute(CommandConstants.MSG_BEAN, beanForInvalid);
    }

    public void handleSuccessAction(HttpServletRequest request) {
        String command = request.getParameter(CommandConstants.COMMAND_ATTR);
        if (command != null) {
            MsgBean successBean = msgService.getBeanForSuccess(command);
            request.setAttribute(CommandConstants.MSG_BEAN, successBean);
        }
    }

    public void handleEntityBanned(HttpServletRequest request, Class entityClass) {
        MsgBean banBean = msgService.getBeanForBanned(entityClass);
        request.setAttribute(CommandConstants.MSG_BEAN, banBean);
    }
}
