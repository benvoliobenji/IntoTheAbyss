package network.packets;

import network.actions.Action;

public class ActionPacket {
	private Action action;

	public ActionPacket() {

	}

	public ActionPacket(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
}
