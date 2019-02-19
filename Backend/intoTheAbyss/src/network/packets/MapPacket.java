package network.packets;

import app.tiles.Tile;

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
