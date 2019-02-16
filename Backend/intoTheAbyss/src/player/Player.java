package player;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Player {
	@Id
	private String playerID;
	private Integer posX, posY, floor, health;

	public String getPlayerId() {
		return playerID;
	}

	public void setPlayerId(int i) {
		this.playerID = "" + i;
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
