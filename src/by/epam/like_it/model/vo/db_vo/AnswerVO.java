package by.epam.like_it.model.vo.db_vo;

import by.epam.like_it.model.bean.Answer;
import by.epam.like_it.model.bean.Rating;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.page_vo.TimeDifferenceVO;

import java.util.List;


public class AnswerVO implements EntityVO {

    private Answer answer;
    private User author;

    private List<CommentVO> comments;
    private TimeDifferenceVO updateDifference;

    private Double avgRate;
    private Long totalRate;
    private Long countRatings;

    private CurrentUserInfo currentUserInfo;

    public AnswerVO() {
        currentUserInfo = new CurrentUserInfo();
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<CommentVO> getComments() {
        return comments;
    }

    public void setComments(List<CommentVO> comments) {
        this.comments = comments;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
    }

    public Long getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(Long totalRate) {
        this.totalRate = totalRate;
    }

    public Long getCountRatings() {
        return countRatings;
    }

    public void setCountRatings(Long countRatings) {
        this.countRatings = countRatings;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public TimeDifferenceVO getUpdateDifference() {
        return updateDifference;
    }

    public void setUpdateDifference(TimeDifferenceVO updateDifference) {
        this.updateDifference = updateDifference;
    }

    public CurrentUserInfo getCurrentUserInfo() {
        return currentUserInfo;
    }

    public void setCurrentUserInfo(CurrentUserInfo currentUserInfo) {
        this.currentUserInfo = currentUserInfo;
    }

    public class CurrentUserInfo {

        private Long userId;
        private Rating rating;
        private boolean isOwner;

        public CurrentUserInfo() {}

        public CurrentUserInfo(Long userId, Rating rating) {
            this.userId = userId;
            this.rating = rating;
            //noinspection SimplifiableIfStatement
            if (author != null){
                isOwner = userId.equals(author.getId());
            } else {
                isOwner = false;
            }

        }

        public Rating getRating() {
            return rating;
        }

        public void setRating(Rating rating) {
            this.rating = rating;
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

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("CurrentUserInfo{");

            sb.append("userId=").append(userId);
            sb.append(", rating=").append(rating);
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

        AnswerVO vo = (AnswerVO) o;

        if (author != null ? !author.equals(vo.author) : vo.author != null) {
            return false;
        }
        if (answer != null ? !answer.equals(vo.answer) : vo.answer != null) {
            return false;
        }
        if (comments != null ? !comments.equals(vo.comments) : vo.comments != null) {
            return false;
        }
        if (avgRate != null ? !avgRate.equals(vo.avgRate) : vo.avgRate != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (totalRate != null ? !totalRate.equals(vo.totalRate) : vo.totalRate != null) {
            return false;
        }
        return countRatings != null ? countRatings.equals(vo.countRatings) : vo.countRatings == null;

    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (avgRate != null ? avgRate.hashCode() : 0);
        result = 31 * result + (totalRate != null ? totalRate.hashCode() : 0);
        result = 31 * result + (countRatings != null ? countRatings.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnswerVO{");

        sb.append("answer=").append(answer);
        sb.append(", author=").append(author);
        sb.append(", comments=").append(comments);
        sb.append(", updateDifference=").append(updateDifference);
        sb.append(", avgRate=").append(avgRate);
        sb.append(", totalRate=").append(totalRate);
        sb.append(", countRatings=").append(countRatings);
        sb.append(", currentUserInfo=").append(currentUserInfo);
        sb.append('}');
        return sb.toString();
    }


}
