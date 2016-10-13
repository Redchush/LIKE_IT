package by.epam.like_it.model.bean;

import by.epam.like_it.model.bean.util_interface.Entity;

public class ReadedPost implements Entity{

    private Long userId;
    private Long postId;

    public ReadedPost() {
    }

    public ReadedPost(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReadedPost that = (ReadedPost) o;

        //noinspection SimplifiableIfStatement
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        return postId != null ? postId.equals(that.postId) : that.postId == null;

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReadedPost{");

        sb.append("userId=").append(userId);
        sb.append(", postId=").append(postId);
        sb.append('}');
        return sb.toString();
    }


}