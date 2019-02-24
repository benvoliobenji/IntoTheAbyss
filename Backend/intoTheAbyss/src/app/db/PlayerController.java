package app.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import app.player.Player;

@Controller
@RequestMapping(path = "/players")
public class PlayerController {
	@Autowired
	private PlayerRepository playerRepository;

	@GetMapping(path = "/add")
	public @ResponseBody String addPlayer() {
		Player p = new Player();
		p.setFloor(-1);
		p.setUsername(-1 + "");
		p.setHealth(10);
		p.setPlayerID(0);
		p.setPosX(-1);
		p.setPosY(-1);
		playerRepository.save(p);
		return "Saved";
	}
	
	@GetMapping(path = "/addSafe")
	public @ResponseBody String addPlayer(@RequestParam Integer playerid) {
		List<Player> list = playerRepository.getPlayerByUsername(playerid + "");
		if (list.isEmpty()) {
			Player p = new Player();
			p.setFloor(0);
			p.setUsername(playerid + "");
			p.setHealth(10);
			p.setPlayerID(playerid);
			p.setPosX(1);
			p.setPosY(1);
			playerRepository.save(p);
			return "Saved";
		}
		return "Unable to save player with id " + playerid + " as this player already exists";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Player> getPlayers() {
		return playerRepository.findAll();
	}

	@GetMapping(path = "/clear")
	public @ResponseBody String clearPlayers() {
		playerRepository.deleteAll();
		return "Cleared players";
	}
}
