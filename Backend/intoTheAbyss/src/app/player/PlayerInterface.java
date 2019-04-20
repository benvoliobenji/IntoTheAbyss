package app.player;

import app.entity.Entity;

public interface PlayerInterface extends Entity {

	public String getUsername();

	public void setUsername(String uname);

	public Integer getHealth();

	public void setHealth(Integer health);
}
