package exp2;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalTime;

@RestController
public class Data {

    @RequestMapping("/")
    public String testIndex() {
        return "Hello";
    }

    @RequestMapping("/time")
    public String getTime() {
        return LocalTime.now().toString();
    }

}