package es.uvigo.esei.dgss.exercises.domain;

import javax.persistence.Entity;

@Entity
public class Video extends Post {

	private String duration;
	
	public Video(){}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
