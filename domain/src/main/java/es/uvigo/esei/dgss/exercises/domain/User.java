package es.uvigo.esei.dgss.exercises.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	private String login;
	
	private String name;
	private String password;
	private byte[] picture;
	
	@OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<UserFriendship> friends = new ArrayList<>();
	
	@OneToMany(mappedBy = "user2", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<UserFriendship> friendOf = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> posts = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private Collection<Like> likes = new ArrayList<>();

	public User() {
	}

	public User(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getPicture() {
		return picture;
	}
	
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Collection<UserFriendship> getFriends() {
		return friends;
	}

	public void setFriends(Collection<UserFriendship> friends) {
		this.friends = friends;
	}

	public Collection<UserFriendship> getFriendOf() {
		return friendOf;
	}

	public void setFriendOf(Collection<UserFriendship> friendOf) {
		this.friendOf = friendOf;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
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
	
	public void addLike(Like like){
		this.likes.add(like);
		like.setUser(this);
	}
}
