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
}
