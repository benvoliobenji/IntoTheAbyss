package worlds;
import java.util.ArrayList;


public class World {
	private ArrayList<Level> levels;
	private ArrayList<Player> players;
	
	public World() {
		levels = new ArrayList<Level>();
		
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
		for(Level l: levels) {
			for(Player p: players) {
		        p.setFloorNumber(0);
		    }
		}
	}
	
	
}
