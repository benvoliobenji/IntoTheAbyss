package app.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.db.GroupRepository;
import app.entity.player.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupController.
 */
@Controller
@RequestMapping(path = "/group")
public class GroupController {
	
	/** The group repository. */
	@Autowired
	GroupRepository groupRepository;

	/**
	 * Gets the group members.
	 *
	 * @param player the player
	 * @return the group members
	 */
	@GetMapping(path = "/all")
	public Iterable<Player> getGroupMembers(Player player) {
		return player.getGroup().getPlayers();
	}
}
