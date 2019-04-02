package app.tiles;
import app.utils.TileTypes;

public class Floor extends Tile {

	public Floor() {
		canHold = true;
		isPassable = true;
		type = TileTypes.FLOOR;
	}

	@Override
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
