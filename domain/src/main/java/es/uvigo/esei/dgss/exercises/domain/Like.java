package es.uvigo.esei.dgss.exercises.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@IdClass(LikeId.class)
@Table(name="UserLikesPost")
public class Like {

	@Id
	@ManyToOne
	private Post post;

	@Id
	@ManyToOne
	@JsonBackReference(value="user-like")
	private User user;

	public Like() {
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
