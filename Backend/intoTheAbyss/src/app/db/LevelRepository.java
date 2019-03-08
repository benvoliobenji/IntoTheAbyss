package app.db;

import app.level.Level;
import org.springframework.data.repository.CrudRepository;

public interface LevelRepository extends CrudRepository<Level, Integer> {
}
