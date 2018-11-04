package es.uvigo.esei.dgss.exercises.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public abstract class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private Date date;

	@ManyToOne
	@JsonBackReference(value="user-post")
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();
	
	@OneToMany(mappedBy="post")
	private Collection<Like> likes = new ArrayList<>();
	
	public Post() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Collection<Like> getLikes() {
		return likes;
	}

	public void setLikes(Collection<Like> likes) {
		this.likes = likes;
	}
	
	public void addComment(Comment comment){
		this.comments.add(comment);
		comment.setPost(this);
	}
	
}
