package app.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inventory {
    @Id
    @Column(length = 50)
    private String playerid;
    private Integer helmet, chest, boots, weapon, slot1, slot2, slot3, slot4;

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }

    public Integer getHelmet() {
        return helmet;
    }

    public void setHelmet(Integer helmet) {
        this.helmet = helmet;
    }

    public Integer getChest() {
        return chest;
    }

    public void setChest(Integer chest) {
        this.chest = chest;
    }

    public Integer getBoots() {
        return boots;
    }

    public void setBoots(Integer boots) {
        this.boots = boots;
    }

    public Integer getWeapon() {
        return weapon;
    }

    public void setWeapon(Integer weapon) {
        this.weapon = weapon;
    }

    public Integer getSlot1() {
        return slot1;
    }

    public void setSlot1(Integer slot1) {
        this.slot1 = slot1;
    }

    public Integer getSlot2() {
        return slot2;
    }

    public void setSlot2(Integer slot2) {
        this.slot2 = slot2;
    }

    public Integer getSlot3() {
        return slot3;
    }

    public void setSlot3(Integer slot3) {
        this.slot3 = slot3;
    }

    public Integer getSlot4() {
        return slot4;
    }

    public void setSlot4(Integer slot4) {
        this.slot4 = slot4;
    }
}
