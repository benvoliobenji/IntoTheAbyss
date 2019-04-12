package app.entity;

public interface Entity {
	@Override
	public String toString();

	public Integer getPosX();

	public void setPosX(Integer posX);

	public Integer getPosY();

	public void setPosY(Integer posY);

	public Integer getFloor();

	public void setFloor(Integer floor);
}
