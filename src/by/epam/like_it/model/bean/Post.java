package by.epam.like_it.model.bean;

import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.TimeDependent;

import java.sql.Timestamp;

public class Post implements DeletableByBan, TimeDependent {

    private Long id;
    private Long userId;
    private Long categoryId;
    private String title;
    private String content;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Boolean banned;

    public Post() {
    }

    public Post(Long id, Long userId, Long categoryId, String title, String content,
                Timestamp createdDate, Timestamp updatedDate, Boolean banned) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.banned = banned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Post post = (Post) o;

        if (id != null ? !id.equals(post.id) : post.id != null) {
            return false;
        }
        if (userId != null ? !userId.equals(post.userId) : post.userId != null) {
            return false;
        }
        if (categoryId != null ? !categoryId.equals(post.categoryId) : post.categoryId != null) {
            return false;
        }
        if (title != null ? !title.equals(post.title) : post.title != null) {
            return false;
        }
        if (content != null ? !content.equals(post.content) : post.content != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(post.createdDate) : post.createdDate != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (updatedDate != null ? !updatedDate.equals(post.updatedDate) : post.updatedDate != null) {
            return false;
        }
        return banned != null ? banned.equals(post.banned) : post.banned == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (banned != null ? banned.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Post{");

        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append(", updatedDate=").append(updatedDate);
        sb.append(", banned=").append(banned);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void initRequiredDefault() {
        if (createdDate == null){
            createdDate = getDefaultCreatedDate();
        }
        if (banned == null){
            banned = getDefaultCreateBan();
        }
    }

    @Override
    public void initAllDefault() {
        initRequiredDefault();
        if (updatedDate == null){
            updatedDate = getDefaultUpdatedDate();
        }
    }
}