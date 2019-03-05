package network.packets;

public class ConnectionPacket {
	private String userID;
	
	public ConnectionPacket() {
		
	}
	
	public ConnectionPacket(String ID) {
		userID = ID;
	}
	
	public void setID(String ID) {
		userID = ID;
	}
	
	public void getID(String ID) {
		userID = ID;
	}
}
