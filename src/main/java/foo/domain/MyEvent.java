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
import javax.persistence.OneToOne;

@Entity
public class MyEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_EVENT", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "EVENT_ID", referencedColumnName = "ID") })
	private Set<User> partisipants;
	// default = lazy
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Activity> activities;
	@OneToOne
	private User creator;

	// for JPA
	public MyEvent() {
		initLists();
	}

	public MyEvent(String name, String description, User creator) {
		super();
		this.name = name;
		this.description = description;
		this.creator = creator;
		initLists();
	}

	public Set<Activity> getActivities() {
		return activities;
	}

	public User getCreator() {
		return creator;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activities == null) ? 0 : activities.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
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
		if (activities == null) {
			if (other.activities != null)
				return false;
		} else if (!activities.equals(other.activities))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
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

	private void initLists() {
		partisipants = new HashSet<User>();
		activities = new HashSet<Activity>();
	}

}
