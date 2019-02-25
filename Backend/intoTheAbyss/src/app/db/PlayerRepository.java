package app.db;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.player.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
	List<Player> getPlayerByUsername(String username);
	Player getPlayerByPlayerID(Integer playerPassedID);
}
