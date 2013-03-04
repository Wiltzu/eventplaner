package foo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Activity {

	@GeneratedValue(strategy=GenerationType.TABLE)
	@Id
	private int id;
	private String name;
	@OneToOne
	private User creator;
	
	//For JPA
	public Activity(){}
	
	public Activity(String name, User creator) {
		super();
		this.name = name;
		this.creator = creator;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
	
}
