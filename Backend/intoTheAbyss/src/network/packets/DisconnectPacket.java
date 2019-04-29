package network.packets;

public class DisconnectPacket {
	/** The user ID. */
	private String userID;
	private int floor;

	/**
	 * Instantiates a new connection packet.
	 */
	public DisconnectPacket() {

	}

	/**
	 * Instantiates a new connection packet.
	 *
	 * @param ID the ID of connected entity... a user as of now.
	 */
	public DisconnectPacket(String ID) {
		userID = ID;
	}

	/**
	 * Sets the ID for entity connected.
	 *
	 * @param ID the new id
	 */
	public void setID(String ID) {
		userID = ID;
	}

	/**
	 * Gets the ID for created entity.
	 *
	 * @return the id
	 */
	public String getID() {
		return userID;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}
}
