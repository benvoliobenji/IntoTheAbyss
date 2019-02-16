package tiles;

public class Wall extends Tile {
	final int type = 2;

	public Wall() {
		canHold = false;
		isPassable = false;
	}

	@Override
	public int getType() {
		return type;
	}

}
