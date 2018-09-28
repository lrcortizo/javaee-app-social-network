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
	
	public Comment findCommentById(int id) {
		return em.find(Comment.class, id);
	}
	
	//TODO video, photo, link
	public Post createPost(Post post){
		em.persist(post);
		return post;
	}
	
	public Post updatePost(int id){
		Post post = findPostById(id);
		post.setDate(new Date());
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
	
	public Comment updateComment(int id, String text){
		Comment comment = findCommentById(id);
		comment.setComment(text);
		comment.setDate(new Date());
		em.persist(comment);
		return comment;
	}
	
	public void removeComment(Comment comment){
		em.remove(comment);
	}
}

