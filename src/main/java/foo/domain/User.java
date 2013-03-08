package foo.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * <p>
 * This class represents user of this application.
 * </p>
 * 
 * @author Ville Ahti
 * 
 */
@Entity
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// @Column(unique = true)
	private String name;
	private String password;
	@ManyToMany(mappedBy = "partisipants", fetch = FetchType.EAGER)
	private Set<MyEvent> events;

	/**
	 * <p>
	 * Default constructor for JPA only.
	 * <p>
	 */
	public User() {
		events = new HashSet<MyEvent>();
	}

	/**
	 * <p>
	 * Main constructor for this class.
	 * </p>
	 * 
	 * @param name
	 *            to the user
	 * @param password
	 *            to the user
	 */
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
		events = new HashSet<MyEvent>();
	}

	/**
	 * <p>
	 * Getter for user's name.
	 * </p>
	 * 
	 * @return name of the user
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>
	 * Setter for user's name.
	 * </p>
	 * 
	 * @param name
	 *            to this user
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>
	 * Getter for user's password
	 * </p>
	 * 
	 * @return password of this user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <p>
	 * Setter for user's password.
	 * </p>
	 * 
	 * @param password
	 *            to this user.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * <p>
	 * Getter for user's id.
	 * </p>
	 * 
	 * @return user's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * <p>
	 * Setter for user's id. Only for JPA.
	 * </p>
	 * 
	 * @param id
	 *            to this user
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Getter for user's events.
	 * </p>
	 * 
	 * @return events of user
	 */
	public Set<MyEvent> getEvents() {
		return events;
	}

	/**
	 * <p>
	 * Setter for user's events. Only for JPA.
	 * </p>
	 * 
	 * @param events
	 *            to this user.
	 */
	public void setEvents(Set<MyEvent> events) {
		this.events = events;
	}

	/**
	 * <p>
	 * Adds user to this event
	 * </p>
	 * 
	 * @param event
	 *            in which user joins
	 */
	public void addToEvent(MyEvent event) {
		this.events.add(event);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		return true;
	}

	// Just use the username, for now.
	@Override
	public String toString() {
		return getName();
	}

}
