package app.level;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import app.player.Player;
import app.tiles.*;
import app.utils.TileTypes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Level {
	public static final int mapWidth = 100;
	public static final int mapHeight = 25;

	@Id
	private Integer level;
	private Random rand;
	private ArrayList<Player> players;
	@Transient
	private Tile[][] grid;
	@Transient
	private Room[] rooms;
	private Point spawn;
	private Point stair;

	public Level() {
		grid = new Tile[mapHeight][mapWidth];
		rand = new Random();
		generate();
	}

	public Level(Random random) {
		grid = new Tile[mapHeight][mapWidth];
		rand = random;
		generate();
	}

	public Level(Integer level) {
		this.level = level;
		grid = new Tile[mapHeight][mapWidth];
		rand = new Random();
		generate();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Tile[][] getGrid() {
		return grid;
	}

	public Point getSpawn() {
		return spawn;
	}

	public Point getStair() {
		return stair;
	}

	private void generate() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = new Wall();
			}
		}

		int numRooms = 0;
		rooms = new Room[8];

		while (numRooms < 8) {
			createRoom(numRooms);
			numRooms++;
		}

		createCorridors();
		createSpawn();
		createStair();

	}

	private void createRoom(int idx) {
		/* generate height, width, then top left corner x,y */
		int i, j;
		int x, y;
		boolean loop;

		do {
			/* create entry in room array */
			//y length and x length
			//y corner x corner
			rooms[idx] = new Room(rand);


			/* check that the room's position is valid */
			loop = false;
			for (i = 0; i < rooms[idx].getyLength() + 2; i++) {
				for (j = 0; j < rooms[idx].getxLength() + 2; j++) {
					x = rooms[idx].getCorner().x;
					y = rooms[idx].getCorner().y;
					if (grid[y + i - 1][x + j - 1].getType() != TileTypes.WALL) // add an enum or smth for tile types?
						loop = true;
				}
			}
			/* if not, retry */
		} while (loop);

		/* write array into grid */
		for (i = 0; i < rooms[idx].getyLength(); i++) {
			for (j = 0; j < rooms[idx].getxLength(); j++) {
				x = rooms[idx].getCorner().x;
				y = rooms[idx].getCorner().y;
				grid[y + i][x + j] = new Floor();
			}
		}
	}

	private void createCorridors() {
		int idx = findCenterRoom();
		Point center, p;
		center = rooms[idx].getCenter();

		for (int i = 0; i < rooms.length; i++) {
			if (i == idx)
				continue;
			p = rooms[i].getCenter();
			drawCorridor(p.x, p.y, center.x, center.y);
		}
	}

	private void drawCorridor(int x1, int y1, int x2, int y2) {
		boolean leftRoom = false;
		int xsign = x1 > x2 ? -1 : 1;
		int ysign = y1 > y2 ? -1 : 1;
		int i = 0;

		while (x1 + i != x2) {
			if (grid[y1][x1 + i].getType() == TileTypes.WALL)
				leftRoom = true;
			if (leftRoom && grid[y1][x1 + i].getType() != TileTypes.WALL)
				return;
			if (grid[y1][x1 + i].getType() == TileTypes.WALL)
				grid[y1][x1 + i] = new Floor();
			if (leftRoom && (grid[y1 + 1][x1 + i].getType() != TileTypes.WALL ||
					grid[y1 - 1][x1 + i].getType() != TileTypes.WALL))
				return;
			i += xsign;
		}
		i = 0;

		while (y1 + i != y2) {
			if (grid[y1 + i][x2].getType() == TileTypes.WALL)
				grid[y1 + i][x2] = new Floor();
			i += ysign;
		}
	}

	private int findCenterRoom() {
		int cX = 40;
		int cY = 21 / 2;
		int i;
		Point p;
		int idx = 0;
		double dist = 1000;
		double curr_dist;

		for (i = 0; i < rooms.length; i++) {
			p = rooms[i].getCenter();
			curr_dist = Math.sqrt((cX - p.x) * (cX - p.x) + 4 * (cY - p.y) * (cY - p.y));
			if (curr_dist < dist) {
				dist = curr_dist;
				idx = i;
			}
		}

		return idx;
	}

	private void createStair() {
		int idx = findCenterRoom();
		int r = 0;
		while (r == idx)
			r = rand.nextInt(rooms.length);
		stair = rooms[r].getRandomPointInRoom();
		grid[stair.y][stair.x] = new Stair();
	}

	private void createSpawn() {
		int idx = findCenterRoom();
		spawn = rooms[idx].getCenter();
	}

	public void fillGridForDefaultMap() {
		for(int i=0; i < mapHeight; i++) {
			for(int j=0; j < mapWidth; j++) {
				//Checks if the selected index is an edge of the grid
				if(i == 0 || j == 0 || i == mapHeight-1 || j == mapWidth-1) {
					grid[i][j]  = new Wall();
				}else {
					grid[i][j] = new Floor();
				}
			}
		}
		grid[mapHeight / 2][mapWidth / 2] = new Stair();
		stair.y = mapHeight / 2;
		stair.x = mapWidth / 2;
	}

}
