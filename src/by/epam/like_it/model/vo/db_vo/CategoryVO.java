package by.epam.like_it.model.vo.db_vo;

import by.epam.like_it.model.bean.Category;
import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.Tag;

import java.util.ArrayList;
import java.util.List;

public class CategoryVO implements EntityVO {



    private Category parentCategory; //parameter can be null//
    private Category category;
    private List<CategoryVO> subCategories;

    //parameters that can be null in db, but in orm represents by empty list//
    private List<Post> posts;
    private List<Tag> tags;

    public CategoryVO() {
        posts = new ArrayList<>(0);
        tags = new ArrayList<>(0);
        subCategories = new ArrayList<>(0);
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<CategoryVO> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<CategoryVO> subCategories) {
        this.subCategories = subCategories;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryVO that = (CategoryVO) o;

        if (parentCategory != null ? !parentCategory.equals(that.parentCategory) : that.parentCategory != null) {
            return false;
        }
        if (category != null ? !category.equals(that.category) : that.category != null) {
            return false;
        }
        if (subCategories != null ? !subCategories.equals(that.subCategories) : that.subCategories != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (posts != null ? !posts.equals(that.posts) : that.posts != null) {
            return false;
        }
        return tags != null ? tags.equals(that.tags) : that.tags == null;

    }

    @Override
    public int hashCode() {
        int result = parentCategory != null ? parentCategory.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (subCategories != null ? subCategories.hashCode() : 0);
        result = 31 * result + (posts != null ? posts.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CategoryVO{");
        sb.append(super.toString()).append(" ");

        sb.append("parentCategory=").append(parentCategory);
        sb.append(", category=").append(category);
        sb.append(", subCategories=").append(subCategories);
        sb.append(", posts=").append(posts);
        sb.append(", tags=").append(tags);
        sb.append('}');
        return sb.toString();
    }
}
