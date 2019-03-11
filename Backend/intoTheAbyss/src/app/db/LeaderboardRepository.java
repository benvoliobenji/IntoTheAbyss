package app.db;

import app.leaderboard.Leaderboard;
import org.springframework.data.repository.CrudRepository;

public interface LeaderboardRepository extends CrudRepository<Leaderboard, Integer>{
}
