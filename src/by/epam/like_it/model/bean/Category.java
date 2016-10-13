package by.epam.like_it.model.bean;

import by.epam.like_it.model.bean.util_interface.NotDeletable;
import by.epam.like_it.model.bean.util_interface.RealEntity;

import java.sql.Timestamp;
import java.util.Date;

public class Category implements NotDeletable, RealEntity {

    private Long id;
    private String title;
    private Byte languageId;
    private String description;
    private Long parentCategory;
    private Timestamp createdDate;
    private Boolean published;


    public Category() {
    }

    public Category(Long id, String title, Byte languageId, String  description, Long parentCategory,
                    Timestamp createdDate, Boolean published) {
        this.id = id;
        this.title = title;
        this.languageId = languageId;
        this.description = description;
        this.parentCategory = parentCategory;
        this.createdDate = createdDate;
        this.published = published;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Long parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Byte getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Byte languageId) {
        this.languageId = languageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) {
            return false;
        }
        if (title != null ? !title.equals(category.title) : category.title != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(category.createdDate) : category.createdDate != null) {
            return false;
        }
        if (description != null ? !description.equals(category.description) : category.description != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (parentCategory != null ? !parentCategory.equals(category.parentCategory)
                                   : category.parentCategory != null) {
            return false;
        }
        return published != null ? published.equals(category.published) : category.published == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (parentCategory != null ? parentCategory.hashCode() : 0);
        result = 31 * result + (published != null ? published.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CategoryContent{");

        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append(", description='").append(description).append('\'');
        sb.append(", parentCategory=").append(parentCategory);
        sb.append(", published=").append(published);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void initRequiredDefault() {
        if (published == null) {
            published = getDefaultCreateBan();
        }
        if (createdDate == null) {
            createdDate = new Timestamp(new Date().getTime());
        }
    }

    @Override
    public void initAllDefault() {
        initRequiredDefault();
    }
}