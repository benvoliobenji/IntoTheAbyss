package app.entity;

public interface Entity {
	@Override
	public String toString();

	public String getID();

	public void setID(String ID);

	public Integer getPosX();

	public void setPosX(Integer posX);

	public Integer getPosY();

	public void setPosY(Integer posY);

	public Integer getFloor();

	public void setFloor(Integer floor);
}
