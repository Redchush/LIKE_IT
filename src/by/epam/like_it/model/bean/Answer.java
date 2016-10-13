package by.epam.like_it.model.bean;

import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.RealEntity;
import by.epam.like_it.model.bean.util_interface.TimeDependent;

import java.sql.Timestamp;

public class Answer implements DeletableByBan, TimeDependent, RealEntity {

    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Boolean banned;

    public Answer() {}

    public Answer(Long id, Long userId, Long postId, String content, Timestamp createdDate,
                  Timestamp updatedDate, Boolean banned) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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

        Answer answer = (Answer) o;

        if (id != null ? !id.equals(answer.id) : answer.id != null) {
            return false;
        }
        if (userId != null ? !userId.equals(answer.userId) : answer.userId != null) {
            return false;
        }
        if (postId != null ? !postId.equals(answer.postId) : answer.postId != null) {
            return false;
        }
        if (content != null ? !content.equals(answer.content) : answer.content != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(answer.createdDate) : answer.createdDate != null) {
            return false;
        }
        if (updatedDate != null ? !updatedDate.equals(answer.updatedDate) : answer.updatedDate != null) {
            return false;
        }
        return banned != null ? banned.equals(answer.banned) : answer.banned == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (banned != null ? banned.hashCode() : 0);
        return result;
    }
    @Override
    public void initRequiredDefault() {
        if (banned == null) {
            banned = getDefaultCreateBan();
        }
        if (createdDate == null) {
            createdDate = getDefaultCreatedDate();
        }
    }

    @Override
    public void initAllDefault() {
        initRequiredDefault();
        if (updatedDate == null){
            updatedDate = getDefaultUpdatedDate();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Answer{");

        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", postId=").append(postId);
        sb.append(", content='").append(content).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append(", updatedDate=").append(updatedDate);
        sb.append(", banned=").append(banned);
        sb.append('}');
        return sb.toString();
    }



}