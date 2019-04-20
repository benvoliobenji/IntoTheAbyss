package app.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.db.GroupRepository;
import app.player.Player;

@Controller
@RequestMapping(path = "/group")
public class GroupController {
	@Autowired
	GroupRepository groupRepository;

	@GetMapping(path = "/all")
	public Iterable<Player> getGroupMembers(Player player) {
		return player.getGroup().getPlayers();
	}
}
