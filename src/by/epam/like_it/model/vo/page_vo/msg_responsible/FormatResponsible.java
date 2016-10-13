package by.epam.like_it.model.vo.page_vo.msg_responsible;

public class FormatResponsible {

    private String pattern;
    private String strings;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getStrings() {
        return strings;
    }

    public void setStrings(String strings) {

        this.strings = strings;
    }

    public String getResult() {
        if (pattern != null && strings != null){
            String[] split = strings.split(";");
            return String.format(pattern, split);
        }
        return "";
    }
}
