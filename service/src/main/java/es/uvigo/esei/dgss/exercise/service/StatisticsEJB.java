package es.uvigo.esei.dgss.exercise.service;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
@Lock(LockType.WRITE)
public class StatisticsEJB {
	private int user_count;
	private int post_count;

	@PostConstruct
	void init() {
		user_count = 0;
		post_count = 0;
	}
	
	public void incrementUserCount(){
		this.user_count+=1;
	}
	
	public void incrementPostCount(){
		this.post_count+=1;
	}
	
	@Lock(LockType.READ)
	public int getUserCount() {
		return user_count;
	}

	public void setUserCount(int user_count) {
		this.user_count = user_count;
	}
	
	@Lock(LockType.READ)
	public int getPostCount() {
		return post_count;
	}

	public void setPostCount(int post_count) {
		this.post_count = post_count;
	}
}
