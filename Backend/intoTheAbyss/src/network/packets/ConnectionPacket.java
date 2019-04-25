package network.packets;

/**
 * This class is the initial connection packet that takes an entity's ID.
 */
public class ConnectionPacket {

	/** The user ID. */
	private String userID;

	/**
	 * Instantiates a new connection packet.
	 */
	public ConnectionPacket() {

	}

	/**
	 * Instantiates a new connection packet.
	 *
	 * @param ID the ID of connected entity... a user as of now.
	 */
	public ConnectionPacket(String ID) {
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
}
