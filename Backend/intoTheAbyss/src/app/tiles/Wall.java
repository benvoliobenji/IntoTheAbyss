package app.tiles;

import app.utils.TileTypes;

public class Wall extends Tile {

	public Wall() {
		canHold = false;
		isPassable = false;
		type = TileTypes.WALL;
	}

	@Override
	public TileTypes getType() {
		return type;
	}

}
