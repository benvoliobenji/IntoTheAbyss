package app.db;

import app.level.Level;
import org.springframework.data.repository.CrudRepository;

/**
 * The Interface LevelRepository.
 */
public interface LevelRepository extends CrudRepository<Level, Integer> {
}
