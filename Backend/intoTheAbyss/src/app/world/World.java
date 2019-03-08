package app.world;

import java.util.ArrayList;

import app.level.Level;
import app.player.Player;

public class World {
	private ArrayList<Level> levels;
	private ArrayList<Player> players;
	// private Random rand;

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

	public int getDepth(){
		return levels.size();
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
