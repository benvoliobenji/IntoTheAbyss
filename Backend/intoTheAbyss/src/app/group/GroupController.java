package app.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.db.GroupRepository;
import app.entity.player.Player;

/**
 * This is the controller for for the groups api
 */
@Controller
@RequestMapping(path = "/group")
public class GroupController {

	/** The group repository. */
	@Autowired
	private GroupRepository groupRepository;

	/**
	 * Gets the group members by player.
	 *
	 * @param player the player
	 * @return the group members
	 */
	@GetMapping(path = "/all")
	public List<String> getGroupMembers(Player player) {
		return player.getGroup().getPlayers();

	}

	/**
	 * Gets the group members by playerID.
	 *
	 * @param player the player
	 * @return the group members
	 */
	@GetMapping(path = "/all")
	public List<String> getGroupMembers(String ID) {
		return groupRepository.findById(ID).get().getPlayers();

	}
}
