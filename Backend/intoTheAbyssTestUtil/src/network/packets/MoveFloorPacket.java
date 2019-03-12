package network.packets;

public class MoveFloorPacket {
	private String userID;
	private int floor;

	public MoveFloorPacket() {

	}

	public String getUserID() {
		return userID;
	}

	public int getFloorNum() {
		return getFloor();
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}
}
