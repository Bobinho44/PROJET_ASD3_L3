package application;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Shape is the interface that defines the different shapes that will be able to follow the paths.
 * @see Path
 * 
 * @author Kylian GERARD and Eugénie SHEKA KASONGO
 * @version 1.0
 */
public interface Shape {
	
	/**
	 * Getter: 
     * Returns the position of this shape.
     * @return A Point representing the position of this shape.
     */
	public Point getPosition();
	
	/**
	 * Getter: 
     * Returns the rotation angle of this shape.
     * @return A double representing the rotation angle of this shape.
     */
	public double getRotationAngle();
	
	/**
	 * Getter: 
     * Returns the color of this shape.
     * @return A Color representing the color of this shape.
     */
	public Color getColor();
	
	/**
	 * Setter: 
     * Updates the position of this shape.
     * @param position
     *           The new position of this shape.
     */
	public void setPosition(Point position); 
	
	/**
	 * Setter: 
     * Updates the rotation angle of this shape.
     * @param angle
     *           The new rotation angle of this shape.
     */
	public void setRotationAngle(double angle); 
	
	/**
	 * Setter: 
     * Updates the color of this shape.
     * @param color
     *           The new color of this shape.
     */
	public void setColor (Color color);
	
    /**
     *  Returns if the position is included in this shape.
     *  @param position
     *           A position in our panel. (used with the position of the mouse when clicking).
     *  @return A boolean representing the inclusion of this position in this shape.
     */
	public boolean containsMouse(Point position);
	
	/**
     * Draws this shape in our panel.
     * @param g
     *           The 2D graphic in which this shape will be drawn.
     */
	public void draw(Graphics2D g);

	/**
     * Returns a clone of this shape.
     * @return A Shape representing a new square instance of this shape.
     */
	public Shape clone();
	
    /**
     *  Returns if the two shapes are identical.
     *  @param shape
     *           Another shape that we want to compare to this shape.
     *  @return A boolean representing the equivalence between these two shapes.
     */
	public boolean equals(Shape shape);
}
