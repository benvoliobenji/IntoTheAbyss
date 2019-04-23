package app.world;

import java.util.Hashtable;
import java.util.Random;

import app.entity.player.PlayerInterface;
import app.level.Level;
import app.level.LevelInterface;
import app.room.Room;

public class World implements WorldInterface {
	private Hashtable<Integer, LevelInterface> levels;

	public World() {
		levels = new Hashtable<Integer, LevelInterface>();
		addLevel(0);
		levels.get(0).fillGridForDefaultMap();
		addLevel(1);
	}

	public LevelInterface getLevel(int floorNumber) {
		return levels.get(floorNumber);
	}

	public void addLevel(int levelNum) {
		levels.put(levelNum, new Level(levelNum, new Room(new Random())));
	}

	public void removeLevel(int levelNum) {
		levels.remove(levelNum);
	}

	public void addLevel(LevelInterface level) {
		levels.put(level.getLevel(), level);
	}

	public void switchFloors(PlayerInterface player, int from, int to) {
		levels.get(from).removePlayer(player.getID());
		levels.get(to).addPlayer(player);
		player.setFloor(Integer.valueOf(to));
		player.setPosX(Integer.valueOf(levels.get(to).getSpawn().x));
		player.setPosY(Integer.valueOf(levels.get(to).getSpawn().y));
	}

	public int getDepth() {
		return levels.size();
	}
}
