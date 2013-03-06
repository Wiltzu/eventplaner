package foo.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Activity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	private String name;
	@OneToOne
	private User creator;
	@OneToMany
	private Set<User> voters;
	private int votes;
	
	public enum Thumb {
		UP, DOWN;
	}

	// For JPA
	public Activity() {
	}

	public Activity(String name, User creator) {
		super();
		this.name = name;
		this.creator = creator;
		voters = new HashSet<User>();
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
	
	public Set<User> getVoters() {
		return voters;
	}

	public void setVoters(Set<User> voters) {
		this.voters = voters;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	public boolean vote(Thumb vote, User user) {
		if(!getVoters().contains(user)) {
			if(vote == Thumb.UP) {
				votes++;
			}
			else {
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
