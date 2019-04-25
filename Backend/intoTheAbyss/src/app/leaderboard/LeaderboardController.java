package app.leaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.db.LeaderboardRepository;

/**
 * Leaderboard API controller, in which we handle requests to /leaderboard This
 * should be absolutely
 */
@Controller
@RequestMapping(path = "/leaderboard")
public class LeaderboardController {

	/** The leaderboard repository. */
	@Autowired
	LeaderboardRepository leaderboardRepository;

	/**
	 * Gets a list of leaderboard objects.
	 *
	 * @return the leaderboard
	 */
	@GetMapping(path = "/get")
	public @ResponseBody Iterable<Leaderboard> getLeaderboard() {

		return leaderboardRepository.findAll();
	}

}
