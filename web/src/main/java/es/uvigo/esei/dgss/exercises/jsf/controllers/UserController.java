package es.uvigo.esei.dgss.exercises.jsf.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import es.uvigo.esei.dgss.exercise.service.PostEJB;
import es.uvigo.esei.dgss.exercise.service.UserEJB;
import es.uvigo.esei.dgss.exercises.domain.Post;
import es.uvigo.esei.dgss.exercises.domain.User;

@Named(value="userController")
@SessionScoped
public class UserController implements Serializable {
	
	@EJB
	private UserEJB userEJB;
	
	@EJB
	private PostEJB postEJB;
	
	private Date date;
	private String filter;
	private User selectedUser;
	private List<Post> posts = new ArrayList<>();
	private List<User> matching = new ArrayList<>();
	

	public UserController() {
	}
	
	@PostConstruct
	public void initDate() {
	  date = Calendar.getInstance().getTime();
	}
	
	public void searchUsers() {
		this.matching = userEJB.searchUsers(this.filter);
		
	}
	
	public void showInfo(String login) {
		this.selectedUser = userEJB.findUserByIdNoAuth(login);
		this.posts = postEJB.getPostsOfUser(this.selectedUser);
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public List<User> getMatching() {
		return matching;
	}

	public void setMatching(List<User> matching) {
		this.matching = matching;
	}
	
	public boolean isNotEmpty(){
		return !this.matching.isEmpty();
	}
	
	public boolean userIsNotEmpty() {
		return !(this.selectedUser == null);
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

}
