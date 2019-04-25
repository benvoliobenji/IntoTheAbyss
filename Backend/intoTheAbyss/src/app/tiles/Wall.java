package app.tiles;

import app.utils.TileTypes;

// TODO: Auto-generated Javadoc
/**
 * The Class Wall.
 */
public class Wall extends Tile {

	/**
	 * Instantiates a new wall.
	 */
	public Wall() {
		canHold = false;
		isPassable = false;
		type = TileTypes.WALL;
	}

	/* (non-Javadoc)
	 * @see app.tiles.Tile#getType()
	 */
	public TileTypes getType() {
		return type;
	}
	
	/* (non-Javadoc)
	 * @see app.tiles.Tile#isPassable()
	 */
	public boolean isPassable() {
		return isPassable;
	}
	
	/* (non-Javadoc)
	 * @see app.tiles.Tile#canHold()
	 */
	public boolean canHold() {
		return canHold;
	}

}
