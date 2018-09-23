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

	public User addUser(String login, String name, String password, byte[] picture) {
		User user = new User(login);

		user.setName(name);
		user.setPassword(password);
		user.setPicture(picture);

		em.persist(user);

		return user;
	}

	public UserFriendship addFriendship(User user1, User user2, Date date) {
		UserFriendship friendship = new UserFriendship();

		friendship.setUser1(user1);
		friendship.setUser2(user2);
		friendship.setDate(date);

		em.persist(friendship);

		return friendship;
	}

	public List<User> getFriendships(User user) {
		addFriendship(user, new User(UUID.randomUUID().toString()), new Date());
		addFriendship(user, new User(UUID.randomUUID().toString()), new Date());
		addFriendship(user, new User(UUID.randomUUID().toString()), new Date());

		Query query = em.createQuery("SELECT u FROM UserFriendship u WHERE u.user1 in :us", UserFriendship.class)
				.setParameter("us", user);
		List<UserFriendship> friendships = (List<UserFriendship>) query.getResultList();
		List<User> toRet = new ArrayList<>();
		for (UserFriendship uf : friendships) {
			toRet.add(uf.getUser2());
		}
		return toRet;
	}

	public List<Post> getFriendsPosts(User user) {
		// Creating friends
		User prueba1 = new User(UUID.randomUUID().toString());
		addFriendship(user, prueba1, new Date());
		User prueba2 = new User(UUID.randomUUID().toString());
		addFriendship(user, prueba2, new Date());
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

		Query query = em.createQuery(
				"SELECT p FROM Post p WHERE EXISTS (SELECT u FROM UserFriendship u WHERE u.user1 in :us AND p.user = u.user2)",
				Post.class).setParameter("us", user);

		List<Post> posts = (List<Post>) query.getResultList();
		return posts;
	}

}
