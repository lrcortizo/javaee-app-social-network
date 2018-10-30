package es.uvigo.esei.dgss.exercises.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uvigo.esei.dgss.exercises.domain.Comment;
import es.uvigo.esei.dgss.exercises.domain.Like;
import es.uvigo.esei.dgss.exercises.domain.Link;
import es.uvigo.esei.dgss.exercises.domain.Photo;
import es.uvigo.esei.dgss.exercises.domain.Post;
import es.uvigo.esei.dgss.exercises.domain.User;
import es.uvigo.esei.dgss.exercises.domain.UserFriendship;
import es.uvigo.esei.dgss.exercises.domain.Video;

@Dependent
public class Facade {

	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	// Exercise 1, Task 2.1
	public User addUser(String login, String name, String password, byte[] picture) {
		User user = new User(login);

		user.setName(name);
		user.setPassword(password);
		user.setPicture(picture);

		em.persist(user);

		return user;
	}

	// Exercise 1, Task 2.2
	public UserFriendship addFriendship(User user1, User user2) {
		UserFriendship friendship = new UserFriendship();

		friendship.setUser1(user1);
		friendship.setUser2(user2);
		friendship.setDate(new Date());

		em.persist(friendship);

		return friendship;
	}

	// Exercise 1, Task 2.3
	public List<User> getFriendships(User user) {
		addFriendship(user, new User(UUID.randomUUID().toString()));
		addFriendship(user, new User(UUID.randomUUID().toString()));
		addFriendship(user, new User(UUID.randomUUID().toString()));

		Query query = em.createQuery("SELECT u FROM User u WHERE "
				+ "u in (SELECT uf.user1 FROM UserFriendship uf WHERE uf.user2 = :us) OR "
				+ "u in (SELECT uf.user2 FROM UserFriendship uf WHERE uf.user1 = :us)", User.class)
				.setParameter("us", user);
		List<UserFriendship> friendships = (List<UserFriendship>) query.getResultList();
		List<User> toRet = new ArrayList<>();
		for (UserFriendship uf : friendships) {
			toRet.add(uf.getUser2());
		}
		return toRet;
	}

	// Exercise 1, Task 2.4
	public List<Post> getFriendsPosts(User user) {
		// Creating friends
		User prueba1 = new User(UUID.randomUUID().toString());
		addFriendship(user, prueba1);
		User prueba2 = new User(UUID.randomUUID().toString());
		addFriendship(user, prueba2);
		User prueba3 = new User(UUID.randomUUID().toString());
		em.persist(prueba3);

		// Creating posts
		Post post1 = new Link();
		post1.setUser(prueba1);
		Post post2 = new Video();
		post2.setUser(prueba1);
		Post post3 = new Photo();
		post3.setUser(prueba2);
		Post post4 = new Link();
		post4.setUser(prueba3);
		Post post5 = new Video();
		post5.setUser(prueba3);

		em.persist(post1);
		em.persist(post2);
		em.persist(post3);
		em.persist(post4);
		em.persist(post5);

		Query query = em
				.createQuery("SELECT p FROM Post p, UserFriendship uf WHERE "
						+ "(uf.user1 = :us AND p.user = uf.user2) OR "
						+ "uf.user2 = :us AND p.user = uf.user1)",
						Post.class)
				.setParameter("us", user);

		List<Post> posts = (List<Post>) query.getResultList();
		return posts;
	}

	// Exercise 1, Task 2.5
	// TODO
	public List<Post> getCommentedPostsByFriends(User user, Date date) {
		
		// Creating friends
		User prueba1 = new User(UUID.randomUUID().toString());
		addFriendship(user, prueba1);
		User prueba2 = new User(UUID.randomUUID().toString());
		addFriendship(user, prueba2);
		User prueba3 = new User(UUID.randomUUID().toString());
		em.persist(prueba3);
		
		//Creating comments
		Comment comment1 = new Comment();
		comment1.setComment("Comment 1");
		comment1.setUser(prueba1);
		comment1.setDate(new Date());
		em.persist(comment1);
		Comment comment2 = new Comment();
		comment2.setComment("Comment 2");
		comment2.setUser(prueba2);
		comment2.setDate(new Date());
		em.persist(comment2);
		Comment comment3 = new Comment();
		comment3.setComment("Comment 3");
		comment3.setUser(prueba3);
		comment3.setDate(new Date());
		em.persist(comment3);

		// Creating posts
		Post post1 = new Link();
		post1.setUser(user);
		post1.addComment(comment1);
		em.persist(post1);
		Post post2 = new Video();
		post2.setUser(user);
		post2.addComment(comment2);
		em.persist(post2);
		Post post3 = new Photo();
		post3.setUser(user);
		post3.addComment(comment1);
		post3.addComment(comment2);
		em.persist(post3);
		Post post4 = new Photo();
		post4.setUser(user);
		post4.addComment(comment3);
		em.persist(post4);
		Post post5 = new Video();
		post5.addComment(comment1);
		post5.addComment(comment3);
		post5.setUser(user);
		em.persist(post5);
		
		Query query = em
				.createQuery("SELECT p FROM Post p WHERE "
						+ "p in (SELECT c.post FROM Comment c WHERE "
						+ "(c.user in (SELECT uf.user1 FROM UserFriendship uf WHERE uf.user2 = :us) OR "
						+ "c.user in (SELECT uf.user2 FROM UserFriendship uf WHERE uf.user1 = :us)) AND "
						+ "c.date > :dat)",
						Post.class)
				.setParameter("us", user).setParameter("dat", date);
		
		List<Post> posts = (List<Post>) query.getResultList();
		
		return posts;
	}

	// Exercise 1, Task 2.6
	// TODO
	public List<User> unnamed(User user, Post post) {
		
		// Creating friends
				User prueba1 = new User(UUID.randomUUID().toString());
				addFriendship(user, prueba1);
				User prueba2 = new User(UUID.randomUUID().toString());
				addFriendship(user, prueba2);
				User prueba3 = new User(UUID.randomUUID().toString());
				em.persist(prueba3);
				User prueba4 = new User(UUID.randomUUID().toString());
				addFriendship(user, prueba4);
				

				// Creating likes
				Like like1 = new Like();
				like1.setUser(prueba1);
				like1.setPost(post);
				em.persist(like1);
				
				Like like2 = new Like();
				like2.setUser(prueba2);
				like2.setPost(post);
				em.persist(like2);
				
				Like like3 = new Like();
				like3.setUser(prueba3);
				like3.setPost(post);
				em.persist(like3);
		
				Query query = em
						.createQuery("SELECT u FROM User u WHERE "
								+ "(u in (SELECT uf.user1 FROM UserFriendship uf WHERE uf.user2 = :us) OR "
								+ "u in (SELECT uf.user2 FROM UserFriendship uf WHERE uf.user1 = :us)) AND "
								+ "u in (SELECT l.user from Like l WHERE l.post = :pos)", User.class)
						.setParameter("us", user).setParameter("pos", post);
				
				List<User> toRet = (List<User>) query.getResultList();
				
		return toRet;
	}

	// Exercise 1, Task 2.7
	// TODO
	public List<Post> getPicturesUserLikes(User user) {
		
		//Creating Photos
		Post photo1 = new Photo();
		em.persist(photo1);
		Post photo2 = new Photo();
		em.persist(photo2);
		Post photo3 = new Photo();
		em.persist(photo3);
		
		Like like1 = new Like();
		like1.setUser(user);
		like1.setPost(photo1);
		em.persist(like1);
		
		Like like2 = new Like();
		like2.setUser(user);
		like2.setPost(photo2);
		em.persist(like2);
		
		//Creating Likes
		
		
		Query query = em
				.createQuery("SELECT p FROM Post p WHERE "
						+ "p in (SELECT l.post FROM Like l WHERE l.user = :us)", Post.class)
				.setParameter("us", user);
		
		List<Post> toRet = (List<Post>) query.getResultList();
		
		return toRet;
	}

	// Exercise 1, Task 2.8
	// TODO
	public List<User> getPotentialFriends(User user) {
		List<User> toRet = new ArrayList<>();
		return toRet;
	}

}
