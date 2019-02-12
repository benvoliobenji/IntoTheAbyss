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
		createSpawn();
		createStair();

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
		int idx = findCenterRoom();
		int x,y;
		int cx = rooms[idx][1] + rooms[idx][3] / 2;
		int cy = rooms[idx][0] + rooms[idx][2] / 2;

		for (int i = 0; i < max; i++) {
			if (i == idx)
				continue;
			x = rooms[i][1] + rooms[i][3] / 2;
			y = rooms[i][0] + rooms[i][2] / 2;
			drawCorridor(x,y,cx,cy);
		}
	}

	private void drawCorridor(int x1, int y1, int x2, int y2){		 
		int leftRoom;
		int i;
		int xsign = (x1 - x2) / Math.abs(x1 - x2);
		int ysign = (y1 - y2) / Math.abs(y1 - y2);

		leftRoom = 0;
		for (i = x1; xsign * i >= xsign * x2; i -= xsign) {
			if (grid[y1][i].getType() == 2)
				leftRoom = 1;
			if (leftRoom && grid[y1][i].getType() != 2)
				return;
			if (grid[y1][i].getType() == 2) {
				grid[y1][i] = new Floor();
			}
			if (leftRoom && (grid[y1 + 1][i].getType() != 2 || grid[y1 - 1][i].getType() != 2))
				return;
		}

		for (i = y1; ysign * i >= ysign * y2; i -= ysign) {
			if (grid[i][x2].getType() == 2) {
				grid[i][x2] = new Floor();
			}
		}

	}

	private int findCenterRoom() {
	int cX = 40;
    int cY = 21/2;
    int i,x,y;
    int idx = 0;
    double dist = 100;
    double curr_dist;

    for (i = 0; i < MAX_ROOMS; i++){
        x = rooms[i][1] + rooms[i][3] / 2;
        y = rooms[i][0] + rooms[i][2] / 2;
        curr_dist = sqrt((cX-x)*(cX-x) + (cY-y)*(cY-y));
        if (curr_dist < dist){
            dist = curr_dist;
            idx = i;
        }
    }

    return idx;
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
