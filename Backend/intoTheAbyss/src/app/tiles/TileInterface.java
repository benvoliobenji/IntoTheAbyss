package app.tiles;

import app.utils.TileTypes;

// TODO: Auto-generated Javadoc
/**
 * The Interface TileInterface.
 */
public interface TileInterface {
	
	/**
	 * Checks if is passable.
	 *
	 * @return true, if is passable
	 */
	public abstract boolean isPassable();
	
	/**
	 * Can hold.
	 *
	 * @return true, if successful
	 */
	public abstract boolean canHold();
	
	/**
	 * Gets the type.
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
