package network.packets;

// TODO: Auto-generated Javadoc
/**
 * The Class MapRequestPacket.
 */
public class MapRequestPacket {
	
	/** The floor num. */
	public int floorNum;
	
	/**
	 * Instantiates a new map request packet.
	 */
	public MapRequestPacket() {
		
	}
	
	/**
	 * Instantiates a new map request packet.
	 *
	 * @param floor the floor
	 */
	public MapRequestPacket(int floor){
		floorNum = floor;
	}
	
	/**
	 * Gets the floor num.
	 *
	 * @return the floor num
	 */
	public int getFloorNum() {
		return floorNum;
	}
	
	/**
	 * Sets the floor num.
	 *
	 * @param floor the new floor num
	 */
	public void setFloorNum(int floor) {
		floorNum = floor;
	}
}
