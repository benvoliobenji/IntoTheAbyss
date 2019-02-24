package app.player;

import java.util.ArrayList;
import app.items.Item;

public class Inventory {
	private Integer ownerID;
	private Integer maxSize;
	private ArrayList<Item> items;
	
	public Inventory(){
		items = new ArrayList<Item>();
	}
	
	public Inventory(Integer ID) {
		ownerID = ID;
		items = new ArrayList<Item>();
	}
	
	public Integer getOwnerID() {
		return ownerID;
	}
	
	public void setOwnerID(Integer ID) {
		ownerID = ID;
	}
	
	public Integer getMaxSize() {
		return maxSize;
	}
	
	public void setMaxSize(Integer newSize) {
		maxSize = newSize;
	}
}
