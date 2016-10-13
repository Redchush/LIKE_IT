package by.epam.like_it.model.vo.db_vo;

import by.epam.like_it.model.bean.Answer;
import by.epam.like_it.model.bean.Rating;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.page_vo.TimeDifferenceVO;


public class RatingVO implements EntityVO {

    private Answer parent;
    private User author;
    private Rating rating;
    private TimeDifferenceVO updateDifference;

    public RatingVO() {}

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Answer getParent() {
        return parent;
    }

    public void setParent(Answer parent) {
        this.parent = parent;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RatingVO ratingVO = (RatingVO) o;

        if (parent != null ? !parent.equals(ratingVO.parent) : ratingVO.parent != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (author != null ? !author.equals(ratingVO.author) : ratingVO.author != null) {
            return false;
        }
        return rating != null ? rating.equals(ratingVO.rating) : ratingVO.rating == null;

    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RatingVO{");
        sb.append(super.toString()).append(" ");

        sb.append("parent=").append(parent);
        sb.append(", author=").append(author);
        sb.append(", rating=").append(rating);
        sb.append('}');
        return sb.toString();
    }


}
