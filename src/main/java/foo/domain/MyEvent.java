package foo.domain;

import java.io.Serializable;
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

/**
 * <p>
 * This class represents events in this application.
 * </p>
 * 
 * @author Ville Ahti
 * 
 */
@Entity
public class MyEvent implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_EVENT", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "EVENT_ID", referencedColumnName = "ID") })
	private Set<User> partisipants;
	// default = lazy
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "event")
	private Set<Activity> activities;
	@OneToOne
	private User creator;

	/**
	 * <p>
	 * Default constructor for JPA only.
	 * <p>
	 */
	public MyEvent() {
		initSets();
	}

	/**
	 * <p>
	 * Main contstructor for creating new instances of this class.
	 * </p>
	 * 
	 * @param name
	 *            of the event
	 * @param description
	 *            of the event
	 * @param creator
	 *            of the event
	 */
	public MyEvent(String name, String description, User creator) {
		super();
		this.name = name;
		this.description = description;
		this.creator = creator;
		initSets();
		addPartisipant(creator);
	}

	/**
	 * <p>
	 * Getter for event's activities
	 * </p>
	 * 
	 * @return event's activities
	 */
	public Set<Activity> getActivities() {
		return activities;
	}

	/**
	 * <p>
	 * Setter for event's activities.
	 * </p>
	 * 
	 * @param activities
	 *            for this event.
	 */
	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	/**
	 * <p>
	 * Getter for event's creator
	 * </p>
	 * 
	 * @return creator of this event
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * <p>
	 * Setter for event's creator.
	 * </p>
	 * 
	 * @param creator
	 *            of this event
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * <p>
	 * Getter for event's id.
	 * </p>
	 * 
	 * @return event's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * <p>
	 * Setter for event's id. only for JPA.
	 * </p>
	 * 
	 * @param id
	 *            of this event
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Getter for event's name.
	 * </p>
	 * 
	 * @return event's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>
	 * Setter for event's
	 * </p>
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>
	 * Getter for event's description.
	 * </p>
	 * 
	 * @return event's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <p>
	 * Setter for event's description
	 * </p>
	 * 
	 * @param description
	 *            of this event
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * <p>
	 * Getter for event's partisipants.
	 * </p>
	 * 
	 * @return set of partisipants
	 */
	public Set<User> getPartisipants() {
		return partisipants;
	}

	/**
	 * <p>
	 * Setter for event's partisipants
	 * </p>
	 * 
	 * @param partisipants
	 *            to this event
	 */
	public void setPartisipants(Set<User> partisipants) {
		this.partisipants = partisipants;
	}

	/**
	 * <p>
	 * Adds partisipant to this event.
	 * </p>
	 * 
	 * @param partisipant
	 *            new partisipant to this event
	 */
	public void addPartisipant(User partisipant) {
		this.partisipants.add(partisipant);
	}

	/**
	 * <p>
	 * Adds new activity to this event.
	 * </p>
	 * 
	 * @param activity
	 *            new activity to this event
	 */
	public void addActivity(Activity activity) {
		activity.setEvent(this);
		this.activities.add(activity);
	}

	/**
	 * <p>
	 * Adds set of activities to this event.
	 * </p>
	 * 
	 * @param activities
	 *            set of activities
	 */
	public void addSetOfActivities(Set<Activity> activities) {
		for (Activity activity : activities) {
			addActivity(activity);
		}
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

	/**
	 * <p>
	 * inits sets of this class.
	 * </p>
	 */
	private void initSets() {
		partisipants = new HashSet<User>();
		activities = new HashSet<Activity>();
	}

}
