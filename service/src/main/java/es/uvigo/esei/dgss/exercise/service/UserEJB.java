package es.uvigo.esei.dgss.exercise.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.dgss.exercises.domain.User;

@Stateless
public class UserEJB {
	
	@PersistenceContext
	private EntityManager em;
	
	public User findUserById(String login) {
		return em.find(User.class, login);
	}
	
	public User createUser(User user){
		em.persist(user);
		return user;
	}
	
	public User updateUser(String login, String name, String password, byte[] picture){
		User user = findUserById(login);
		user.setName(name);
		user.setPassword(password);
		user.setPicture(picture);
		em.persist(user);
		return user;
	}
	
	public void removeUser(User user){
		em.remove(user);
	}
	
}