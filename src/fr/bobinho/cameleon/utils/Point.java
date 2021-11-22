package fr.bobinho.cameleon.utils;

/**
 * The Point class represents a point of dimension two on a plane.
 * @author Kylian GERARD and Quentin GOMES DOS REIS
 * @version 1.0
 */
public class Point {

	/*
	 * Fiels of point's coordinates.
	 */
	private float x;
	private float y;
	
	/**
	 * Creates a Point.
     * @param x
     *           float - The x coordinate of a point.
     * @param y
     *           float - The y coordinate of a point.           
	 */
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the x coordinate of a Point.
     * @return float - The x coordinate of this point.       
	 */
	public float getX() {
		return this.x;
	}
	
	/**
	 * Returns the y coordinate of a Point.
     * @return float - The y coordinate of this point.       
	 */
	public float getY() {
		return this.y;
	}
	
	/**
	 * Returns a new Point translated by the vector (translation, translation).
     * @param translation
     *           int - The length of displacement of the coordinates of this point.   
     * @return Point - The translated point.       
	 */
	public Point translate(int translation) {
		return new Point(getX() + translation, getY() + translation);
	}

}