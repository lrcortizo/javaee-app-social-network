package es.uvigo.esei.dgss.exercises.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public abstract class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private Date date;

	@ManyToOne
	@JsonBackReference(value="user-post")
	private User user;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference(value="post-comment")
	private List<Comment> comments = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="post")
	@JsonManagedReference(value="post-like")
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
	
	public PostDTO toDTO() {
		
		PostDTO dto = new PostDTO();
		
		dto.setComments(this.comments);
		dto.setDate(this.date);
		dto.setId(this.id);
		dto.setLikes(this.likes);
		dto.setUser(this.user);
		
		return dto;
	}
}
