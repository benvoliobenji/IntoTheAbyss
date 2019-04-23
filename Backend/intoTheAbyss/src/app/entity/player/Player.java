package app.entity.player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import app.group.Group;
import network.packets.PlayerPacket;

@Entity
public class Player implements PlayerInterface {
	@Id
	@Column(length = 50)
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	public String ID;
	private String username;
	@ManyToOne
	@JoinColumn(name = "groupID")
	private Group group;
	private Integer posX, posY, floor, health;

	public Player() {
		floor = Integer.valueOf(0);
		username = "";
		health = Integer.valueOf(10);
	}

	public Player(PlayerPacket playerPacket) {
		username = playerPacket.getUsername();
		floor = Integer.valueOf(playerPacket.getFloorNumber());
		posX = Integer.valueOf(playerPacket.getXPos());
		posY = Integer.valueOf(playerPacket.getYPos());
	}

	public String toString() {
		String s = "";
		s += "Username: " + username + "\t";
		s += "Position (floor, x, y): " + floor + ", " + posX + ", " + posY;
		return s;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String uname) {
		this.username = uname;
	}

	public String getID() {
		return ID;
	}

	public void setID(String playerid) {
		this.ID = playerid;

	}

	public Integer getPosX() {
		return posX;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = Integer.valueOf(floor);
	}

	public Integer getHealth() {
		return health;
	}

	public void setHealth(Integer health) {
		this.health = Integer.valueOf(health);
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Group getGroup() {
		return group;
	}
}
