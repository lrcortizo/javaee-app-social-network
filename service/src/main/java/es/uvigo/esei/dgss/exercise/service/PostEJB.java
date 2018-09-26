package es.uvigo.esei.dgss.exercise.service;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.dgss.exercises.domain.Comment;
import es.uvigo.esei.dgss.exercises.domain.Post;
import es.uvigo.esei.dgss.exercises.domain.User;

@Stateless
public class PostEJB {
	
	@PersistenceContext
	private EntityManager em;
	
	public Post findPostById(int id) {
		return em.find(Post.class, id);
	}
	
	public Post createPost(Post post){
		em.persist(post);
		return post;
	}
	
	//TODO video, photo, link
	public Post updatePost(int id, Date date){
		Post post = findPostById(id);
		post.setDate(date);
		em.persist(post);
		return post;
	}
	
	public void removePost(Post Post){
		em.remove(Post);
	}
	
	public Comment addComment(User user, Post post, String text){
		Comment comment = new Comment();
		comment.setUser(user);
		comment.setPost(post);
		comment.setComment(text);
		comment.setDate(new Date());
		em.persist(comment);
		return comment;
	}
}

