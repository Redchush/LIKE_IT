package by.epam.like_it.model.vo.db_vo;

import by.epam.like_it.model.bean.*;

import java.util.ArrayList;
import java.util.List;

public class PostVO implements EntityVO{

    private User author;
    private Post post;
    private List<Answer> answers;
    private ArrayList<Tag> tags;
    private Category category;


    private Info info;
    private CurrentUserInfo currentUserInfo;

    public PostVO() {
        answers = new ArrayList<>(0);
        tags = new ArrayList<>(0);
        currentUserInfo = new CurrentUserInfo();
        info = new Info();
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Info getInfo() {
        return info;
    }
    public void setInfo(Info info) {
        this.info = info;
    }

    public class Info {
        private Long favoriteCount;
        private Long answersCount;
        private Long readedCount;

        public Long getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(Long favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public Long getAnswersCount() {
            return answersCount;
        }

        public void setAnswersCount(Long answersCount) {
            this.answersCount = answersCount;
        }

        public Long getReadedCount() {
            return readedCount;
        }

        public void setReadedCount(Long readedCount) {
            this.readedCount = readedCount;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Info{");

            sb.append("favoriteCount=").append(favoriteCount);
            sb.append(", answersCount=").append(answersCount);
            sb.append(", readedCount=").append(readedCount);
            sb.append('}');
            return sb.toString();
        }
    }

    public CurrentUserInfo getCurrentUserInfo() {
        return currentUserInfo;
    }

    public void setCurrentUserInfo(CurrentUserInfo currentUserInfo) {
        this.currentUserInfo = currentUserInfo;
    }

    public class CurrentUserInfo {

        private Long userId;
        private FavoriteUserPost favoriteUserPost;
        private boolean isOwner;

        public CurrentUserInfo() {}

        public CurrentUserInfo(Long userId, FavoriteUserPost favoriteUserPost) {
            this.userId = userId;
            this.favoriteUserPost = favoriteUserPost;
            this.isOwner = userId.equals(getAuthor().getId());
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public boolean isOwner() {
            return isOwner;
        }

        public void setOwner(boolean owner) {
            isOwner = owner;
        }


        public FavoriteUserPost getFavoriteUserPost() {
            return favoriteUserPost;
        }

        public void setFavoriteUserPost(FavoriteUserPost favoriteUserPost) {
            this.favoriteUserPost = favoriteUserPost;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("CurrentUserInfo{");
            sb.append("userId=").append(userId);
            sb.append(", favoriteUserPost=").append(favoriteUserPost);
            sb.append(", isOwner=").append(isOwner);
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

        PostVO vo = (PostVO) o;

        if (author != null ? !author.equals(vo.author) : vo.author != null) {
            return false;
        }
        if (post != null ? !post.equals(vo.post) : vo.post != null) {
            return false;
        }
        if (answers != null ? !answers.equals(vo.answers) : vo.answers != null) {
            return false;
        }
        if (tags != null ? !tags.equals(vo.tags) : vo.tags != null) {
            return false;
        }
        if (category != null ? !category.equals(vo.category) : vo.category != null) {
            return false;
        }
        if (info != null ? !info.equals(vo.info) : vo.info != null) {
            return false;
        }
        return currentUserInfo != null ? currentUserInfo.equals(vo.currentUserInfo) : vo.currentUserInfo == null;

    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (post != null ? post.hashCode() : 0);
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (currentUserInfo != null ? currentUserInfo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PostVO{");
        sb.append(super.toString()).append(" ");

        sb.append("author=").append(author);
        sb.append(", post=").append(post);
        sb.append(", answers=").append(answers);
        sb.append(", tags=").append(tags);
        sb.append(", category=").append(category);
        sb.append(", info=").append(info);
        sb.append(", currentUserInfo=").append(currentUserInfo);
        sb.append('}');
        return sb.toString();
    }


}