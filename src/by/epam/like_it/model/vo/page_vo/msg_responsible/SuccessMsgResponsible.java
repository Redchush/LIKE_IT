package by.epam.like_it.model.vo.page_vo.msg_responsible;


import static by.epam.like_it.common_util.ResourceManager.FRONTEND_EN;

public class SuccessMsgResponsible {

    private static final String KEY_FORMAT = "locale.success.%s";
    private static final String KEY_IF_ABSENCE = "locale.success.unnamed";

    private String commandName;
    private String result;

    public SuccessMsgResponsible(){}

    public SuccessMsgResponsible(String name){
        this.commandName = name.toLowerCase();
        reallySetResult(commandName);
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
        reallySetResult(commandName);
    }

    private void reallySetResult(String commandName){
        String key = String.format(KEY_FORMAT, commandName);
        boolean b = FRONTEND_EN.containsKey(key);
        if (b){
            this.result = key;
        } else {
            this.result = KEY_IF_ABSENCE;
        }
    }

    public String getResult() {
        return result;
    }
}
