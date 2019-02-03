package worlds;

import java.util.ArrayList;
import java.util.Random;

public class Level {
	private static int mapWidth = 100;
	private static int mapHeight = 50;
	private static int mapdepth = 3;

	private Random rand;
	private ArrayList<Player> players;
	private Object[][] grid;
	
	public Level() {
		grid = new Object[mapWidth][mapHeight];
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
	
	
	
}
