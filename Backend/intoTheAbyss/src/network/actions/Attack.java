package network.actions;

/**
 * This is a Action Type, designed to be in the payload.
 */
public class Attack {

	/** The id of the person attacked. */
	private String attackedID;

	/** The dmg. */
	private int dmg;

	/**
	 * Instantiates a new attack.
	 */
	public Attack() {

	}

	/**
	 * Gets the attacked ID.
	 *
	 * @return the attacked ID
	 */
	public String getAttackedID() {
		return attackedID;
	}

	/**
	 * Sets the attacked ID.
	 *
	 * @param attackedID the ID of the Entity attacked
	 */
	public void setAttackedID(String attackedID) {
		this.attackedID = attackedID;
	}

	/**
	 * Gets the dmg of the attack.
	 *
	 * @return the dmg
	 */
	public int getDmg() {
		return dmg;
	}

	/**
	 * Sets the dmg of the attack.
	 *
	 * @param dmg the new dmg
	 */
	public void setDmg(int dmg) {
		this.dmg = dmg;
	}
}
