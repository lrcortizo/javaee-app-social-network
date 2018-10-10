package es.uvigo.esei.dgss.exercise.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.dgss.exercises.domain.Comment;
import es.uvigo.esei.dgss.exercises.domain.Link;
import es.uvigo.esei.dgss.exercises.domain.Photo;
import es.uvigo.esei.dgss.exercises.domain.Post;
import es.uvigo.esei.dgss.exercises.domain.User;
import es.uvigo.esei.dgss.exercises.domain.Video;

@Stateless
public class PostEJB {
	
	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private StatisticsEJB statisticsEJB;
	
	public Post findPostById(int id) {
		return em.find(Post.class, id);
	}
	
	public Comment findCommentById(int id) {
		return em.find(Comment.class, id);
	}
	
	public List<Post> getPosts() {
		List<Post> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
		return toRet;
	} 
	
	public List<Post> getPostsOfUser(User user) {
		List<Post> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT p FROM Post p WHERE p.user = :us", Post.class).setParameter("us", user).getResultList();
		return toRet;
	}
	
	public List<Video> getVideos() {
		List<Video> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT p FROM Post p", Video.class).getResultList();
		return toRet;
	} 
	
	public List<Video> getVideosOfUser(User user) {
		List<Video> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT p FROM Post p WHERE p.user = :us", Video.class).setParameter("us", user).getResultList();
		return toRet;
	}
	
	public List<Photo> getPhotos() {
		List<Photo> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT p FROM Post p", Photo.class).getResultList();
		return toRet;
	} 
	
	public List<Photo> getPhotosOfUser(User user) {
		List<Photo> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT p FROM Post p WHERE p.user = :us", Photo.class).setParameter("us", user).getResultList();
		return toRet;
	}
	
	public List<Link> getLinks() {
		List<Link> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT p FROM Post p", Link.class).getResultList();
		return toRet;
	} 
	
	public List<Link> getLinksOfUser(User user) {
		List<Link> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT p FROM Post p WHERE p.user = :us", Link.class).setParameter("us", user).getResultList();
		return toRet;
	}
	
	public Video createVideo(Video video){
		em.persist(video);
		statisticsEJB.incrementPostCount();
		return video;
	}
	
	public Photo createPhoto(Photo photo){
		em.persist(photo);
		statisticsEJB.incrementPostCount();
		return photo;
	}
	
	public Link createLink(Link link){
		em.persist(link);
		statisticsEJB.incrementPostCount();
		return link;
	}
	
	public Video updateVideo(int id, Integer duration){
		Video video = (Video) findPostById(id);
		video.setDuration(duration);
		video.setDate(new Date());
		em.persist(video);
		return video;
	}
	
	public Photo updatePhoto(int id, Byte [] content){
		Photo photo = (Photo) findPostById(id);
		photo.setContent(content);
		photo.setDate(new Date());
		em.persist(photo);
		return photo;
	}
	
	public Link updateLink(int id, String url){
		Link link = (Link) findPostById(id);
		link.setUrl(url);
		link.setDate(new Date());
		em.persist(link);
		return link;
	}
	
	public void removePost(Post post){
		em.remove(post);
		statisticsEJB.decrementPostCount();
	}
	
	public Comment addComment(Post post, Comment comment){
		post.addComment(comment);
		em.persist(post);
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

