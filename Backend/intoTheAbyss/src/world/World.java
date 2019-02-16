package world;

import java.util.ArrayList;

import level.Level;
import app.player.Player;

public class World {
	private ArrayList<Level> levels;
	private ArrayList<Player> players;
	// private Random rand;

	public World() {
		levels = new ArrayList<Level>();
		players = new ArrayList<Player>();

	}

	public World(int seed) {

	}

	public Level getLevel(int floorNumber) {
		return levels.get(floorNumber);
	}

	public void addLevel() {
		levels.add(new Level());
	}

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
