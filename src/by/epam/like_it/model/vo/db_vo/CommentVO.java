package by.epam.like_it.model.vo.db_vo;

import by.epam.like_it.model.bean.Comment;
import by.epam.like_it.model.bean.User;
import by.epam.like_it.model.vo.page_vo.TimeDifferenceVO;

public class CommentVO implements EntityVO {

    private User author;
    private Comment comment;
    private TimeDifferenceVO updateDifference;



    public CommentVO() {

    }

    public CommentVO(User author, Comment comment) {
        this.author = author;
        this.comment = comment;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public TimeDifferenceVO getUpdateDifference() {
        return updateDifference;
    }

    public void setUpdateDifference(TimeDifferenceVO updateDifference) {
        this.updateDifference = updateDifference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentVO commentVO = (CommentVO) o;

        //noinspection SimplifiableIfStatement
        if (author != null ? !author.equals(commentVO.author) : commentVO.author != null) {
            return false;
        }
        return comment != null ? comment.equals(commentVO.comment) : commentVO.comment == null;

    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommentVO{");
        sb.append(super.toString()).append(" ");

        sb.append("author=").append(author);
        sb.append(", comment=").append(comment);
        sb.append('}');
        return sb.toString();
    }

}