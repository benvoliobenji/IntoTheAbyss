package app.db;


import app.level.Level;
import app.world.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/levels")
public class LevelController {
    @Autowired
    private LevelRepository levelRepository;

    @GetMapping(path = "/get")
    public @ResponseBody Level getLevel(@RequestParam Integer id){

        if (levelRepository.existsById(id)) {
            return levelRepository.findById(id).get();
        } else {
            Level l = new Level(id);
            if (id % 5 == 0) {
                l.fillGridForDefaultMap();
            }
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
