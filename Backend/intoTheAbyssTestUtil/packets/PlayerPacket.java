package network.packets;

public class PlayerPacket {
	private String playerID;
	private int playerLocationFloor, playerPositionX, playerPositionY;
	
	public PlayerPacket() {
		
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
		return playerID;
	}
}
