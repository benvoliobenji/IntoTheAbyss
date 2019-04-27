package app.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

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
	@Query(value="select p from player p where p.username = ?1", nativeQuery=true)
	List<Player> getPlayerByUsername(String username);

	/**
	 * Gets the player by player ID.
	 *
	 * @param playerPassedID the player passed ID
	 * @return Player
	 */
	@Query(value="select p from player p where p.ID = ?1", nativeQuery=true)
	Player getPlayerByID(String playerPassedID);
}
