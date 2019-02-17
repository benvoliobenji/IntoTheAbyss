package player;

import javax.persistence.Entity;
import javax.persistence.Id;

import network.packets.PlayerPacket;

@Entity
public class Player {
	@Id
	private String playerID;
	private Integer posX, posY, floor, health;

	public Player() {
		floor = 0;
		playerID = "";
	}
	
	public Player(int floorNum) {
		floor = floorNum;
	}
	
	public Player(PlayerPacket playerPacket) {
		floor = playerPacket.getFloorNumber();
		playerID = playerPacket.getUsername();
		posX = playerPacket.getXPos();
		posY = playerPacket.getYPos();
	}
	
	public Player(String ID, int xPos, int yPos, int floorNum){
		floor = floorNum;
		playerID = ID;
		posX = xPos;
		posY = yPos;
	}

	public String toString() {
		String s = "";
		s += "Username: " + playerID + "\t";
		s += "Position (floor, x, y): " + floor + ", " + posX + ", " + posY;
		return s;
	}
	
	
	public String getPlayerId() {
		return playerID;
	}

	public void setPlayerId(String s) {
		this.playerID = s;
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
