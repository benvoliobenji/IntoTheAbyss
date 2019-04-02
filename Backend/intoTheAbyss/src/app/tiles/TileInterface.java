package app.tiles;

import app.utils.TileTypes;

public interface TileInterface {
	public abstract boolean isPassable();
	public abstract boolean canHold();
	public abstract TileTypes getType();
	@Override
	public String toString();
}
