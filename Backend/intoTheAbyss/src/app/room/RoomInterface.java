package app.room;

import java.awt.Point;
import java.util.Random;

import app.tiles.Tile;

public interface RoomInterface {
	public Point getCorner();

	public Point getCenter();

	public int getxLength();

	public int getyLength();

	public Point getRandomPointInRoom();

	public RoomInterface genValidRoom(Random rand, Tile[][] grid);
}
