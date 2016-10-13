package by.epam.like_it.service;

import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.model.vo.page_vo.MsgBean;
import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;

/**
 * Service for creating messages
 */
public interface MessageService {
    MsgBean getBeanForBanned(Class clazz);

    MsgBean getBeanForSuccess(CommandConstants command);

    MsgBean getBeanForSuccess(String command);

    MsgBean getBeanForError(String command, String field);

    MsgBean getBeanForError(ErrorInfo info);

    MsgBean getBeanForInvalid(Class clazz, String field);

    String getSimpleKeyForInvalid(String simpleName, String field);

    MsgBean getBeanForInvalid(InvalidInfo info);
}
