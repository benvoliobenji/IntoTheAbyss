package app.db;

import org.springframework.data.repository.CrudRepository;
import app.player.Player;

public interface InventoryRepository extends CrudRepository<Player, Integer>{
	
}
