package es.uvigo.esei.dgss.exercises.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

		Query query = em.createQuery("SELECT u FROM UserFriendship u WHERE u.user1 in :us", UserFriendship.class)
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
				.createQuery("SELECT p FROM Post p, UserFriendship uf WHERE uf.user1 = :us AND p.user = uf.user2)",
						Post.class)
				.setParameter("us", user);

		List<Post> posts = (List<Post>) query.getResultList();
		return posts;
	}

	// Exercise 1, Task 2.5
	// TODO
	public List<Post> getCommentedPostsByFriends(User user, Date date) {
		List<Post> posts = new ArrayList<>();
		return posts;
	}

	// Exercise 1, Task 2.6
	// TODO
	public List<User> Unnamed(User user, Post post) {
		List<User> toRet = new ArrayList<>();
		return toRet;
	}

	// Exercise 1, Task 2.7
	// TODO
	public List<Photo> getPicturesUserLikes(User user) {
		List<Photo> toRet = new ArrayList<>();
		return toRet;
	}

	// Exercise 1, Task 2.8
	// TODO
	public List<User> getPotentialFriends(User user) {
		List<User> toRet = new ArrayList<>();
		return toRet;
	}

}
