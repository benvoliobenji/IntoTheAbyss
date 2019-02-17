package app.db;
import org.springframework.data.repository.CrudRepository;

import player.Player;
import network.packets.PlayerPacket;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

}
