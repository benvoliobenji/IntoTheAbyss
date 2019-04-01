package app.level;

import java.awt.Point;
import java.util.ArrayList;

import app.player.Player;
import app.tiles.Tile;

public interface LevelInterface {

	public void buildDefaultLevel();

	public ArrayList<Player> getPlayers();

	public Player getPlayer(String ID);

	public Tile[][] getGrid();

	public Point getSpawn();

	public Point getStair();

	public void addPlayer(Player p);

	public void removePlayer(String playerID);

	public void fillGridForDefaultMap();

}
