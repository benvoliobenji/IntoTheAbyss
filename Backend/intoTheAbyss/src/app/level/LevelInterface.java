package app.level;

import java.awt.Point;
import java.util.ArrayList;

import app.entity.Entity;
import app.entity.player.PlayerInterface;
import app.tiles.Tile;

// TODO: Auto-generated Javadoc
/**
 * The Interface LevelInterface.
 */
public interface LevelInterface {
	
	/** The Constant MAPWIDTH. */
	public static final int MAPWIDTH = 100;
	
	/** The Constant MAPHEIGHT. */
	public static final int MAPHEIGHT = 25;
	
	/** The Constant MAXROOMS. */
	public static final int MAXROOMS = 8;

	/**
	 * Builds the default level.
	 */
	public void buildDefaultLevel();

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public ArrayList<PlayerInterface> getPlayers();

	/**
	 * Gets the player.
	 *
	 * @param ID the id
	 * @return the player
	 */
	public PlayerInterface getPlayer(String ID);

	/**
	 * Gets the grid.
	 *
	 * @return the grid
	 */
	public Tile[][] getGrid();

	/**
	 * Gets the spawn.
	 *
	 * @return the spawn
	 */
	public Point getSpawn();

	/**
	 * Gets the stair.
	 *
	 * @return the stair
	 */
	public Point getStair();

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public Integer getLevel();

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty();

	/**
	 * Adds the player.
	 *
	 * @param p the p
	 */
	public void addPlayer(PlayerInterface p);

	/**
	 * Adds the entity.
	 *
	 * @param e the e
	 */
	public void addEntity(Entity e);

	/**
	 * Removes the entity.
	 *
	 * @param entityID the entity ID
	 */
	public void removeEntity(String entityID);

	/**
	 * Removes the player.
	 *
	 * @param playerID the player ID
	 */
	public void removePlayer(String playerID);

	/**
	 * Replace player.
	 *
	 * @param playerID the player ID
	 * @param p the p
	 */
	public void replacePlayer(String playerID, PlayerInterface p);

	/**
	 * Fill grid for default map.
	 */
	public void fillGridForDefaultMap();

}
