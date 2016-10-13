package by.epam.like_it.model.vo.page_vo;


public class CountMsgResponsible {



    public CountMsgResponsible() {
    }

    /**
     * ONE - 1
     * SEVERAL - ends on 2-4
     * MANY - other case
     */

    private enum Postfixes{

        ONE, SEVERAL, MANY
    }
}
