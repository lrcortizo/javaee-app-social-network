package es.uvigo.esei.dgss.exercises.domain;

import javax.persistence.Entity;

@Entity
public class Video extends Post {

	private Integer duration;
	
	public Video(){}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}
