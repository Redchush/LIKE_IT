package by.epam.like_it.model.bean;

import by.epam.like_it.model.bean.util_interface.DeletableByBan;
import by.epam.like_it.model.bean.util_interface.TimeDependent;

import java.sql.Timestamp;

public class Rating implements DeletableByBan, TimeDependent {

    private Long id;
    private Long userId;
    private Long answerId;
    private Byte rating;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Boolean banned;

    public Rating() {
    }

    public Rating(Long id, Long userId, Long answerId, Byte rating, Timestamp createdDate, Timestamp updatedDate, Boolean banned) {
        this.id = id;
        this.userId = userId;
        this.answerId = answerId;
        this.rating = rating;
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

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
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

        Rating rating1 = (Rating) o;

        if (id != null ? !id.equals(rating1.id) : rating1.id != null) {
            return false;
        }
        if (userId != null ? !userId.equals(rating1.userId) : rating1.userId != null) {
            return false;
        }
        if (answerId != null ? !answerId.equals(rating1.answerId) : rating1.answerId != null) {
            return false;
        }
        if (rating != null ? !rating.equals(rating1.rating) : rating1.rating != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(rating1.createdDate) : rating1.createdDate != null) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (updatedDate != null ? !updatedDate.equals(rating1.updatedDate) : rating1.updatedDate != null) {
            return false;
        }
        return banned != null ? banned.equals(rating1.banned) : rating1.banned == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (answerId != null ? answerId.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (banned != null ? banned.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rating{");

        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", answerId=").append(answerId);
        sb.append(", rating=").append(rating);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", updatedDate=").append(updatedDate);
        sb.append(", banned=").append(banned);
        sb.append('}');
        return sb.toString();
    }


    @Override
    public void initRequiredDefault() {
        if (createdDate == null){
            this.createdDate = getDefaultCreatedDate();
        }
        if (banned == null){
            this.banned = getDefaultCreateBan();
        }
    }

    @Override
    public void initAllDefault() {
        initRequiredDefault();
        if (updatedDate == null){
            this.updatedDate = getDefaultUpdatedDate();
        }
    }
}