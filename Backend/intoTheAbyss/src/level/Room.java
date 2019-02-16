package level;

import java.awt.Point;
import java.util.Random;

import tiles.Floor;

public class Room {
	private static int mapWidth = 100;
	private static int mapHeight = 50;
	
	private Point corner;
	private int xLength, yLength;

	public Room(Random rand) {
		corner = new Point();
		/* create entry in room array */
		// y length and x length
		yLength = (rand.nextInt(Integer.MAX_VALUE) % 5) + 6;
		xLength = (rand.nextInt(Integer.MAX_VALUE) % 15) + 10;
		// y corner x corner
		corner.x = (rand.nextInt(Integer.MAX_VALUE) % (mapHeight - yLength - 1)) + 1;
		corner.y = (rand.nextInt(Integer.MAX_VALUE) % (mapWidth - xLength - 1)) + 1;

	}

	public Point getCorners() {
		return corner;
	}

	public Point getCenter() {
		return null;
	}

	public boolean overlapsRoom(Room differentRoom) {
		return false;
	}

	public Point getRandomPointInRoom() {
		return null;
	}

}
