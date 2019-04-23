package network.packets;

import app.entity.player.Player;

public class PlayerPacket {
	private String id, username;
	private int playerLocationFloor, playerPositionX, playerPositionY, health;

	public PlayerPacket() {

	}

	public PlayerPacket(Player p) {
		id = p.getID();
		username = p.getUsername();
		playerLocationFloor = p.getFloor().intValue();
		playerPositionX = p.getPosX().intValue();
		playerPositionY = p.getPosY().intValue();
		health = p.getHealth().intValue();
	}

	public String getID() {
		return id;
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

	public int getHealth() {
		return health;
	}
}
