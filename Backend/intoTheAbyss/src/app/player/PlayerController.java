package app.player;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.esotericsoftware.jsonbeans.Json;

import app.db.PlayerRepository;

@Controller
@RequestMapping(path = "/players")
public class PlayerController {
	@Autowired
	private PlayerRepository playerRepository;

	@GetMapping(path = "/add")
	public @ResponseBody String addPlayer(@RequestParam String username) {
		List<Player> players = playerRepository.getPlayerByUsername(username);
		if (players.isEmpty()) {
			Player player = new Player();
			player.setFloor(0);
			player.setUsername(username);
			player.setHealth(10);
			player.setPosX(1);
			player.setPosY(1);
			playerRepository.save(player);
			Json json = new Json();
			return json.toJson(player);
		}
		return "Unable to save player with username " + username + " as this player already exists";
	}

	@GetMapping(path = "/getPlayer")
	public @ResponseBody String getPlayer(@RequestParam String playerUUIDPassed) {
		Json j = new Json();
		String jsonStr = j.toJson(playerRepository.getPlayerByPlayerID(playerUUIDPassed));
		return jsonStr;
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Player> getAllPlayers() {
		return playerRepository.findAll();
	}

	@GetMapping(path = "/clear")
	public @ResponseBody String clearPlayers() {
		playerRepository.deleteAll();
		return "Cleared players";
	}
}
