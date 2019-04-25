package network.packets;

import app.entity.player.Player;

/**
 * Class used for sending data to a test utility. Try using the api for more
 * expected behavior.
 */
public class PlayerPacket {

	/** The username. */
	private String id, username;

	/** The health. */
	private int playerLocationFloor, playerPositionX, playerPositionY, health;

	/**
	 * Instantiates a new player packet.
	 */
	public PlayerPacket() {

	}

	/**
	 * Instantiates a new player packet.
	 *
	 * @param p the p
	 */
	public PlayerPacket(Player p) {
		id = p.getID();
		username = p.getUsername();
		playerLocationFloor = p.getFloor().intValue();
		playerPositionX = p.getPosX().intValue();
		playerPositionY = p.getPosY().intValue();
		health = p.getHealth().intValue();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getID() {
		return id;
	}

	/**
	 * Gets the x pos.
	 *
	 * @return the x pos
	 */
	public int getXPos() {
		return playerPositionX;
	}

	/**
	 * Gets the y pos.
	 *
	 * @return the y pos
	 */
	public int getYPos() {
		return playerPositionY;
	}

	/**
	 * Gets the floor number.
	 *
	 * @return the floor number
	 */
	public int getFloorNumber() {
		return playerLocationFloor;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the health.
	 *
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}
}
