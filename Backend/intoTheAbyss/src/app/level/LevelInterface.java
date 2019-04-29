package app.level;

import java.awt.Point;

import app.entity.GameEntity;
import app.tiles.Tile;

/**
 * The Level Interface.
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
	 * Gets the grid.
	 *
	 * @return the grid
	 */
	public Tile[][] getGrid();

	/**
	 * Gets the spawn point.
	 *
	 * @return the spawn point
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
	 * Adds the entity to the level.
	 *
	 * @param e the e
	 */
	public void addEntity(GameEntity e);

	/**
	 * Gets the Entity as a Entitiy by ID
	 *
	 * @param ID the id
	 * @return the Entity requested
	 */
	public GameEntity getEntity(String ID);

	/**
	 * Removes the entity from the level by ID.
	 *
	 * @param entityID the entity ID
	 */
	public void removeEntity(String ID);

	public void replaceEntity(String ID, GameEntity e);

	/**
	 * Fill grid for default map.
	 */
	public void fillGridForDefaultMap();

}
