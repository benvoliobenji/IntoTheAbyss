package app.group;

import org.springframework.beans.factory.annotation.Autowired;

import app.db.GroupRepository;
import app.player.Player;

public class GroupController {
	@Autowired
	GroupRepository groupRepository;

	public Iterable<Player> getGroupMembers(Player player) {
		return player.getGroup().getPlayers();
	}
}
