package by.epam.like_it.model.vo.validation_vo;


import by.epam.like_it.common_util.ResourceManager;
import by.epam.like_it.model.vo.validation_vo.info.ValidationInfoLong;
import by.epam.like_it.service.MessageService;
import by.epam.like_it.service.ServiceFactory;

import java.util.MissingResourceException;

/**
 * Produce messages for frontend by current ValidationInfo
 */
public class ValidationMsgResponsible {

    private static final MessageService MESSAGE_SERVICE;

    static {
        MESSAGE_SERVICE = ServiceFactory.getInstance().getMessageService();
    }

    private static final String WHAT_PATTERN = "locale.tooltip.entity.%s";
    private static final String IF_ABSENCE_WHAT_KEY = "locale.tooltip.entity";

    private  final String LENGTH_PATTERN_RU;
    private  final String LENGTH_PATTERN_EN;

    private static final String RU = "ru";
    private static final String EN = "en";
    {
        String key = "locale.tooltip.length_pattern";
        LENGTH_PATTERN_EN = ResourceManager.FRONTEND_EN.getString(key);
        LENGTH_PATTERN_RU = ResourceManager.FRONTEND_RU.getString(key);
    }
    private String lang;
    private ValidationInfoLong info;
    private String what;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public ValidationInfoLong getInfo() {
        return info;
    }

    public void setInfo(ValidationInfoLong info) {
        this.info = info;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    /**
     * @return message that notify about restrictions for data to be entered by client and empty sting if data for
     * produce message not sufficient
     */
    public String getRestrictionMsg(){
        String currentLang = this.lang == null ? EN : this.lang;
        String what = this.what == null ? info.getFieldName() : this.what;
        String key = String.format(WHAT_PATTERN, what);
        if (currentLang.contains(RU)) {
            String entity_loc = getFinalPattern(ResourceManager.FRONTEND_RU, key);
            return String.format(LENGTH_PATTERN_RU, entity_loc, info.getMin(), info.getMax());
        } else {
            String entity_loc = getFinalPattern(ResourceManager.FRONTEND_EN, key);
            return String.format(LENGTH_PATTERN_EN, entity_loc, info.getMin(), info.getMax());
        }
    }
    /**
     * @return message that notify about data entering fail by reason of failed validation
     */
    public String getInvalidMsg(){
        String currentLang = this.lang == null ? EN : this.lang;
        String what = this.what == null ? info.getFieldName() : this.what;
        String beanName = info.getBeanName();
        String simpleKeyForInvalid = MESSAGE_SERVICE.getSimpleKeyForInvalid(what, beanName);
        if (currentLang.contains(RU)){
            return ResourceManager.FRONTEND_RU.getString(simpleKeyForInvalid);
        }else {
            return ResourceManager.FRONTEND_EN.getString(simpleKeyForInvalid);
        }
    }

    private String getFinalPattern(ResourceManager manager, String key){
        try{
            return manager.getString(key);
        } catch (MissingResourceException e){
            return manager.getString(IF_ABSENCE_WHAT_KEY);
        }
    }
}
