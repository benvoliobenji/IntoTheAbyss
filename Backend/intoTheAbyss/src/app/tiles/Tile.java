package app.tiles;
import app.utils.TileTypes;

public abstract class Tile implements TileInterface {
	protected boolean canHold, isPassable;
	protected TileTypes type;

	public abstract boolean isPassable();
	public abstract boolean canHold();
	public abstract TileTypes getType();

	@Override
	public String toString(){
		return type.name() + "";
	}
}
