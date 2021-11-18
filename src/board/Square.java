package board;

import java.awt.Graphics;
import java.awt.Color;

import application.View;
import selectable.SelectableColor;
import utils.Point;

/**
 * The Square class represents the squares of the game board.
 * 
 * @author Kylian GERARD and Quentin GOMES DOS RIES
 * @version 1.0
 */
public class Square {
	
	//The color of the Square (white is uncolored).
	private SelectableColor color;
	
	//Tell if the Square is in a acquired region.
	private boolean isAcquired;
	
	//The coordinates of the Square on the game board.
	private Point coordinates;
	
	//The Square index in the empty square list (if the Square is white).
	private int emptyNumber;
	
	/**
	 * Creates a Square.
     * @param color
     *           SelectableColor - The color of the Square.
     * @param coordinates
     *           Point - The coordinates of the Square on the game board.   
     * @see SelectableColor
     * @see Point        
	 */
	public Square(SelectableColor color, Point coordinates) {
		this.color = color;
		this.isAcquired = false;
		this.coordinates = coordinates;
		this.emptyNumber = -1;
	}
	
	/**
	 * Defines the Square's color.
     * @param color
     *           SelectableColor - The color of the Square.  
     * @see SelectableColor           
	 */
	public void setColor(SelectableColor color) {
		this.color = color;
	}
	
	/**
	 * Returns the Square's color.
     * @return SelectableColor - The color of the Square.
     * @see SelectableColor  
	 */
	public SelectableColor getColor() {
		return this.color;
	}
	
	/**
	 * Sets the square's acquired status.
     * @param isAcquired
     *           boolean - The Square's acquired status.   
	 */
	public void acquired(boolean isAcquired) {
		this.isAcquired = isAcquired;
	}
	
	/**
	 * Returns the Square's acquired status.
     * @return boolean - The Square's acquired status.
	 */
	public boolean isAcquired() {
		return this.isAcquired;
	}
	
	/**
	 * Returns the Square's coordinates.
     * @return Point - The Square's coordinates.
     * @see Point
	 */
	private Point getCoordinates() {
		return this.coordinates;
	}
	
	/**
	 * Returns the Square's x-coordinates.
     * @return int - The Square's x-coordinates.
	 */
	public int getX() {
		return (int) getCoordinates().getX();
	}
	
	/**
	 * Returns the Square's y-coordinates.
     * @return int - The Square's y-coordinates.
	 */
	public int getY() {
		return (int) getCoordinates().getY();
	}
	
	/**
	 * Sets the Square index in the empty square list.
     * @param emptyNumber
     *           int - The new empty list's Square index.   
	 */
	public void setEmptyNumber(int emptyNumber) {
		this.emptyNumber = emptyNumber;
	}
	
	/**
	 * Returns the Square index in the empty square list.
     * @return int - The empty list's Square index.   
	 */
	public int getEmptyNumber() {
		return this.emptyNumber;
	}
	
	/**
	 * Draws the Square in the drawing area with this informations (color, coordinates).
     * @param g
     *           Graphics - The drawing area.  
	 */
	public void draw(Graphics g) {
		g.setColor(getColor().getPaintColor());
		int x = (int) (View.GAMEBOARD_TOP_LEFT_CORNER.getX() + getX() * (View.SQUARE_SIZE + 1) + 1);
		int y = (int) (View.GAMEBOARD_TOP_LEFT_CORNER.getY() + getY() * (View.SQUARE_SIZE + 1) + 1);
		g.fillRect(x, y, View.SQUARE_SIZE, View.SQUARE_SIZE);
		g.setColor(Color.BLACK);
		//g.setFont(new Font("TimesRoman", Font.PLAIN, View.SQUARE_SIZE/6)); 
		if (getY() == 0) g.drawString("" + (getX() + 1), x + View.SQUARE_SIZE / 2, y - 15);
		if (getX() == 0) g.drawString("" + (getY() + 1), x - 25, y + View.SQUARE_SIZE / 2);
	}
	
}