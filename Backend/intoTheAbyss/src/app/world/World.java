package app.world;

import java.util.ArrayList;

import app.level.Level;
import app.player.Player;

public class World {
	private ArrayList<Level> levels;
	private ArrayList<Player> players;

	public World() {
		levels = new ArrayList<Level>();
		players = new ArrayList<Player>();
		
		addLevel();
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

	public void addLevel(Level level) {
		levels.add(level);
	}

	public void switchFloors(Player player, int fOne, int fTwo) {
		levels.get(fOne).removePlayer(player);
		levels.get(fTwo).addPlayer(player);
		player.setFloor(Integer.valueOf(fTwo));
		player.setPosX(Integer.valueOf(levels.get(fTwo).getSpawn().x));
		player.setPosY(Integer.valueOf(levels.get(fTwo).getSpawn().y));
	}

	public int getDepth() {
		return levels.size();
	}

	// Starts with a near empty world
	public void resetWorld() {
		levels.clear();
		addLevel();
		movePlayersToTop();
	}

	public void movePlayersToTop() {
		for (int i = 0; i < levels.size(); i++) {
			for (Player p : players) {
				p.setFloor(0);
			}
		}
	}

}
