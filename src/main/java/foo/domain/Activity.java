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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * <p>
 * JPA domain class that represents activity in this application.
 * </p>
 * 
 * @author Ville Ahti
 * 
 */
@Entity
public class Activity implements Serializable {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	private String name;
	@OneToOne
	private User creator;
	@OneToMany
	private Set<User> voters;
	private int votes;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EVENT_ID")
	private MyEvent event;

	/**
	 * <p>
	 * Enum class that represents UP and DOWN votes for this activity.
	 * </p>
	 * 
	 * @author Ville Ahti
	 * 
	 */
	public enum Thumb {
		UP, DOWN;
	}

	/**
	 * <p>
	 * Default constructor for JPA only
	 * <p>
	 */
	public Activity() {
	}

	/**
	 * <p>
	 * Main constructor for creating instances of this class.
	 * </p>
	 * 
	 * @param name
	 *            of activity
	 * @param creator
	 *            of activity
	 */
	public Activity(String name, User creator) {
		super();
		this.name = name;
		this.creator = creator;
		voters = new HashSet<User>();
	}

	/**
	 * <p>
	 * Getter for activity's name.
	 * </p>
	 * 
	 * @return name of activity
	 */
	public int getId() {
		return id;
	}

	/**
	 * <p>
	 * Setter for activity's Id. Only for JPA.
	 * </p>
	 * 
	 * @param id
	 *            of activity
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Getter for activity's name.
	 * </p>
	 * 
	 * @return name of activity
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>
	 * Setter for activity's name.
	 * </p>
	 * 
	 * @param name
	 *            of activity
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>
	 * Getter for activity's creator.
	 * </p>
	 * 
	 * @return creator of activity
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * <p>
	 * Setter for activity's creator.
	 * </p>
	 * 
	 * @param creator
	 *            of activity
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * <p>
	 * Getter for activity's voters.
	 * </p>
	 * 
	 * @return set of voters
	 */
	public Set<User> getVoters() {
		return voters;
	}

	/**
	 * <p>
	 * Setter for activity's voters. Only for JPA.
	 * </p>
	 * 
	 * @param voters
	 *            of this activity
	 */
	public void setVoters(Set<User> voters) {
		this.voters = voters;
	}

	/**
	 * <p>
	 * Getter for given votes of activity.
	 * </p>
	 * 
	 * @return given votes
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * <p>
	 * Setter for activity's votes. Only for JPA.
	 * </p>
	 * 
	 * @param votes
	 *            of this activity
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}

	/**
	 * <p>
	 * Getter for event in which this activity belongs to.
	 * </p>
	 * 
	 * @return event in which this activity belongs to
	 */
	public MyEvent getEvent() {
		return event;
	}

	/**
	 * <p>
	 * Setter for activity's event
	 * </p>
	 * 
	 * @param event
	 */
	public void setEvent(MyEvent event) {
		this.event = event;
	}

	/**
	 * <p>
	 * Method for voting activity. Two votes for one user aren't allowed.
	 * </p>
	 * 
	 * @param vote
	 *            UP or DOWN
	 * @param user
	 *            who gave the vote
	 * @return the success of the vote
	 */
	public boolean vote(Thumb vote, User user) {
		if (!getVoters().contains(user)) {
			if (vote == Thumb.UP) {
				votes++;
			} else {
				votes--;
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Activity other = (Activity) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + ", creator=" + creator
				+ "]";
	}

}
