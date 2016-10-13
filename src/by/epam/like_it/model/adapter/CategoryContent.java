package by.epam.like_it.model.adapter;

import by.epam.like_it.model.bean.Category;
import by.epam.like_it.model.bean.util_interface.RealEntity;

public class CategoryContent implements Content {

    private Category entity;

    public CategoryContent(Category entity) {
        this.entity = entity;
    }

    @Override
    public String getTitleFieldName() {
        return null;
    }

    @Override
    public String getContentFieldName() {
        return "description";
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
        return entity.getDescription();
    }

    @Override
    public void setContent(String content) {
        entity.setDescription(content);
    }

    @Override
    public RealEntity getRealEntity() {
        return  entity;
    }
}
