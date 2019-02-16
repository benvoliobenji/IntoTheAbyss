package app.db;
import org.springframework.data.repository.CrudRepository;

import player.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

}
