package by.epam.like_it.model.adapter;


import by.epam.like_it.model.bean.util_interface.RealEntity;

/**
 * Interface that guarantee unique access to bean text fields, so it provide unique handling
 * this fields value. So it declare new type and play role of adapter from Content type to concrete RealEntity type
 * implementation
 *
 */
public interface Content {

    String getTitleFieldName();
    String getContentFieldName();

    String getTitle();
    void setTitle(String title);
    String getContent();
    void setContent(String content);

    RealEntity getRealEntity();

}
