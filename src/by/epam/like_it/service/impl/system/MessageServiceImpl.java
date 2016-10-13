package by.epam.like_it.service.impl.system;


import by.epam.like_it.controller.command.util.CommandConstants;
import by.epam.like_it.model.vo.page_vo.CodeConstants;
import by.epam.like_it.model.vo.page_vo.MsgBean;
import by.epam.like_it.model.vo.system_vo.error_bean.ErrorInfo;
import by.epam.like_it.model.vo.system_vo.error_bean.InvalidInfo;
import by.epam.like_it.service.MessageService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static by.epam.like_it.common_util.ResourceManager.FRONTEND_EN;

public class MessageServiceImpl implements by.epam.like_it.service.MessageService {

    private static final String KEY_FORMAT_SUCCESS = "locale.success.%s";
    private static final String KEY_IF_ABSENCE_SUCCESS = "locale.success.unnamed";

    /**
     * locale.invalid.${failedBean}.${failedObject}
     */
    private static final String KEY_FORMAT_INVALID = "locale.invalid.%s.%s";
    /**
     * locale.invalid.${failedBean}
     */
    private static final String KEY_FORMAT_INVALID_IF_ABSENCE = "locale.invalid.%s";
    private static final String INVALID_TOTAL_ABSENCE = "locale.invalid.unnamed";
    /**
     locale.error.${action}.${failedField}
     */
    private static final String KEY_FORMAT_ERROR = "locale.error.%s.%s";
    /**
     locale.error.${action}.unnamed
     */
    private static final String KEY_FORMAT_ERROR_IF_ABSENCE = "locale.error.%s.unnamed";
    private static final String ERROR_TOTAL_ABSENCE = "locale.error.unnamed";

    private static final String KEY_FORMAT_BANNED = "locale.security.banned.%s";
    private static final String KEY_FORMAT_BANNED_ANCSENCE = "locale.security.not_found";


    private static MessageService instance;

    private MessageServiceImpl(){}

    public static MessageService getInstance(){

        if (instance == null)
            synchronized (MessageServiceImpl.class){
                if (instance == null)
                    instance = new MessageServiceImpl();
            }
        return instance;
    }

    @Override
    public MsgBean getBeanForBanned(Class clazz){
        MsgBean result = new MsgBean();
        result.setCode(CodeConstants.BUNNED_CODE);
        String key =  String.format(KEY_FORMAT_BANNED, clazz.getSimpleName());
        key = FRONTEND_EN.containsKey(key) ?  key : KEY_FORMAT_BANNED_ANCSENCE;
        result.setMsgKeys(Collections.singletonList(key));
        return result;
    }

    @Override
    public MsgBean getBeanForSuccess(CommandConstants command){
        return getBeanForSuccess(command.toString());
    }

    @Override
    public MsgBean getBeanForSuccess(String command){
        MsgBean result = new MsgBean();
        result.setCode(CodeConstants.SUCCESS_CODE);
        String commandName = normalizeCommandName(command);
        String key = String.format(KEY_FORMAT_SUCCESS, commandName);
        key = FRONTEND_EN.containsKey(key) ? key : KEY_IF_ABSENCE_SUCCESS;
        result.setMsgKeys(Collections.singletonList(key));
        return result;
    }
    @Override
    public MsgBean getBeanForError(String command, String field){
        ErrorInfo info = new ErrorInfo();
        info.setFailedAction(command);
        info.setFailedField(field);
        return getBeanForError(info);
    }

    @Override
    public MsgBean getBeanForError(ErrorInfo info){
        MsgBean result = new MsgBean();
        result.setCode(CodeConstants.ERROR_CODE);

        String commandName = normalizeCommandName(info.getFailedAction());
        String field = info.getFailedField();

        String key = String.format(KEY_FORMAT_ERROR, commandName, field);
        boolean hasKey = FRONTEND_EN.containsKey(key);
        if (!hasKey){
            key = String.format(KEY_FORMAT_ERROR_IF_ABSENCE, commandName);
            key =  FRONTEND_EN.containsKey(key) ? key : ERROR_TOTAL_ABSENCE;
        }
        result.setMsgKeys(Collections.singletonList(key));
        return result;
    }

    @Override
    public MsgBean getBeanForInvalid(Class clazz, String field){
        InvalidInfo info = new InvalidInfo();
        info.setFailedBean(clazz.getSimpleName());
        info.setFailedFields(Collections.singletonList(field));
        return getBeanForInvalid(info);
    }

    @Override
    public String getSimpleKeyForInvalid(String simpleName, String field){
       return getSimpleKeyForInvalid(simpleName, field, false);
    }

    /**
     * @param simpleName - simple class name
     * @param field -  violated field
     * @param observed - whether something has intention to check result on total abscence
     * @return empty string if observed = true and INVALID_TOTAL_ABSENCE if observed = false
     */
    private String getSimpleKeyForInvalid(String simpleName, String field, boolean observed){
        String key =  String.format(KEY_FORMAT_INVALID, simpleName, field);
        if (!FRONTEND_EN.containsKey(key)) {
            key = String.format(KEY_FORMAT_INVALID_IF_ABSENCE, simpleName);
            if (!FRONTEND_EN.containsKey(key)) {
                key = observed ?  "" : INVALID_TOTAL_ABSENCE ;
            }
        }
        return key;
    }

    @Override
    public MsgBean getBeanForInvalid(InvalidInfo info){
        MsgBean result = new MsgBean();
        result.setCode(CodeConstants.INVALID_CODE);

        String bean = info.getFailedBean();
        List<String> resultList = new ArrayList<>();
        if (bean == null || info.getFailedFields().isEmpty()) {
            resultList.add(INVALID_TOTAL_ABSENCE);
        } else {
            for (String field : info.getFailedFields()){
                String key = getSimpleKeyForInvalid(bean, field);
                if (!key.isEmpty()) {
                    resultList.add(key);
                }
            }
        }
        if (resultList.isEmpty()){
            resultList.add(INVALID_TOTAL_ABSENCE);
        }
        result.setMsgKeys(resultList);
        return result;
    }

    private String normalizeCommandName(CommandConstants command){
        return command.toString().toLowerCase();
    }

    private String normalizeCommandName(String command){
        return command.replace('-', '_').toLowerCase();
    }

}
