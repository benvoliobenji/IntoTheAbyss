package app.player;

import javax.persistence.Entity;
import javax.persistence.Id;

import network.packets.PlayerPacket;

@Entity
public class Player {
	@Id
	private Integer playerID;
	private String username;
	private Integer posX, posY, floor, health;

	public Player() {
		floor = 0;
		username = "";
	}
	
	public Player(int floorNum) {
		floor = Integer.valueOf(floorNum);
		playerID = Integer.valueOf(17);
		username = "" + 17;
		posX = Integer.valueOf(1);
		posY = Integer.valueOf(1);
		health = Integer.valueOf(10);
	}
	
	public Player(PlayerPacket playerPacket) {
		playerID = Integer.valueOf(19);
		floor = Integer.valueOf(playerPacket.getFloorNumber());
		username = playerPacket.getUsername() + "";
		posX = Integer.valueOf(playerPacket.getXPos());
		posY = Integer.valueOf(playerPacket.getYPos());
		health = Integer.valueOf(10);
	}
	
	@Override
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

	public Integer getPlayerID() {
		return playerID;
	}

	public void setPlayerID(Integer playerid) {
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
