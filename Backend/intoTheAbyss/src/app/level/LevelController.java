package app.level;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.db.LevelRepository;
import app.room.Room;

// TODO: Auto-generated Javadoc
/**
 * The Class LevelController.
 */
@Controller
@RequestMapping(path = "/levels")
public class LevelController {
	
	/** The level repository. */
	@Autowired
	private LevelRepository levelRepository;

	/**
	 * Gets the level.
	 *
	 * @param id the id
	 * @return the level
	 */
	@GetMapping(path = "/get")
	public @ResponseBody Level getLevel(@RequestParam Integer id) {
		Optional<Level> level = levelRepository.findById(id);
		if (level.isPresent()) {
			return level.get();
		} else {
			Level l = new Level(id, new Room(new Random()));
			levelRepository.save(l);
			return l;
		}
	}

	/**
	 * Gets the levels.
	 *
	 * @return the levels
	 */
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Level> getLevels() {
		return levelRepository.findAll();
	}

	/**
	 * Clear levels.
	 *
	 * @return the string
	 */
	@GetMapping(path = "/clear")
	public @ResponseBody String clearLevels() {
		levelRepository.deleteAll();
		return "Cleared levels.";
	}

	/**
	 * Count levels.
	 *
	 * @return the long
	 */
	@GetMapping(path = "/count")
	public @ResponseBody Long countLevels() {
		return levelRepository.count();
	}
}