package network.actions;

import java.awt.Point;

/**
 * The Class Move.
 */
public class Move {

	/** The location. */
	private Point location;

	/** The floor moved to. */
	private int floorMovedTo;

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Sets the location to p.
	 *
	 * @param p the location to move actor of the Action
	 */
	public void setLocation(Point p) {
		location = p;
	}

	/**
	 * Gets the floor moved to.
	 *
	 * @return the floor moved to
	 */
	public int getFloorMovedTo() {
		return floorMovedTo;
	}

	/**
	 * Sets the floor moved to. This is the same as the floor the action happened on
	 * if you are not moving to a new floor.
	 * 
	 * @param floorMovedTo the new floor moved to
	 */
	public void setFloorMovedTo(int floorMovedTo) {
		this.floorMovedTo = floorMovedTo;
	}

}
