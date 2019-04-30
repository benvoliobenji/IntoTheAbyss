package app.entity.player;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import app.group.Group;
import network.packets.PlayerPacket;

/**
 * This stores data for all player types.
 */
@Entity
public class Player implements PlayerInterface {

	/** The id. */
	@Id
	// @Column(length = 50)
	// @GeneratedValue(generator = "UUID")
	// @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	public String ID;

	/** The username. */
	private String username;

	/** The group. */
	// @Embedded
	@Transient
	private Group group;

	/** The health. */
	private Integer posX, posY, floor, health;
	private boolean isAdmin;

	/**
	 * Instantiates a new player.
	 */
	public Player() {
		floor = Integer.valueOf(0);
		username = "";
		health = Integer.valueOf(10);
		isAdmin = false;
	}

	/**
	 * Instantiates a new player, by assigning a username, floor, posx, posy.
	 *
	 * @param playerPacket the player packet
	 */
	public Player(PlayerPacket playerPacket) {
		username = playerPacket.getUsername();
		floor = Integer.valueOf(playerPacket.getFloorNumber());
		posX = Integer.valueOf(playerPacket.getXPos());
		posY = Integer.valueOf(playerPacket.getYPos());
		health = Integer.valueOf(10);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = "";
		s += "Username: " + username + "\t";
		s += "Position (floor, x, y): " + floor + ", " + posX + ", " + posY;
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.entity.player.PlayerInterface#getUsername()
	 */
	public String getUsername() {
		return username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.entity.player.PlayerInterface#setUsername(java.lang.String)
	 */
	public void setUsername(String uname) {
		this.username = uname;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.entity.Entity#getID()
	 */
	public String getID() {
		return ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.entity.Entity#setID(java.lang.String)
	 */
	public void setID(String playerid) {
		this.ID = playerid;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.entity.Entity#getPosX()
	 */
	public Integer getPosX() {
		return posX;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.entity.Entity#setPosX(java.lang.Integer)
	 */
	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.entity.Entity#getPosY()
	 */
	public Integer getPosY() {
		return posY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.entity.Entity#setPosY(java.lang.Integer)
	 */
	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.entity.Entity#getFloor()
	 */
	public Integer getFloor() {
		return floor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.entity.Entity#setFloor(java.lang.Integer)
	 */
	public void setFloor(Integer floor) {
		this.floor = Integer.valueOf(floor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.entity.player.PlayerInterface#getHealth()
	 */
	public Integer getHealth() {
		return health;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.entity.player.PlayerInterface#setHealth(java.lang.Integer)
	 */
	public void setHealth(Integer health) {
		this.health = Integer.valueOf(health);
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * Sets the player as admin.
	 *
	 * @param group the new group
	 */
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * Gets the isAdmin attribute.
	 *
	 * @return the group
	 */
	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void removePlayerFromGroup(String playerID) {
		group.removePlayer(playerID);
	}
}
