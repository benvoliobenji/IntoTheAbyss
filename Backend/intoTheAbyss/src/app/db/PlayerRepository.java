package app.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import app.entity.player.Player;

/**
 * The Interface PlayerRepository.
 */
public interface PlayerRepository extends CrudRepository<Player, String> {

	/**
	 * Gets the player by username.
	 *
	 * @param username the username
	 * @return list<Player>
	 */
	List<Player> getPlayerByUsername(String username);

	/**
	 * Gets the player by player ID.
	 *
	 * @param playerPassedID the player passed ID
	 * @return Player
	 */
	Player getPlayerByPlayerID(String playerPassedID);
}
