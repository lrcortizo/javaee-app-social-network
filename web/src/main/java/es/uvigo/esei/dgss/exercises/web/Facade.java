package es.uvigo.esei.dgss.exercises.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uvigo.esei.dgss.exercises.domain.User;
import es.uvigo.esei.dgss.exercises.domain.UserFriendship;

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
    
    public UserFriendship addFriendship(User user1, User user2, Date date){
    	UserFriendship friendship = new UserFriendship();
    	
    	friendship.setUser1(user1);
    	friendship.setUser2(user2);
    	friendship.setDate(date);
    	
    	em.persist(friendship);
    	
    	return friendship;
    }
    
    public List<User> getFriendships(User user){
    	addFriendship(user, new User(UUID.randomUUID().toString()), new Date());
    	addFriendship(user, new User(UUID.randomUUID().toString()), new Date());
    	addFriendship(user, new User(UUID.randomUUID().toString()), new Date());
    	
    	Query query = em.createQuery("SELECT u FROM UserFriendship u WHERE u.user1 in :us", UserFriendship.class).setParameter("us", user);
    	List<UserFriendship> friendships = (List<UserFriendship>)query.getResultList();
    	List<User> toRet = new ArrayList<>();
    	for(UserFriendship uf : friendships){
    		toRet.add(uf.getUser2());
    	}
    	return toRet;
    }
}
