package by.epam.like_it.model.bean;

import by.epam.like_it.model.bean.util_interface.Entity;

public class PostTag implements Entity{

    private Long postId;
    private Long tagId;

    public PostTag() {
    }

    public PostTag(Long postId, Long tagId) {
        this.postId = postId;
        this.tagId = tagId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PostTag{");

        sb.append("postId=").append(postId);
        sb.append(", tagId=").append(tagId);
        sb.append('}');
        return sb.toString();
    }
}