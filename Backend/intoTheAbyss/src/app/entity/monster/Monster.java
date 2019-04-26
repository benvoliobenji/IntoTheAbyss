package app.entity.monster;

import java.awt.Point;

/**
 * The Class Monster.
 */
public class Monster implements Mob {

	public String ID;

	private Integer posX, posY, floor, health;

	@Override
	public String toString() {
		return ID + " " + posX + ", " + posY + " Floor:" + floor;
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void setID(String ID) {
		this.ID = ID;
	}

	@Override
	public Integer getPosX() {
		return posX;
	}

	@Override
	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	@Override
	public Integer getPosY() {
		return posY;
	}

	@Override
	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	@Override
	public Integer getFloor() {
		return floor;
	}

	@Override
	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	@Override
	public Integer getHealth() {
		return health;
	}

	@Override
	public void setHealth(Integer health) {
		this.health = health;
	}

	@Override
	public Point getNextAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
