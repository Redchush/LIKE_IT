package by.epam.like_it.model.vo.db_vo;


import java.util.List;

public class SimpleCategoryVO implements EntityVO{

    private Long id;
    private String title;
    private List<SimpleCategoryVO> subCategories;
    private boolean hasChildren;

    public SimpleCategoryVO() {}


    public SimpleCategoryVO(Long id, String title) {
        this.id = id;
        this.title = title;
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

    public List<SimpleCategoryVO> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SimpleCategoryVO> subCategories) {
        this.subCategories = subCategories;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimpleCategoryVO that = (SimpleCategoryVO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (title != null ? !title.equals(that.title) : that.title != null) {
            return false;
        }
        return subCategories != null ? subCategories.equals(that.subCategories) : that.subCategories == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (subCategories != null ? subCategories.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleCategoryVO{");

        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", subCategories=").append(subCategories);
        sb.append('}');
        return sb.toString();
    }
}
