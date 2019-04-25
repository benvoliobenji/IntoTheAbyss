package app.db;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import app.entity.player.Player;

@Repository
public abstract class PlayerRepositoryImpl implements PlayerRepository {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.db.PlayerRepository#getPlayerByUsername(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Player> getPlayerByUsername(String username) {
		String queryString = "SELECT p FROM player p WHERE p.username =" + username;
		javax.persistence.Query query = entityManager.createNativeQuery(queryString, Player.class);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.db.PlayerRepository#getPlayerByPlayerID(java.lang.String)
	 */
	@Override
	public Player getPlayerByPlayerID(String playerPassedID) {
		UUID uuid = UUID.fromString(playerPassedID);
		System.out.print(uuid.toString());
		return entityManager.find(Player.class, uuid.toString());
	}
}