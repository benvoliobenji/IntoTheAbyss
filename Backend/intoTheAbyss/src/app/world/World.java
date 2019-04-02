package app.world;

import java.util.ArrayList;
import java.util.Hashtable;

import app.level.Level;
import app.player.Player;

public class World {
	private ArrayList<Level> levels;
	private Hashtable<Integer, Level> levelsT;

	public World() {
		levels = new ArrayList<Level>();
		levelsT = new Hashtable<Integer, Level>();
		addLevel(0);
		levels.get(0).fillGridForDefaultMap();
	}

	public World(int seed) {

	}

	public Level getLevel(int floorNumber) {
		return levels.get(floorNumber);
	}

	public void addLevel() {
		levels.add(new Level());
	}

	public void addLevel(int levelNum) {
		levels.add(new Level(levelNum));
	}

	public void addLevel(Level level) {
		levels.add(level);
	}

	public void switchFloors(Player player, int fOne, int fTwo) {
		levels.get(fOne).removePlayer(player.getPlayerID());
		levels.get(fTwo).addPlayer(player);
		player.setFloor(Integer.valueOf(fTwo));
		player.setPosX(Integer.valueOf(levels.get(fTwo).getSpawn().x));
		player.setPosY(Integer.valueOf(levels.get(fTwo).getSpawn().y));
	}

	public int getDepth() {
		return levels.size();
	}
}
