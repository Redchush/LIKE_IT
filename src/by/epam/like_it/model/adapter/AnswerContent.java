package by.epam.like_it.model.adapter;


import by.epam.like_it.model.bean.Answer;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;

public class AnswerContent implements DeletableByBanContent {

    private Answer entity;

    public AnswerContent(Answer entity) {
         this.entity = entity;
    }

    @Override
    public String getTitleFieldName() {
        return null;
    }

    @Override
    public String getContentFieldName() {
        return "content";
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setTitle(String title) {}

    @Override
    public String getContent() {
        return entity.getContent();
    }

    @Override
    public void setContent(String content) {
        entity.setContent(content);
    }

    @Override
    public DeletableByBan getRealEntity() {
        return entity;
    }

}
