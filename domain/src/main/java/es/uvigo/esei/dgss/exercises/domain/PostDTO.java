package es.uvigo.esei.dgss.exercises.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class PostDTO {

	private int id;
	
	private Date date;

	private User user;
	
	private List<Comment> comments = new ArrayList<>();
	
	private Collection<Like> likes = new ArrayList<>();
	
	public PostDTO() {}

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
	
}
