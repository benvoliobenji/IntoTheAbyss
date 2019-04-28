package app.entity.player;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esotericsoftware.jsonbeans.Json;

import app.db.PlayerRepository;

/**
 * The Class PlayerController.
 */
@Controller
@RequestMapping(path = "/players")
public class PlayerController {

	/** The player repository. */
	@Autowired
	private PlayerRepository playerRepository;

	/**
	 * Adds the player with default values on floor one.
	 *
	 * @param id the id
	 * @return the player
	 */
	@GetMapping(path = "/add")
	public @ResponseBody Player addPlayer(@RequestParam String id) {
		Optional<Player> p = playerRepository.findById(id);
		if (!p.isPresent()) {
			Player player = new Player(id);
			player.setMod(false);
			playerRepository.save(player);
			return player;
		} else
			return p.get();
	}

	@GetMapping(path="/addMod")
	public @ResponseBody Player addMod(@RequestParam String id, @RequestParam String code) {
		final String password = "testing";

		Player p = addPlayer(id);
		p.setMod(password.equals(code));
		playerRepository.save(p);

		return p;
	}

	/**
	 * Gets the player by ID
	 *
	 * @param playerUUIDPassed the player UUID passed
	 * @return the player
	 */
	@GetMapping(path = "/getPlayer")
	public @ResponseBody String getPlayer(@RequestParam String playerUUIDPassed) {
		Json j = new Json();
		String jsonStr = j.toJson(playerRepository.getPlayerByID(playerUUIDPassed));
		return jsonStr;
	}

	/**
	 * Gets the all players.
	 *
	 * @return the all players
	 */
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Player> getAllPlayers() {
		return playerRepository.findAll();
	}

	/**
	 * Clear players.
	 *
	 * @return the string
	 */
	@GetMapping(path = "/clear")
	public @ResponseBody String clearPlayers() {
		playerRepository.deleteAll();
		return "Cleared players";
	}
}
