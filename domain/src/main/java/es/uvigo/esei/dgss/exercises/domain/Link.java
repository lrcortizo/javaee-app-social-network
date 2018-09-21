package es.uvigo.esei.dgss.exercises.domain;

import javax.persistence.Entity;

@Entity
public class Link extends Post {

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
