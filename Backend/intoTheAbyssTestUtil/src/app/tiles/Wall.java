package app.tiles;

import app.utils.TileTypes;

public class Wall extends Tile {
	final int type = 2;

	public Wall() {
		canHold = false;
		isPassable = false;
		typel = TileTypes.WALL;
	}

	@Override
	public int getType() {
		return type;
	}

}
