package es.uvigo.esei.dgss.exercise.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.dgss.exercises.domain.Post;

@Stateless
public class PostEJB {
	
	@PersistenceContext
	private EntityManager em;
	
	public Post createPost(Post post){
		em.persist(post);
		return post;
	}
}

