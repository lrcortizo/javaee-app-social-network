package es.uvigo.esei.dgss.exercise.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.dgss.exercises.domain.Like;
import es.uvigo.esei.dgss.exercises.domain.User;
import es.uvigo.esei.dgss.exercises.domain.UserFriendship;

@Stateless
public class UserEJB {

	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private SessionContext ctx;

	public User findUserById(String login) {
		if (ctx.getCallerPrincipal().getName().equals(login)) {
			return em.find(User.class, login);
		} else {
			throw new SecurityException("The authenticated user is not "+login);
		}
	}
	
	public List<User> getUsers(){
		List<User> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		return toRet;
		
	}
	
	public List<User> getFriends(User user){
		List<User> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT u.user2 FROM UserFriendship u WHERE u.user1 in :us", User.class)
				.setParameter("us", user).getResultList();
		
		return toRet;
	}

	public User createUser(User user) {
		em.persist(user);
		return user;
	}

	public User updateUser(String login, String name, String password, byte[] picture) {
		User user = findUserById(login);
		user.setName(name);
		user.setPassword(password);
		user.setPicture(picture);
		em.persist(user);
		return user;
	}

	public void removeUser(User user) {
		em.remove(user);
	}

	public UserFriendship createFriendship(User user1, User user2) {
		UserFriendship uf = new UserFriendship();
		uf.setUser1(user1);
		uf.setUser2(user2);
		uf.setDate(new Date());
		em.persist(uf);
		return uf;
	}
	
	public void removeFriendship(UserFriendship uf) {
		em.remove(uf);
	}
	
	public Like addLike(User user, Like like){
		user.addLike(like);
		em.persist(user);
		return like;
	}
	
	public void removeLike(Like like){
		em.remove(like);
	}

}