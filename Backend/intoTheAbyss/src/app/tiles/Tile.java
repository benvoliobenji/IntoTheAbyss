package app.tiles;

import app.utils.TileTypes;

public abstract class Tile implements TileInterface {

	/** The is passable. */
	protected boolean canHold, isPassable;

	/** The type. */
	protected TileTypes type;

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.tiles.TileInterface#isPassable()
	 */
	public abstract boolean isPassable();

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.tiles.TileInterface#canHold()
	 */
	public abstract boolean canHold();

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.tiles.TileInterface#getType()
	 */
	public abstract TileTypes getType();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return type.name() + "";
	}
}
