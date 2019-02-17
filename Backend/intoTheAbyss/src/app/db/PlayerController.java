package app.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import app.player.Player;

@Controller
@RequestMapping(path="/players")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping(path="/add")
    public @ResponseBody String addPlayer (@RequestParam Integer playerid) {
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

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Player> getPlayers() {
        return playerRepository.findAll();
    }

    @GetMapping(path="/clear")
    public @ResponseBody String clearPlayers() {
        playerRepository.deleteAll();
        return "Cleared players";
    }
}
