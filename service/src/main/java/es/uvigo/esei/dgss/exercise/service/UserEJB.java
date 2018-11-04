package es.uvigo.esei.dgss.exercise.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uvigo.esei.dgss.exercises.domain.Like;
import es.uvigo.esei.dgss.exercises.domain.Post;
import es.uvigo.esei.dgss.exercises.domain.User;
import es.uvigo.esei.dgss.exercises.domain.UserFriendship;
import es.uvigo.esei.dgss.exercises.domain.UserFriendshipId;

@Stateless
public class UserEJB {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private StatisticsEJB statisticsEJB;
	
	@EJB
	private EmailServiceEJB emailEJB;
	
	@Resource
	private SessionContext ctx;
	
	public boolean isAuthenticated(String login) {
		if (ctx.getCallerPrincipal().getName().equals(login)) {
			return true;
		} else {
			throw new SecurityException("The authenticated user is not "+login);
		}
	}

	public User findUserById(String login) {
		User toRet = null;
		if (isAuthenticated(login)) {
			toRet = em.find(User.class, login);
			return toRet;
		} 
		return toRet;
	}
	
	public User findUserByIdNoAuth(String login) {
		User toRet = em.find(User.class, login);
		return toRet;
	}
	
	public List<User> getUsers(){
		List<User> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		return toRet;
		
	}
	
	public List<User> getFriends(User user){
		List<User> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT u FROM User u WHERE "
				+ "u in (SELECT uf.user1 FROM UserFriendship uf WHERE uf.user2 = :us) OR "
				+ "u in (SELECT uf.user2 FROM UserFriendship uf WHERE uf.user1 = :us)", User.class)
				.setParameter("us", user).getResultList();
		
		return toRet;
	}

	public User createUser(User user) {
		em.persist(user);
		statisticsEJB.incrementUserCount();
		return user;
	}

	public User updateUser(String login, String name, String password, byte[] picture, String email) {
		User user = findUserById(login);
		user.setName(name);
		user.setPassword(password);
		user.setPicture(picture);
		user.setEmail(email);
		em.persist(user);
		return user;
	}

	public void removeUser(User user) {
		em.remove(user);
		statisticsEJB.decrementUserCount();
	}
	
	public void deleteUser(String login) {
		User user = findUserById(login);
		em.remove(user);
		statisticsEJB.decrementUserCount();
	}
	
	public List<User> searchUsers(String filter) {
		List<User> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT u FROM User u WHERE u.login LIKE :f OR u.name LIKE :f OR u.role LIKE :f", User.class).setParameter("f", "%"+filter+"%").getResultList();
		return toRet;
	}

	public UserFriendship createFriendship(User user1, User user2) {
		UserFriendship uf = new UserFriendship();
		uf.setUser1(user1);
		uf.setUser2(user2);
		uf.setDate(new Date());
		uf.setAccepted(false);
		List<User> l = getFriends(user1);
		if(!l.contains(user2)){
			em.persist(uf);
		}
		
		return uf;
	}
	
	public void removeFriendship(UserFriendship uf) {
		em.remove(uf);
	}
	
	public Like addLike(Like like){
		User user = like.getUser();
		em.persist(like);
		
		String subject = "Like";
		String body = user.getName() + " likes your post ";
		
		emailEJB.sendEmail(like.getPost().getUser(), subject, body);
		
		return like;
	}
	
	public void removeLike(Like like){
		em.remove(like);
	}
	
	public List<User> getUsersFriendsOfUserWhoLikesPost(User user, Post post){
		Query query = em
				.createQuery("SELECT u FROM User u WHERE "
						+ "(u in (SELECT uf.user1 FROM UserFriendship uf WHERE uf.user2 = :us) OR "
						+ "u in (SELECT uf.user2 FROM UserFriendship uf WHERE uf.user1 = :us)) AND "
						+ "u in (SELECT l.user from Like l WHERE l.post = :pos)", User.class)
				.setParameter("us", user).setParameter("pos", post);
		
		List<User> toRet = (List<User>) query.getResultList();
		
		return toRet;
	}
	
	public List<User> getPotentialFriends(User user){
		List<User> toRet = new ArrayList<User>();
		
		//Get all users
		List <User> allUsers = getUsers();
		
		//Count common friends
		for (User u : allUsers) {
			if(user != u && !isFriend(user, u)) {
				if (getCommonFriends(user, u).size() >= 5) {
					toRet.add(u);
				}
			}
		}
		
		return toRet;
	}
	
	public boolean isFriend(User user1, User user2) {
		List<User> l = getFriends(user1);
		if(l.contains(user2)){
			return true;
		}
		return false;
	}
	
	public List<User> getCommonFriends(User user1, User user2) {
		List<User> friends1 = getFriends(user1);
		List<User> friends2 = getFriends(user2);
		
		List<User> common = new ArrayList<User>(friends1);
		common.retainAll(friends2);
		
		return common;
	}

	public UserFriendship requestFriendship(String login) {
		User user = findUserById(ctx.getCallerPrincipal().getName());
		User friend = em.find(User.class, login);
		
		return createFriendship(user, friend);
		
	}
	
	public List <UserFriendship> getFrienshipRequests() {
		User user = findUserById(ctx.getCallerPrincipal().getName());
		
		List<UserFriendship> toRet = new ArrayList<>();
		toRet = em.createQuery("SELECT uf FROM UserFriendship uf WHERE (user2 = :us OR user1 = :us) AND "
				+ "accepted = FALSE)", UserFriendship.class)
				.setParameter("us", user).getResultList();
		
		return toRet;
	}
	
	public UserFriendship acceptFriendship(String login) {
		User user = findUserById(ctx.getCallerPrincipal().getName());
		User friend = em.find(User.class, login);
		
		UserFriendship toRet = em.find(UserFriendship.class, new UserFriendshipId(user.getLogin(), friend.getLogin()));
		toRet.setAccepted(true);
		em.persist(toRet);
		
		return toRet;
	}
	
}