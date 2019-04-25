package app.level;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import app.entity.player.PlayerInterface;
import app.room.RoomInterface;
import app.tiles.Floor;
import app.tiles.Stair;
import app.tiles.Tile;
import app.tiles.Wall;
import app.utils.TileTypes;

/**
 * This stores most of the data for a given floor of our dungeon.
 */
@Entity
public class Level implements LevelInterface {

	@Id
	private Integer level;
	private Random rand;
	private Hashtable<String, PlayerInterface> players;
	private Hashtable<String, app.entity.Entity> entities;
	@Transient
	private Tile[][] grid;
	@Transient
	private RoomInterface[] rooms;
	private Point spawn;
	private Point stair;

	/**
	 * Instantiates a new level.
	 */
	public Level() {
		rand = new Random();
		players = new Hashtable<String, PlayerInterface>();
		entities = new Hashtable<String, app.entity.Entity>();
		grid = new Tile[MAPHEIGHT][MAPWIDTH];
		fillGridForDefaultMap();
	}

	/**
	 * Instantiates a new level.
	 *
	 * @param level the level
	 * @param room  the room
	 */
	public Level(Integer level, RoomInterface room) {
		players = new Hashtable<String, PlayerInterface>();
		entities = new Hashtable<String, app.entity.Entity>();
		this.level = level;
		grid = new Tile[MAPHEIGHT][MAPWIDTH];
		rand = new Random();
		if (level % 5 != 0)
			generate(room);
		else
			fillGridForDefaultMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#buildDefaultLevel()
	 */
	public void buildDefaultLevel() {
		players = new Hashtable<String, PlayerInterface>();
		grid = new Tile[MAPHEIGHT][MAPWIDTH];
		fillGridForDefaultMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#getGrid()
	 */
	public Tile[][] getGrid() {
		return grid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#getSpawn()
	 */
	public Point getSpawn() {
		return spawn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#getStair()
	 */
	public Point getStair() {
		return stair;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#getLevel()
	 */
	public Integer getLevel() {
		return level;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#getPlayer(java.lang.String)
	 */
	public PlayerInterface getPlayer(String ID) {
		return players.get(ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#getPlayers()
	 */
	public ArrayList<PlayerInterface> getPlayers() {
		return new ArrayList<PlayerInterface>(players.values());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#addPlayer(app.entity.player.PlayerInterface)
	 */
	public void addPlayer(PlayerInterface p) {
		players.put(p.getID(), p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#removePlayer(java.lang.String)
	 */
	public void removePlayer(String playerID) {
		players.remove(playerID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#replacePlayer(java.lang.String,
	 * app.entity.player.PlayerInterface)
	 */
	public void replacePlayer(String playerID, PlayerInterface p) {
		players.replace(playerID, p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#addEntity(app.entity.Entity)
	 */
	public void addEntity(app.entity.Entity e) {
		entities.put(e.getID(), e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#removeEntity(java.lang.String)
	 */
	public void removeEntity(String entityID) {
		entities.remove(entityID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#isEmpty()
	 */
	public boolean isEmpty() {
		return players.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.level.LevelInterface#fillGridForDefaultMap()
	 */
	public void fillGridForDefaultMap() {
		for (int i = 0; i < MAPHEIGHT; i++) {
			for (int j = 0; j < MAPWIDTH; j++) {
				// Checks if the selected index is an edge of the grid
				if (i == 0 || j == 0 || i == MAPHEIGHT - 1 || j == MAPWIDTH - 1) {
					grid[i][j] = new Wall();
				} else {
					grid[i][j] = new Floor();
				}
			}
		}
		grid[MAPHEIGHT / 2][MAPWIDTH / 2] = new Stair();
		stair = new Point(MAPWIDTH / 2, MAPHEIGHT / 2);
		spawn = new Point((MAPWIDTH / 2) - 1, (MAPHEIGHT / 2) - 1);
	}

	/**
	 * Prints the level on server side.
	 */
	public void printLevel() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].getType() == TileTypes.WALL)
					System.out.print('#');
				else if (grid[i][j].getType() == TileTypes.FLOOR)
					System.out.print('.');
				else if (grid[i][j].getType() == TileTypes.STAIR)
					System.out.print('S');
			}
			System.out.println("");
		}
	}

	/**
	 * Generates a map in the tile array.
	 *
	 * @param room a room object to help with generation and placement of tiles.
	 */
	private void generate(RoomInterface room) {
		// fill level with walls
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = new Wall();
			}
		}

		int numRooms = 0;
		rooms = new RoomInterface[MAXROOMS];

		while (numRooms < MAXROOMS) {
			createRoom(numRooms, room);
			numRooms++;
		}

		createCorridors();
		createSpawn();
		createStair();

	}

	/**
	 * This creates a random valid room at an index idx.
	 *
	 * @param idx  the index to place the room in the level
	 * @param room the room we use to generate a valid room
	 */
	private void createRoom(int idx, RoomInterface room) {
		rooms[idx] = room.genValidRoom(rand, grid);
		/* write array into grid */
		for (int i = 0; i < rooms[idx].getyLength(); i++) {
			for (int j = 0; j < rooms[idx].getxLength(); j++) {
				int x = rooms[idx].getCorner().x;
				int y = rooms[idx].getCorner().y;
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

	/**
	 * Draw corridor.
	 *
	 * @param x1 the x 1
	 * @param y1 the y 1
	 * @param x2 the x 2
	 * @param y2 the y 2
	 */
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
			if (leftRoom && (grid[y1 + 1][x1 + i].getType() != TileTypes.WALL
					|| grid[y1 - 1][x1 + i].getType() != TileTypes.WALL))
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

	/**
	 * Find center room.
	 *
	 * @return the int
	 */
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
			if (curr_dist < dist) { dist = curr_dist; idx = i; }
		}

		return idx;
	}

	/**
	 * Creates the stairs for the level.
	 */
	private void createStair() {
		int idx = findCenterRoom();
		int r = 0;
		while (r == idx)
			r = rand.nextInt(rooms.length);
		stair = rooms[r].getRandomPointInRoom();
		grid[stair.y][stair.x] = new Stair();
	}

	/**
	 * Creates the spawn for the level.
	 */
	private void createSpawn() {
		int idx = findCenterRoom();
		spawn = rooms[idx].getCenter();
	}
}
