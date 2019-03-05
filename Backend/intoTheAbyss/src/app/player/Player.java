package app.player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import app.items.Item;
import network.packets.PlayerPacket;

@Entity
public class Player {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	public String playerID;
	private String username;
	private Integer posX, posY, floor, health;
	

	public Player() {
		floor = 0;
		username = "";
		health = 10;
	}

	public Player(int floorNum) {
		floor = floorNum;
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

	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerid) {
		this.playerID = playerid;
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
		this.floor = floor;
	}

	public Integer getHealth() {
		return health;
	}

	public void setHealth(Integer health) {
		this.health = health;
	}
}
