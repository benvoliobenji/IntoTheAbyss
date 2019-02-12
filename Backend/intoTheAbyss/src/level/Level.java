package level;

import java.util.ArrayList;
import java.util.Random;
import player.Player;
import tiles.Floor;
import tiles.Tile;
import tiles.Wall;

public class Level {
	private static int mapWidth = 100;
	private static int mapHeight = 50;
	// private static int mapdepth = 3;

	private Random rand;
	private ArrayList<Player> players;
	private Tile[][] grid;
	public int[][] rooms;
	private int[] spawn;

	public Level() {
		grid = new Tile[mapHeight][mapWidth];
		rand = new Random();
		generate();
	}

	public Level(Random random) {
	  grid = new Tile[mapHeight][mapWidth];
    rand = random;

	}

	public Level(int seed) {
	  grid = new Tile[mapHeight][mapWidth];
    rand = new Random(seed);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Tile[][] getGrid() {
		return grid;
	}

	private void generate() {
		for (Tile[] dim : grid)
			for (Tile t : dim)
				t = new Wall();
		
		for (int i = 0; i < grid.length; i ++) {
		  for (int j = 0; j < grid[0].length; j++) {
		    grid[i][j] = new Wall();
		  }
		}

		int num_rooms = 0;
		rooms = new int[8][4];
		
		while (num_rooms < 8) {
			createRoom(num_rooms);
			num_rooms++;
		}

		createCorridors();
		createSpawn();
		createStair();

	}

	private void createRoom(int idx) {
		/* generate height, width, then top left corner x,y */
		int i,j;
		int a,b,c,d;
		boolean x;

		do {
			/* create entry in room array */
			c = rooms[idx][2] = (rand.nextInt(Integer.MAX_VALUE) % 5) + 6;
			d = rooms[idx][3] = (rand.nextInt(Integer.MAX_VALUE) % 15) + 10;
			
			a = rooms[idx][0] = (rand.nextInt(Integer.MAX_VALUE) % (mapHeight - c - 1)) + 1;
      b = rooms[idx][1] = (rand.nextInt(Integer.MAX_VALUE) % (mapWidth - d - 1)) + 1;
      
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

		for (int i = 0; i < rooms.length; i++) {
			if (i == idx)
				continue;
			x = rooms[i][1] + rooms[i][3] / 2;
			y = rooms[i][0] + rooms[i][2] / 2;
			drawCorridor(x,y,cx,cy);
		}
	}

	private void drawCorridor(int x1, int y1, int x2, int y2){		 
		boolean leftRoom = false;
		int xsign = x1 > x2 ? -1 : 1;
		int ysign = y1 > y2 ? -1 : 1;
    int i = 0;
		
	  while (x1 + i != x2) {
	    if (grid[y1][x1 + i].getType() == 2)
	      leftRoom = true;
	    if (leftRoom && grid[y1][x1 + i].getType() != 2)
        return;
	    if (grid[y1][x1 + i].getType() == 2)
	      grid[y1][x1 + i] = new Floor();
	    if (leftRoom && (grid[y1 + 1][x1 + i].getType() != 2 || grid[y1 - 1][x1 + i].getType() != 2 ))
	      return;
	    i += xsign;
	  }
	  i = 0;
	  
	  while (y1 + i != y2) {
	    if (grid[y1 + i][x2].getType() == 2)
	      grid[y1 + i][x2] = new Floor();
	    i += ysign;
	  }
	  
	  /*boolean leftRoom;
		int i;
		int xsign = (x1 - x2) / Math.abs(x1 - x2);
		int ysign = (y1 - y2) / Math.abs(y1 - y2);

		leftRoom = false;
		for (i = x1; xsign * i >= xsign * x2; i -= xsign) {
			if (grid[y1][i].getType() == 2)
				leftRoom = true;
			if (leftRoom && grid[y1][i].getType() != 2)
				return;
			if (grid[y1][i].getType() == 2) {
				grid[y1][i] = new Floor();
			}
			//if (leftRoom && (grid[y1 + 1][i].getType() != 2 || grid[y1 - 1][i].getType() != 2))
			//	return;
		}

		for (i = y1; ysign * i >= ysign * y2; i -= ysign) {
			if (grid[i][x2].getType() == 2) {
				grid[i][x2] = new Floor();
			}
		}*/

	}

	private int findCenterRoom() {
	int cX = 40;
    int cY = 21/2;
    int i,x,y;
    int idx = 0;
    double dist = 100;
    double curr_dist;

    for (i = 0; i < rooms.length; i++){
        x = rooms[i][1] + rooms[i][3] / 2;
        y = rooms[i][0] + rooms[i][2] / 2;
        curr_dist = Math.sqrt((cX-x)*(cX-x) + (cY-y)*(cY-y));
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
