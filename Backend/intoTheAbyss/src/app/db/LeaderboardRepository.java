package app.db;

import app.leaderboard.Leaderboard;
import org.springframework.data.repository.CrudRepository;

/**
 * The Interface LeaderboardRepository.
 */
public interface LeaderboardRepository extends CrudRepository<Leaderboard, Integer>{
}
