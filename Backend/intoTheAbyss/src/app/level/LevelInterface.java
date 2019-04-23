package app.level;

import java.awt.Point;
import java.util.ArrayList;

import app.entity.Entity;
import app.entity.player.PlayerInterface;
import app.tiles.Tile;

public interface LevelInterface {
	public static final int MAPWIDTH = 100;
	public static final int MAPHEIGHT = 25;
	public static final int MAXROOMS = 8;

	public void buildDefaultLevel();

	public ArrayList<PlayerInterface> getPlayers();

	public PlayerInterface getPlayer(String ID);

	public Tile[][] getGrid();

	public Point getSpawn();

	public Point getStair();

	public Integer getLevel();

	public boolean isEmpty();

	public void addPlayer(PlayerInterface p);

	public void addEntity(Entity e);

	public void removeEntity(String entityID);

	public void removePlayer(String playerID);

	public void replacePlayer(String playerID, PlayerInterface p);

	public void fillGridForDefaultMap();

}
