package app.tiles;

import app.utils.TileTypes;

/**
 * The Class Floor.
 */
public class Floor extends Tile {

	/**
	 * Instantiates a new floor.
	 */
	public Floor() {
		canHold = true;
		isPassable = true;
		type = TileTypes.FLOOR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.tiles.Tile#getType()
	 */
	@Override
	public TileTypes getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.tiles.Tile#isPassable()
	 */
	public boolean isPassable() {
		return isPassable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.tiles.Tile#canHold()
	 */
	public boolean canHold() {
		return canHold;
	}

}
