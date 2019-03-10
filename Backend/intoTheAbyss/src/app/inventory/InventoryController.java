package app.inventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.db.InventoryRepository;

@Controller
@RequestMapping(path = "/inventory")
public class InventoryController {
	@Autowired
	private InventoryRepository inventoryRepository;
	
	/*@GetMapping(path = "/getByOwner")
	public @ResponseBody Interable<Inventory> getByOwner(@RequestParam Integer playerid) {
		
		
		
	}*/

}
