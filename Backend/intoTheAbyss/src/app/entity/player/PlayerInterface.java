package app.entity.player;

import app.entity.GameEntity;
import app.group.Group;

/**
 * The Interface PlayerInterface.
 */
public interface PlayerInterface extends GameEntity {

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername();

	/**
	 * Sets the username.
	 *
	 * @param uname the new username
	 */
	public void setUsername(String uname);

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

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(Group group);

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public Group getGroup();
}
