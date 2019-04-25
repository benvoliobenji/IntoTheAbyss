package app.tiles;

import app.utils.TileTypes;

/**
 * The Interface TileInterface.
 */
public interface TileInterface {

	/**
	 * Checks if is passable, meaning it can be moved through by a Entity.
	 *
	 * @return true, if is passable
	 */
	public abstract boolean isPassable();

	/**
	 * Can hold an Item.
	 *
	 * @return true, if successful
	 */
	public abstract boolean canHold();

	/**
	 * Gets the type as a TileTypes enum.
	 *
	 * @return the type
	 */
	public abstract TileTypes getType();

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString();
}
