package app.room;

import java.awt.Point;
import java.util.Random;

import app.tiles.Tile;

/**
 * The Interface for a room.
 */
public interface RoomInterface {

	/**
	 * Gets the top left corner of the room as a Point
	 *
	 * @return Point representing the top left corner
	 */
	public Point getCorner();

	/**
	 * Gets the center of the room.
	 *
	 * @return Point at the center of the room
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
	 * Gen valid room. meaning it doesn't overlap another room in the grid.
	 *
	 * @param rand Random to use when generating
	 * @param grid Tile[][] that is used to manage
	 * @return RoomInterface of a valid room
	 */
	public RoomInterface genValidRoom(Random rand, Tile[][] grid);
}
