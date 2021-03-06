package app.entity.player;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	 * @param username the username
	 * @return the player
	 */
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

	/**
	 * Gets the player by ID
	 *
	 * @param playerUUIDPassed the player UUID passed
	 * @return the player
	 */
	@GetMapping(path = "/getPlayer")
	public @ResponseBody Player getPlayer(@RequestParam String playerUUIDPassed, @RequestParam String playerNamePassed,
			@RequestParam Boolean isAdmin) {
		Optional<Player> players = playerRepository.findById(playerUUIDPassed);
		if (!players.isPresent()) {
			Player player = new Player();
			player.setFloor(0);
			player.setID(playerUUIDPassed);
			player.setUsername(playerNamePassed);
			player.setHealth(10);
			player.setPosX(1);
			player.setPosY(1);
			player.setIsAdmin(isAdmin);
			playerRepository.save(player);
			return player;
		} else
			return players.get();
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
