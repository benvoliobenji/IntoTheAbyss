package network.packets;

public class PlayerLocationPacket {
	private String playerID;
	private int playerLocationFloor, playerPositionX, playerPositionY;

	public PlayerLocationPacket() {

	}

	public String getPlayerID() {
		return playerID;
	}

	public int getPlayerFloor() {
		return playerLocationFloor;
	}

	public int getXPos() {
		return playerPositionX;
	}

	public int getYPos() {
		return playerPositionY;
	}

}
