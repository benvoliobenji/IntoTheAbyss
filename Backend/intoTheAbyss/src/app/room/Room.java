package app.room;

import java.awt.Point;
import java.util.Random;

import app.tiles.Tile;
import app.utils.TileTypes;

/**
 * The Class Room.
 */
public class Room implements RoomInterface {

	/** The Constant MAPWIDTH. */
	private static final int MAPWIDTH = 100;

	/** The Constant MAPHEIGHT. */
	private static final int MAPHEIGHT = 25;

	/** The min width. */
	private static int minWidth = 10;

	/** The min height. */
	private static int minHeight = 6;

	/** The corner. */
	private Point corner;

	/** The y length. */
	private int xLength, yLength;

	/** The rand. */
	private Random rand;

	/**
	 * Instantiates a new room.
	 *
	 * @param rand Random to be used to gen room.
	 */
	public Room(Random rand) {
		corner = new Point();
		this.rand = rand;

		/* create entry in room array */
		// y length and x length
		yLength = rand.nextInt(4) + minHeight;
		xLength = rand.nextInt(15) + minWidth;
		// y corner x corner
		corner.y = rand.nextInt(MAPHEIGHT - yLength - 1) + 1;
		corner.x = rand.nextInt(MAPWIDTH - xLength - 1) + 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.room.RoomInterface#getCorner()
	 */
	public Point getCorner() {
		return corner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.room.RoomInterface#getCenter()
	 */
	public Point getCenter() {
		Point p = new Point();
		p.x = corner.x + xLength / 2;
		p.y = corner.y + yLength / 2;
		return p;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.room.RoomInterface#getxLength()
	 */
	public int getxLength() {
		return xLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.room.RoomInterface#getyLength()
	 */
	public int getyLength() {
		return yLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.room.RoomInterface#getRandomPointInRoom()
	 */
	public Point getRandomPointInRoom() {
		Point p = new Point();
		p.x = corner.x + 1 + rand.nextInt(xLength - 2);
		p.y = corner.y + 1 + rand.nextInt(yLength - 2);
		return p;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.room.RoomInterface#genValidRoom(java.util.Random,
	 * app.tiles.Tile[][])
	 */
	public RoomInterface genValidRoom(Random rand, Tile[][] grid) {
		boolean loop = false;
		Room result;

		do {
			result = new Room(rand);
			/*
			 * Checks that the room's position is valid
			 */
			loop = false;
			for (int i = 0; i < result.getyLength() + 2; i++) {
				for (int j = 0; j < result.getxLength() + 2; j++) {
					if (grid[result.getCorner().y + i - 1][result.getCorner().x + j - 1].getType() != TileTypes.WALL)
						loop = true;
				}
			}
			/* if not, retry */
		} while (loop);

		return result;
	}

}
