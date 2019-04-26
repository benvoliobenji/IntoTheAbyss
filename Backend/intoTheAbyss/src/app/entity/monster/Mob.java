package app.entity.monster;

import java.awt.Point;

import app.entity.GameEntity;

public interface Mob extends GameEntity {
	/**
	 * Gets the health.
	 *
	 * @return the health
	 */
	public Integer getHealth();

	/**
	 * Sets the health.
	 *
	 * @param health the new health
	 */
	public void setHealth(Integer health);

	public Point getNextAction();
}
