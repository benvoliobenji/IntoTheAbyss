package app.leaderboard;


import app.db.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


// TODO: Auto-generated Javadoc
/**
 * The Class LeaderboardController.
 */
@Controller
@RequestMapping(path = "/leaderboard")
public class LeaderboardController {
    
    /** The leaderboard repository. */
    @Autowired
    LeaderboardRepository leaderboardRepository;

    /**
     * Gets the leaderboard.
     *
     * @return the leaderboard
     */
    @GetMapping(path = "/get")
    public @ResponseBody Iterable<Leaderboard> getLeaderboard(){

        return leaderboardRepository.findAll();
    }


}
