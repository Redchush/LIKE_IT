package by.epam.like_it.model.bean;

public class CategoryTag{

    private Long categoryId;
    private Long tagId;

    public CategoryTag() {
    }

    public CategoryTag(Long categoryId, Long tagId) {
        this.categoryId = categoryId;
        this.tagId = tagId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryTag that = (CategoryTag) o;

        //noinspection SimplifiableIfStatement
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) {
            return false;
        }
        return tagId != null ? tagId.equals(that.tagId) : that.tagId == null;

    }

    @Override
    public int hashCode() {
        int result = categoryId != null ? categoryId.hashCode() : 0;
        result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CategoryTag{");

        sb.append("categoryId=").append(categoryId);
        sb.append(", tagId=").append(tagId);
        sb.append('}');
        return sb.toString();
    }


}