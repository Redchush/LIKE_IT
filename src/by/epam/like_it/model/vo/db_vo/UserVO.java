package by.epam.like_it.model.vo.db_vo;

import by.epam.like_it.model.bean.*;
import by.epam.like_it.model.vo.page_vo.TimeDifferenceVO;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserVO implements EntityVO {

	private TimeDifferenceVO updateDifference;
	@NotNull
	private List<Tag> favoriteTags;

//	@NotNull
//	private List<Post> publishedPosts;
//
//	@NotNull
//	private List<Post> readedPosts;
//
//	@NotNull
//	private List<FavoriteUserPost> favoritePosts;
//
//	@NotNull
//	private List<Answer> answers;

	private User user;

	private Info info;

	public UserVO() {
		favoriteTags = new ArrayList<>();
//		readedPosts = new ArrayList<>();
//		favoritePosts = new ArrayList<>();
//		publishedPosts = new ArrayList<>();
//		answers = new ArrayList<>();
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	@NotNull
	public List<Tag> getFavoriteTags() {
		return favoriteTags;
	}

	public void setFavoriteTags(@NotNull List<Tag> favoriteTags) {
		this.favoriteTags = favoriteTags;
	}

	public static class Info{
		private Long answersCount;
		private Long postsCount;
		private Double avgRating;
		private Long totalRating;

		public Info() {}

		public Info(Long answersCount, Long postsCount, Double avgRating, Long totalRating) {
			this.answersCount = answersCount;
			this.postsCount = postsCount;
			this.avgRating = avgRating;
			this.totalRating = totalRating;
		}

		public Long getAnswersCount() {
			return answersCount;
		}

		public void setAnswersCount(Long answersCount) {
			this.answersCount = answersCount;
		}

		public Long getPostsCount() {
			return postsCount;
		}

		public void setPostsCount(Long postsCount) {
			this.postsCount = postsCount;
		}

		public Double getAvgRating() {
			return avgRating;
		}

		public void setAvgRating(Double avgRating) {
			this.avgRating = avgRating;
		}

		public Long getTotalRating() {
			return totalRating;
		}

		public void setTotalRating(Long totalRating) {
			this.totalRating = totalRating;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UserVO{");
		sb.append(super.toString()).append(" ");

		sb.append("updateDifference=").append(updateDifference);
		sb.append(", favoriteTags=").append(favoriteTags);
		sb.append(", user=").append(user);
		sb.append(", info=").append(info);
		sb.append('}');
		return sb.toString();
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		UserVO userVO = (UserVO) o;

		if (updateDifference != null ? !updateDifference.equals(userVO.updateDifference)
									 : userVO.updateDifference != null) {
			return false;
		}
		if (!favoriteTags.equals(userVO.favoriteTags)) {
			return false;
		}
        //noinspection SimplifiableIfStatement
        if (user != null ? !user.equals(userVO.user) : userVO.user != null) {
			return false;
		}
		return info != null ? info.equals(userVO.info) : userVO.info == null;

	}

	@Override
	public int hashCode() {
		int result = updateDifference != null ? updateDifference.hashCode() : 0;
		result = 31 * result + favoriteTags.hashCode();
		result = 31 * result + (user != null ? user.hashCode() : 0);
		result = 31 * result + (info != null ? info.hashCode() : 0);
		return result;
	}
}