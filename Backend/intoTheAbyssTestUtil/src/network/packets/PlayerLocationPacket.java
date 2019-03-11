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
		return getPlayerPositionX();
	}

	public int getYPos() {
		return getPlayerPositionY();
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public int getPlayerPositionX() {
		return playerPositionX;
	}

	public void setPlayerPositionX(int playerPositionX) {
		this.playerPositionX = playerPositionX;
	}

	public int getPlayerPositionY() {
		return playerPositionY;
	}

	public void setPlayerPositionY(int playerPositionY) {
		this.playerPositionY = playerPositionY;
	}

}
