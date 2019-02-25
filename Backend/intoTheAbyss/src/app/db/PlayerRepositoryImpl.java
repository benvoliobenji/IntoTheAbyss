package app.db;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import app.player.Player;

@Repository
public abstract class PlayerRepositoryImpl implements PlayerRepository {
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public List<Player> getPlayerByUsername(String username) {
        javax.persistence.Query query = entityManager.createNativeQuery("SELECT p FROM player p WHERE p.username =" + username , Player.class);
        return query.getResultList();
    }
    
    @Override
    public Player getPlayerByPlayerID(Integer playerPassedID) {
        /*javax.persistence.Query query = entityManager.createQuery(("SELECT * FROM player p WHERE p.playerid = " + playerPassedID.intValue()), Player.class);
        Player returnedPlayer = (Player) query.getSingleResult();*/
        return entityManager.find(Player.class, playerPassedID);
    }
}