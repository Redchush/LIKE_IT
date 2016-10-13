package by.epam.like_it.model.bean;

import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import by.epam.like_it.model.bean.util_interface.TimeDependent;

import java.sql.Timestamp;

public class Comment implements DeletableByBan, TimeDependent, RealEntity {

    private Long id;
    private Long userId;
    private Long answerId;
    private String content;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Boolean banned;

    public Comment() {}

    public Comment(Long id, Long userId, Long answerId, String content, Timestamp createdDate,
                   Timestamp updatedDate, Boolean banned) {
        this.id = id;
        this.userId = userId;
        this.answerId = answerId;
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

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
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

        Comment comment = (Comment) o;

        if (id != null ? !id.equals(comment.id) : comment.id != null) {
            return false;
        }
        if (userId != null ? !userId.equals(comment.userId) : comment.userId != null) {
            return false;
        }
        if (answerId != null ? !answerId.equals(comment.answerId) : comment.answerId != null) {
            return false;
        }
        if (content != null ? !content.equals(comment.content) : comment.content != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(comment.createdDate) : comment.createdDate != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (updatedDate != null ? !updatedDate.equals(comment.updatedDate) : comment.updatedDate != null) {
            return false;
        }
        return banned != null ? banned.equals(comment.banned) : comment.banned == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (answerId != null ? answerId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (banned != null ? banned.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");

        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", answerId=").append(answerId);
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
        if (updatedDate == null){
            updatedDate = getDefaultUpdatedDate();
        }
    }
}