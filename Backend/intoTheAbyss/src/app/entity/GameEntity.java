package app.entity;

/**
 * Interface for items, monsters, npcs, and players
 */
public interface GameEntity {

	/**
	 * Prints a string representation of the given entity.
	 *
	 * @return the string
	 */
	@Override
	public String toString();

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getID();

	/**
	 * Sets the ID to find the instance in the DB and level.
	 *
	 * @param ID the new id
	 */
	public void setID(String ID);

	/**
	 * Gets the position in the X dimension.
	 *
	 * @return the pos X
	 */
	public Integer getPosX();

	/**
	 * Sets the position in the X dimension.
	 *
	 * @param posX the new pos X
	 */
	public void setPosX(Integer posX);

	/**
	 * Gets the position in the Y dimension.
	 *
	 * @return the pos Y
	 */
	public Integer getPosY();

	/**
	 * Sets the position in the Y dimension.
	 *
	 * @param posY the new pos Y
	 */
	public void setPosY(Integer posY);

	/**
	 * Gets the floor.
	 *
	 * @return the floor
	 */
	public Integer getFloor();

	/**
	 * Sets the floor.
	 *
	 * @param floor the new floor
	 */
	public void setFloor(Integer floor);
}
