package network.actions;

public class Attack extends Action {
	private String attackedId;
	private int dmg;

	public Attack() {

	}

	public String getAttackedId() {
		return attackedId;
	}

	public void setAttackedId(String attackedId) {
		this.attackedId = attackedId;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}
}
