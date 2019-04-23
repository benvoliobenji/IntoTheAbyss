package app.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import app.entity.player.Player;

public interface PlayerRepository extends CrudRepository<Player, String> {
	List<Player> getPlayerByUsername(String username);

	Player getPlayerByPlayerID(String playerPassedID);
}
