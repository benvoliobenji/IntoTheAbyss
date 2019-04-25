package network.actions;

/**
 * Data type sent to clients.
 */
public class Action {

	/** The performer ID. */
	private String performerID;

	/** The action type. */
	private ActionTypes actionType;

	/** The floor. */
	private int floor;

	/** The payload stores an instance of a action type in json format */
	private String payload;

	/**
	 * Gets the performer ID.
	 *
	 * @return the performer ID
	 */
	public String getPerformerID() {
		return performerID;
	}

	/**
	 * Sets the performer ID.
	 *
	 * @param performerID the new performer ID
	 */
	public void setPerformerID(String performerID) {
		this.performerID = performerID;
	}

	/**
	 * Gets the action type.
	 *
	 * @return the action type
	 */
	public ActionTypes getActionType() {
		return actionType;
	}

	/**
	 * Sets the action type.
	 *
	 * @param actionType the new action type
	 */
	public void setActionType(ActionTypes actionType) {
		this.actionType = actionType;
	}

	/**
	 * Gets the payload. The payload should be an instance of a action type.
	 *
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}

	/**
	 * Sets the payload.
	 *
	 * @param payload the new payload
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}

	/**
	 * Gets the floor.
	 *
	 * @return the floor
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * Sets the floor.
	 *
	 * @param floor the new floor
	 */
	public void setFloor(int floor) {
		this.floor = floor;
	}

}
