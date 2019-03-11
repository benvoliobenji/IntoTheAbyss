package app.db;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.player.Player;

public interface PlayerRepository extends CrudRepository<Player, String> {
	List<Player> getPlayerByUsername(String username);
	Player getPlayerByPlayerID(String playerPassedID);
}
