package es.uvigo.esei.dgss.exercises.domain;

import javax.persistence.Entity;

@Entity
public class Photo extends Post {

	private Byte[] content;

	public Byte[] getContent() {
		return content;
	}

	public void setContent(Byte[] content) {
		this.content = content;
	}

}
