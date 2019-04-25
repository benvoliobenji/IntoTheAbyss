package network.actions;

import java.awt.Point;

// TODO: Auto-generated Javadoc
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
	 * Sets the location.
	 *
	 * @param p the new location
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
	 * Sets the floor moved to.
	 *
	 * @param floorMovedTo the new floor moved to
	 */
	public void setFloorMovedTo(int floorMovedTo) {
		this.floorMovedTo = floorMovedTo;
	}

}
