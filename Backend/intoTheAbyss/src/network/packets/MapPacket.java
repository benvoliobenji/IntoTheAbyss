package network.packets;

import app.tiles.Tile;

/**
 * The Class MapPacket.
 */
public class MapPacket {

	/** The grid. */
	private Tile[][] grid;

	/**
	 * Instantiates a new map packet.
	 */
	public MapPacket() {

	}

	/**
	 * Instantiates a new map packet via a mapGrid.
	 *
	 * @param mapGrid the map grid
	 */
	public MapPacket(Tile[][] mapGrid) {
		grid = mapGrid;
	}

	/**
	 * Gets the grid.
	 *
	 * @return a Tile[][] representing a levels terrain.
	 */
	public Tile[][] getGrid() {
		return grid;
	}
}
