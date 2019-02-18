package app.player;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import app.db.PlayerRepository;

@Repository
public abstract class PlayerRepositoryImpl implements PlayerRepository {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<Player> getPlayerByUsername(String username) {
        javax.persistence.Query query = entityManager.createNativeQuery("SELECT p FROM player p WHERE p.username =" + username , Player.class);
        return query.getResultList();
    }
}