package app.level;

import java.awt.Point;
import java.util.Random;

public class Room {
	private int mapWidth;
	private int mapHeight;
	
	private Point corner;
	private int xLength, yLength;
	Random rand;

	public Room(Random rand) {
		corner = new Point();
		this.rand = rand;
		mapWidth = Level.mapWidth;
		mapHeight = Level.mapHeight;
		/* create entry in room array */
		// y length and x length
		yLength = rand.nextInt(5) + 6;
		xLength = rand.nextInt(15) + 10;
		// y corner x corner
		corner.y = rand.nextInt(mapHeight - yLength - 1) + 1;
		corner.x = rand.nextInt(mapWidth - xLength - 1)  + 1;

	}

	public Point getCorner() {
		return corner;
	}

	public Point getCenter() {
		Point p = new Point();
		p.x = corner.x + xLength / 2;
		p.y = corner.y + yLength / 2;
		return p;
	}

	public int getxLength() {
		return xLength;
	}

	public int getyLength() {
		return yLength;
	}

	public boolean overlapsRoom(Room differentRoom) {
		return false;
	}

	public Point getRandomPointInRoom() {
		Point p = new Point();
		p.x = corner.x + 1 + rand.nextInt(xLength - 2);
		p.y = corner.y + 1 + rand.nextInt(yLength - 2);
		return p;
	}


}
