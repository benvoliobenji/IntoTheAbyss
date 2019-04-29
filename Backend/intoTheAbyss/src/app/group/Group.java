package app.group;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * The group type stores id and list of players.
 */
@Entity
@Table(name = "\"Table\"")
public class Group {

	/** The group ID. */
	@Id
	@Column(length = 50)
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	public String groupID;

	/** The leader. */
	private String leader;

	/** The players. */
	@OneToMany(mappedBy = "group")
	private List<String> players;

	public Group() {
		players = new ArrayList<String>();
	}

	/**
	 * Gets the group ID.
	 *
	 * @return the group ID
	 */
	public String getGroupID() {
		return groupID;
	}

	/**
	 * Gets the leader.
	 *
	 * @return the leader
	 */
	public String getLeader() {
		return leader;
	}

	/**
	 * Sets the leader.
	 *
	 * @param leader the new leader
	 */
	public void setLeader(String leader) {
		this.leader = leader;
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<String> getPlayers() {
		return players;
	}

	/**
	 * Sets the players in group via list of players.
	 *
	 * @param players the new players
	 */
	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public void addPlayer(String playerID) {
		players.add(playerID);
	}

	public void removePlayer(String playerID) {
		players.remove(playerID);
	}
}
