package by.epam.like_it.model.vo.db_vo;


import by.epam.like_it.model.bean.Post;
import by.epam.like_it.model.bean.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TagVO implements EntityVO{

    private Tag tag;
    private TagInfo info;
    private List<Post> postTag;

    public TagVO() {
        postTag = new ArrayList<>();
    }

    public TagVO(Tag tag){
        this.tag = tag;
    }

    public List<Post> getPostTag() {
        return postTag;
    }

    public void setPostTag(List<Post> postTag) {
        this.postTag = postTag;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public TagInfo getInfo() {
        return info;
    }

    public void setInfo(TagInfo info) {
        this.info = info;
    }

    public class TagInfo implements Serializable{

        private Long countPostTag;
        private Long countFavoriteUserTag;

        public Long getCountPostTag() {
            return countPostTag;
        }

        public void setCountPostTag(Long countPostTag) {
            this.countPostTag = countPostTag;
        }

        public Long getCountFavoriteUserTag() {
            return countFavoriteUserTag;
        }

        public void setCountFavoriteUserTag(Long countFavoriteUserTag) {
            this.countFavoriteUserTag = countFavoriteUserTag;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            TagInfo tagInfo = (TagInfo) o;

            if (countPostTag != null ? !countPostTag.equals(tagInfo.countPostTag) : tagInfo.countPostTag != null) {
                return false;
            }
            //noinspection SimplifiableIfStatement

            return countFavoriteUserTag != null ? countFavoriteUserTag.equals(tagInfo.countFavoriteUserTag)
                                                : tagInfo.countFavoriteUserTag == null;

        }

        @Override
        public int hashCode() {
            int result = countPostTag != null ? countPostTag.hashCode() : 0;
            result = 31 * result + (countFavoriteUserTag != null ? countFavoriteUserTag.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("TagInfo{");
            sb.append("countPostTag=").append(countPostTag);
            sb.append(", countFavoriteUserTag=").append(countFavoriteUserTag);
            sb.append('}');
            return sb.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TagVO tagVO = (TagVO) o;

        if (tag != null ? !tag.equals(tagVO.tag) : tagVO.tag != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (info != null ? !info.equals(tagVO.info) : tagVO.info != null) {
            return false;
        }
        return postTag != null ? postTag.equals(tagVO.postTag) : tagVO.postTag == null;

    }

    @Override
    public int hashCode() {
        int result = tag != null ? tag.hashCode() : 0;
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (postTag != null ? postTag.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TagVO{");
        sb.append("tag=").append(tag);
        sb.append(", info=").append(info);
        sb.append(", postTag=").append(postTag);
        sb.append('}');
        return sb.toString();
    }


}
