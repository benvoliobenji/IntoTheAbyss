package app.level;

import java.awt.Point;

public interface RoomInterface {
	public Point point = new Point();

	public Point getRandomPointInRoom();

	public Point getCenter();
}
