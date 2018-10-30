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

import es.uvigo.esei.dgss.exercises.domain.Like;
import es.uvigo.esei.dgss.exercises.domain.User;
import es.uvigo.esei.dgss.exercises.domain.UserFriendship;

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
		List<User> l = getFriends(user1);
		if(!l.contains(user2)){
			em.persist(uf);
		}
		
		return uf;
	}
	
	public void removeFriendship(UserFriendship uf) {
		em.remove(uf);
	}
	
	public Like addLike(User user, Like like){
		user.addLike(like);
		em.persist(user);
		
		String subject = "Like";
		String body = user.getName() + " likes your post ";
		
		emailEJB.sendEmail(like.getPost().getUser(), subject, body);
		
		return like;
	}
	
	public void removeLike(Like like){
		em.remove(like);
	}

}