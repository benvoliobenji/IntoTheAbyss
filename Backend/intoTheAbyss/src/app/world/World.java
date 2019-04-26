package app.world;

import java.util.Hashtable;
import java.util.Random;

import app.entity.player.PlayerInterface;
import app.level.Level;
import app.level.LevelInterface;
import app.room.Room;

/**
 * The Class World.
 */
public class World implements WorldInterface {

	/** This stores all levels in a hashtable allowing us to grab them by id */
	private Hashtable<Integer, LevelInterface> levels;

	/**
	 * Instantiates a new world with two levels by default.
	 */
	public World() {
		levels = new Hashtable<Integer, LevelInterface>();
		addLevel(0);
		levels.get(0).fillGridForDefaultMap();
		addLevel(1);
	}

	/**
	 * Gets the level by floor number/id.
	 *
	 * @param floorNumber the floor number
	 * @return the level
	 */
	public LevelInterface getLevel(int floorNumber) {
		return levels.get(floorNumber);
	}

	/**
	 * Adds the level with the given id and level number.
	 *
	 * @param levelNum the level num
	 */
	public void addLevel(int levelNum) {
		levels.put(levelNum, new Level(levelNum, new Room(new Random())));
	}

	/**
	 * Removes the level from the map this is usually paired with a update in the
	 * DB, to sync the level. This will be used to remove levels that don't need to
	 * exist as no players are on them.
	 *
	 * @param levelNum the level num
	 */
	public void removeLevel(int levelNum) {
		levels.remove(levelNum);
	}

	/**
	 * Adds the level outright.
	 *
	 * @param level the level
	 */
	public void addLevel(LevelInterface level) {
		levels.put(level.getLevel(), level);
	}

	// TODO change to accept entities
	/**
	 * Moves a player from one level to another.
	 *
	 * @param player the player to move
	 * @param from   the level the player is moved from
	 * @param to     the level the player is to be moved to
	 */
	public void switchFloors(PlayerInterface player, int from, int to) {
		levels.get(from).removePlayer(player.getID());
		levels.get(to).addPlayer(player);
		player.setFloor(Integer.valueOf(to));
		player.setPosX(Integer.valueOf(levels.get(to).getSpawn().x));
		player.setPosY(Integer.valueOf(levels.get(to).getSpawn().y));
	}

	/**
	 * Returns levels stored in the world.
	 *
	 * @return the depth
	 */
	public int getDepth() {
		return levels.size();
	}
}
