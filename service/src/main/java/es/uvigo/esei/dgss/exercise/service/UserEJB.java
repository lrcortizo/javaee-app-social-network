package es.uvigo.esei.dgss.exercise.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.dgss.exercises.domain.User;

@Stateless
public class UserEJB {
	
	@PersistenceContext
	private EntityManager em;
	
	public User createUser(User user){
		em.persist(user);
		return user;
	}
}