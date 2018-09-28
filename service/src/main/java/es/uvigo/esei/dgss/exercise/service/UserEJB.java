package es.uvigo.esei.dgss.exercise.service;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.dgss.exercises.domain.User;
import es.uvigo.esei.dgss.exercises.domain.UserFriendship;

@Stateless
public class UserEJB {

	@PersistenceContext
	private EntityManager em;

	public User findUserById(String login) {
		return em.find(User.class, login);
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

}