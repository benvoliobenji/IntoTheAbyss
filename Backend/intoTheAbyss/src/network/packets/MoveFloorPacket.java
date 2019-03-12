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
		return floor;
	}
}
