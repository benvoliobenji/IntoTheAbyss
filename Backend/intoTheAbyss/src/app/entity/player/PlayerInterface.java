package app.entity.player;

import app.entity.Entity;

/**
 * The Interface PlayerInterface.
 */
public interface PlayerInterface extends Entity {

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
//	
//	public Group getGroup();
//	
//	public void setGroup(Group group);
}
