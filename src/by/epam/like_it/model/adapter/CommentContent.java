package by.epam.like_it.model.adapter;


import by.epam.like_it.model.bean.Comment;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;

public class CommentContent implements DeletableByBanContent {

    private Comment entity;

    public CommentContent(Comment comment) {
        this.entity = comment;
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
    public void setTitle(String title) {
        /*NOP*/
    }

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
