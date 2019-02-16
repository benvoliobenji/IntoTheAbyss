package app.db;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class Leaderboard {

    @GetMapping("/leaderboard")
    public String leaderboard() {
        return "lb";
    }
}

