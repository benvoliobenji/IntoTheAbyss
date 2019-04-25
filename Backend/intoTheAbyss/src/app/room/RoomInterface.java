package app.room;

import java.awt.Point;
import java.util.Random;

import app.tiles.Tile;

// TODO: Auto-generated Javadoc
/**
 * The Interface RoomInterface.
 */
public interface RoomInterface {
	
	/**
	 * Gets the corner.
	 *
	 * @return the corner
	 */
	public Point getCorner();

	/**
	 * Gets the center.
	 *
	 * @return the center
	 */
	public Point getCenter();

	/**
	 * Gets the x length.
	 *
	 * @return the x length
	 */
	public int getxLength();

	/**
	 * Gets the y length.
	 *
	 * @return the y length
	 */
	public int getyLength();

	/**
	 * Gets the random point in room.
	 *
	 * @return the random point in room
	 */
	public Point getRandomPointInRoom();

	/**
	 * Gen valid room.
	 *
	 * @param rand the rand
	 * @param grid the grid
	 * @return the room interface
	 */
	public RoomInterface genValidRoom(Random rand, Tile[][] grid);
}
