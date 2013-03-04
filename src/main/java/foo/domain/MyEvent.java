package foo.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class MyEvent {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;
	private String name;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_EVENT", joinColumns = { @JoinColumn(name = "EVENT_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") })
	private Set<User> partisipants;
	@OneToMany
	private Set<Activity> activities;

	public MyEvent() {
		partisipants = new HashSet<User>();
	}

	public MyEvent(String name) {
		super();
		this.name = name;
		this.partisipants = new HashSet<User>();
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

	public Set<User> getPartisipants() {
		return partisipants;
	}

	public void setPartisipants(Set<User> partisipants) {
		this.partisipants = partisipants;
	}

	public void addPartisipant(User user) {
		this.partisipants.add(user);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((partisipants == null) ? 0 : partisipants.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyEvent other = (MyEvent) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (partisipants == null) {
			if (other.partisipants != null)
				return false;
		} else if (!partisipants.equals(other.partisipants))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", partisipants="
				+ partisipants + "]";
	}

}
