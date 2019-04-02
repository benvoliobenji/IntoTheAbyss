package app.tiles;

import app.utils.TileTypes;

public class Wall extends Tile {

	public Wall() {
		canHold = false;
		isPassable = false;
		type = TileTypes.WALL;
	}

	public TileTypes getType() {
		return type;
	}
	
	public boolean isPassable() {
		return isPassable;
	}
	
	public boolean canHold() {
		return canHold;
	}

}
