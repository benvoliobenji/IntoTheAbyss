package network.actions;

public class Action {
	private String performerID;
	private ActionTypes actionType;
	private int floor;
	private String payload;

	public Action() {

	}

	public Action(String pID, ActionTypes actionType, int floor) {
		performerID = pID;
		this.actionType = actionType;
		this.floor = floor;
	}

	public String getPerformerID() {
		return performerID;
	}

	public void setPerformerID(String performerID) {
		this.performerID = performerID;
	}

	public ActionTypes getActionType() {
		return actionType;
	}

	public void setActionType(ActionTypes actionType) {
		this.actionType = actionType;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

}
