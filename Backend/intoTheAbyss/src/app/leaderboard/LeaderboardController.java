package app.leaderboard;


import app.db.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path = "/leaderboard")
public class LeaderboardController {
    @Autowired
    LeaderboardRepository leaderboardRepository;

    @GetMapping(path = "/get")
    public @ResponseBody Iterable<Leaderboard> getLeaderboard(){

        return leaderboardRepository.findAll();
    }


}
