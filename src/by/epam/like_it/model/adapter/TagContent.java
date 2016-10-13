package by.epam.like_it.model.adapter;


import by.epam.like_it.model.bean.Tag;
import by.epam.like_it.model.bean.util_interface.RealEntity;

public class TagContent implements Content{

    private Tag entity;

    public TagContent(Tag tag) {
        this.entity = tag;
    }

    @Override
    public String getTitleFieldName() {
        return null;
    }

    @Override
    public String getContentFieldName() {
        return null;
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
        return "name";
    }

    @Override
    public void setContent(String content) {
       entity.setName(content);
    }

    @Override
    public RealEntity getRealEntity() {
        return entity;
    }
}
