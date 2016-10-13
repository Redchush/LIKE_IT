package by.epam.like_it.model.bean;

import by.epam.like_it.model.bean.util_interface.Entity;

public class FavoriteUserTag implements Entity{

    private Long userId;
    private Long tagId;

    public FavoriteUserTag() {
    }

    public FavoriteUserTag(Long userId, Long tagId) {
        this.userId = userId;
        this.tagId = tagId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

        FavoriteUserTag that = (FavoriteUserTag) o;

        //noinspection SimplifiableIfStatement
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        return tagId != null ? tagId.equals(that.tagId) : that.tagId == null;

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FavoriteUserTag{");

        sb.append("userId=").append(userId);
        sb.append(", tagId=").append(tagId);
        sb.append('}');
        return sb.toString();
    }


}