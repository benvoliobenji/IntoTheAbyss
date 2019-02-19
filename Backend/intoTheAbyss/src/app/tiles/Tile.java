package app.tiles;
import app.utils.TileTypes;

public abstract class Tile {
	protected boolean canHold, isPassable;
	protected TileTypes typel;

	public abstract int getType();

}
