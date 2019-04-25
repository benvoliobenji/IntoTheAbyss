package app.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import app.entity.player.Player;

// TODO: Auto-generated Javadoc
/**
 * The Interface PlayerRepository.
 */
public interface PlayerRepository extends CrudRepository<Player, String> {
	
	/**
	 * Gets the player by username.
	 *
	 * @param username the username
	 * @return the player by username
	 */
	List<Player> getPlayerByUsername(String username);

	/**
	 * Gets the player by player ID.
	 *
	 * @param playerPassedID the player passed ID
	 * @return the player by player ID
	 */
	Player getPlayerByPlayerID(String playerPassedID);
}
