package player;

public class Player {
	private String username;
	private int floor, x, y;
	
	public Player() {
		floor = 0;
		username = "";
	}
	
	public Player(int floorNum) {
		floor = floorNum;
	}

	public int getFloorNumber() {
		return floor;
	}

	public void setFloorNumber(int floorNumber) {
		floor = floorNumber;
	}
}
