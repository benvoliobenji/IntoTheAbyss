package network.packets;

import tiles.Tile;

public class MapPacket {
	private Tile[][] grid;
	
	public MapPacket() {
		
	}
	
	public MapPacket(Tile[][] mapGrid) {
		grid = mapGrid;
	}
	
	public Tile[][] getGrid(){
		return grid;
	}
}
