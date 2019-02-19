package app.tiles;
import app.utils.TileTypes;

public class Floor extends Tile {
	final int type = 1;

	public Floor() {
		canHold = true;
		isPassable = true;
		typel = TileTypes.FLOOR;
	}

	@Override
	public int getType() {
		return type;
	}
	
	public TileTypes getTypeEnum() {
		return typel;
	}

}
