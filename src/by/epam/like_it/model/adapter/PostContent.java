package by.epam.like_it.model.adapter;

import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.util_interface.DeletableByBan;

public class PostContent implements DeletableByBanContent {

    private Post entity;

    public PostContent(Post post) {
        this.entity = post;
    }

    @Override
    public String getTitleFieldName() {
        return "title";
    }

    @Override
    public String getContentFieldName() {
        return "content";
    }

    @Override
    public String getTitle() {
        return entity.getTitle();
    }

    @Override
    public void setTitle(String title) {
        entity.setTitle(title);
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
