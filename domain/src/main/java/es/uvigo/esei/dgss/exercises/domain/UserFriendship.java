package es.uvigo.esei.dgss.exercises.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@IdClass(UserFriendshipId.class)
public class UserFriendship {
	@Id
	@ManyToOne
	@JsonBackReference(value="user1")
	private User user1;

	@Id
	@ManyToOne
	@JsonBackReference(value="user2")
	private User user2;

	private Date date;
	
	private boolean accepted;

	public UserFriendship() {
	}

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	public UserFriendshipDTO toDTO() {
		UserFriendshipDTO dto = new UserFriendshipDTO();
		dto.setDate(this.date);
		dto.setUser1(this.user1);
		dto.setUser2(this.user2);
		dto.setAccepted(this.accepted);
		
		return dto;
	}

}
