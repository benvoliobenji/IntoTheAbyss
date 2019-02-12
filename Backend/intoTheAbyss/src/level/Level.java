package level;

import java.util.ArrayList;
import java.util.Random;
import player.Player;

public class Level {
	private static int mapWidth = 100;
	private static int mapHeight = 50;
	// private static int mapdepth = 3;

	// private Random rand;
	private ArrayList<Player> players;
	private Object[][] grid;

	public Level() {
		grid = new Object[mapWidth][mapHeight];
	}

	public Level(Random random) {
		grid = new Object[mapWidth][mapHeight];
		// rand = random;

	}

	public Level(int seed) {
		grid = new Object[mapWidth][mapHeight];
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Object[][] getGrid() {
		return grid;
	}

	public void fillGridForDefaultMap() {
		for(int i=0; i < mapWidth; i++) {
			for(int j=0; j < mapHeight; j++) {
				//Checks if the selected index is an edge of the grid
				if(i=0 || j=0 || i = mapWidth-1 || j = mapHeight-1) {
					grid[i][j]  = new Wall();
				}else {
					grid[i][j] = new Floor();
				}
			}
		}
	}

}
