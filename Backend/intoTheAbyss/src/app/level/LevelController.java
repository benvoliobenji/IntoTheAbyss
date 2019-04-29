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

/**
 * Controls the API for the /levels part of our API
 */
@Controller
@RequestMapping(path = "/levels")
public class LevelController {

	/** The level repository. */
	@Autowired
	private LevelRepository levelRepository;

	/**
	 * Gets the level by id, and will create level with given ID if not found.
	 *
	 * @param id The levels ID
	 * @return Level found with ID
	 */
	@GetMapping(path = "/get")
	public @ResponseBody Level getLevel(@RequestParam Integer id) {
		Optional<Level> level = levelRepository.findById(id);
		if (level.isPresent()) {
			return level.get();
		} else {
			Level newLevel = new Level(id, new Room(new Random()));
			levelRepository.save(newLevel);
			return newLevel;
		}
	}

	/**
	 * Returns a list of all levels
	 *
	 * @return the levels
	 */
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Level> getLevels() {
		return levelRepository.findAll();
	}

	/**
	 * Deletes all levels, and sends a success message.
	 *
	 * @return the string
	 */
	@GetMapping(path = "/clear")
	public @ResponseBody String clearLevels() {
		levelRepository.deleteAll();
		return "Cleared levels.";
	}

	/**
	 * Returns the number of levels in the DB
	 *
	 * @return the long
	 */
	@GetMapping(path = "/count")
	public @ResponseBody Long countLevels() {
		return levelRepository.count();
	}
}