package network.actions;

import java.awt.Point;

public class Move {
	private Point location;
	private int floorMovedTo;

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point p) {
		location = p;
	}

	public int getFloorMovedTo() {
		return floorMovedTo;
	}

	public void setFloorMovedTo(int floorMovedTo) {
		this.floorMovedTo = floorMovedTo;
	}

}
