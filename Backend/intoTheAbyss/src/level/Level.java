package level;

import java.util.ArrayList;
import java.util.Random;
import player.Player;
import tiles.*;

public class Level {
	private static int mapWidth = 100;
	private static int mapHeight = 50;
	// private static int mapdepth = 3;

	private Random rand;
	private ArrayList<Player> players;
	private Tile[][] grid;
	private int[][] rooms;
	private int[] spawn;

	public Level() {
		grid = new Tile[mapWidth][mapHeight];
		rand = new Random();
	}

	public Level(Random random) {
		grid = new Tile[mapWidth][mapHeight];
		rand = random;

	}

	public Level(int seed) {
		grid = new Tile[mapWidth][mapHeight];
		rand = new Random(seed);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Object[][] getGrid() {
		return grid;
	}

	private void generate() {
		for (Object[] dim : grid)
			for (Object obj : dim)
				obj = new Wall();

		int num_rooms = 0;

		while (num_rooms < 8) {
			createRoom(num_rooms++);
		}

		createCorridors();

	}

	private void createRoom(int idx) {
		rooms = new int[8][4];
		/* generate height, width, then top left corner x,y */
		int i,j;
		int a,b,c,d;
		boolean x;

		do {
			/* create entry in room array */
			c = rooms[idx][2] = (rand.nextInt() % 5) + 3;
			d = rooms[idx][3] = (rand.nextInt() % 15) + 5;
			a = rooms[idx][0] = (rand.nextInt() % (mapHeight - c - 1)) + 1;
			b = rooms[idx][1] = (rand.nextInt() % (mapWidth - d - 1)) + 1;

			/* check that the room's position is valid */
			x = false;
			for (i = 0; i < c+2; i++){
				for (j = 0; j < d+2; j++){
					if (grid[a + i - 1][b + j - 1].getType() != 2) // add an enum or smth for tile types?
						x = true;
				}
			}
			/* if not, retry */
		} while (x);

		/* write array into grid */
		for (i = 0; i < c; i++){
			for (j = 0; j < d; j++){
				grid[a + i][b + j] = new Floor();
			}
		}
	}

	private void createCorridors() {

	}

	private int findCenterRoom() {

	}

	private void createStair() {

	}

	private void createSpawn() {
		int idx = findCenterRoom();
		spawn = new int[2];
		spawn[1] = rooms[idx][1] + rooms[idx][3] / 2;
		spawn[0] = rooms[idx][0] + rooms[idx][2] / 2;
	}
}
