package app.db;


import app.level.Level;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/levels")
public class LevelController {

    @GetMapping(path = "/get")
    public @ResponseBody Level getLevel(@RequestParam Integer id){

        return null;
    }

}
