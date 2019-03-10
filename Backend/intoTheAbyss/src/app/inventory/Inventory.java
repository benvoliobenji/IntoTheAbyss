package app.inventory;

import java.util.ArrayList;
import app.items.Item;

public class Inventory {
	private String ownerID;
	private Integer maxSize;
	private ArrayList<Item> items;
	
	public Inventory(){
		items = new ArrayList<Item>();
	}
	
	public Inventory(String ID) {
		ownerID = ID;
		items = new ArrayList<Item>();
	}
	
	public String getOwnerID() {
		return ownerID;
	}
	
	public void setOwnerID(String ID) {
		ownerID = ID;
	}
	
	public Integer getMaxSize() {
		return maxSize;
	}
	
	public void setMaxSize(Integer newSize) {
		maxSize = newSize;
	}
}
