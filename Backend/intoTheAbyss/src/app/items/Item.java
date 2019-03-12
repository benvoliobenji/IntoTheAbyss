package app.items;

import javax.persistence.*;

import app.utils.ItemTypes;

@Entity
public abstract class Item {
	@Id
	private Integer itemCode;
	private ItemTypes itemType;
	private String name, effect;
	
	public Integer getItemID() {
        return itemCode;
    }

    public void setItemID(Integer itemID) {
        this.itemCode = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemTypes getType() {
        return itemType;
    }

    public void setType(ItemTypes type) {
        this.itemType = type;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
