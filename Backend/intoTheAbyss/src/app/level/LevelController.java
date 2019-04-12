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

@Controller
@RequestMapping(path = "/levels")
public class LevelController {
	@Autowired
	private LevelRepository levelRepository;

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

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Level> getLevels() {
		return levelRepository.findAll();
	}

	@GetMapping(path = "/clear")
	public @ResponseBody String clearLevels() {
		levelRepository.deleteAll();
		return "Cleared levels.";
	}

	@GetMapping(path = "/count")
	public @ResponseBody Long countLevels() {
		return levelRepository.count();
	}
}