package utils;

public class Point {

	private float x;
	private float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public Point translate(int translation) {
		return new Point(getX() + translation, getY() + translation);
	}
	
	@Override
	public String toString() {
		return getX() + " - " + getY();
	}
}
