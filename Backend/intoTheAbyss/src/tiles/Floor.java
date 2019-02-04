package tiles;

public class Floor extends Tile{
	final int type = 1;
	
	public Floor() {
		canHold = true;
		isPassable = true;
	}
	
	@Override
	public int getType() {
		return type;
	}

}
