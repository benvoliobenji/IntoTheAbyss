package network.packets;

public class MapRequestPacket {
	public int floorNum;
	
	public MapRequestPacket() {
		
	}
	
	public MapRequestPacket(int floor){
		floorNum = floor;
	}
	
	public int getFloorNum() {
		return floorNum;
	}
}
