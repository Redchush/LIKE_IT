package by.epam.like_it.model.bean;

import by.epam.like_it.model.bean.util_interface.RealEntity;

public class FavoriteUserPost implements RealEntity{

    private Long id;
    private Long userId;
    private Long postId;
    private String comment;

    public FavoriteUserPost() {}

    public FavoriteUserPost(Long id, Long userId, Long postId, String comment) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FavoriteUserPost that = (FavoriteUserPost) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (postId != null ? !postId.equals(that.postId) : that.postId != null) {
            return false;
        }
        return comment != null ? comment.equals(that.comment) : that.comment == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FavoriteUserPost{");

        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", postId=").append(postId);
        sb.append(", comment='").append(comment).append('\'');
        sb.append('}');
        return sb.toString();
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public void initRequiredDefault() {
        /*NOP*/
    }

    @Override
    public void initAllDefault() {
        /*NOP*/
    }
}