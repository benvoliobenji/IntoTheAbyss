package app.player;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esotericsoftware.jsonbeans.Json;

import app.db.PlayerRepository;

@Controller
@RequestMapping(path = "/players")
public class PlayerController {
	@Autowired
	private PlayerRepository playerRepository;

	@GetMapping(path = "/add")
	public @ResponseBody Player addPlayer(@RequestParam String username) {
		List<Player> players = playerRepository.getPlayerByUsername(username);
		if (players.isEmpty()) {
			Player player = new Player();
			player.setFloor(0);
			player.setUsername(username);
			player.setHealth(10);
			player.setPosX(1);
			player.setPosY(1);
			playerRepository.save(player);
			return player;
		} else
			return null;
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
