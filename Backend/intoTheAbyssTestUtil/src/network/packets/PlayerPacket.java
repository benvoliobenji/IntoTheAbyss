package network.packets;

public class PlayerPacket {
	private String username;
	private int playerLocationFloor, playerPositionX, playerPositionY, ID;
	
	public PlayerPacket() {
		
	}

	public int getID() {
		return ID;
	}
	
	public int getXPos() {
		return playerPositionX;
	}
	
	public int getYPos() {
		return playerPositionY;
	}
	
	public int getFloorNumber() {
		return playerLocationFloor;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setID(int userID) {
		ID = userID;
	}
	
	public void setXPos(int x) {
		playerPositionX = x;
	}
	
	public void setYPos(int y) {
		playerPositionY = y;
	}
	
	public void setFloorNumber(int floor) {
		playerLocationFloor = floor;
	}
	
	public void setUsername(String uname) {
		username = uname;
	}
}