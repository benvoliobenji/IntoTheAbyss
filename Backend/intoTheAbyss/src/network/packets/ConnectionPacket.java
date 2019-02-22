package network.packets;

public class ConnectionPacket {
	private Integer userID;
	
	public ConnectionPacket() {
		
	}
	
	public ConnectionPacket(int ID) {
		userID = ID;
	}
	
	public void setID(Integer ID) {
		userID = ID;
	}
	
	public void getID(Integer ID) {
		userID = ID;
	}
}
