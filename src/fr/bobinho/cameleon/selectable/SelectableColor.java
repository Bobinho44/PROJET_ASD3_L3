package fr.bobinho.cameleon.selectable;

import java.awt.Color;

/**
 * The SelectableColor enum contains all player colors .
 * 
 * @author Kylian GERARD and Quentin GOMES DOS RIES
 * @version 1.0
 */
public enum SelectableColor {
	WHITE(2, Color.WHITE, 'A'),
	BLUE(1, Color.BLUE, 'B'),
	RED(0, Color.RED, 'R');
	
	//The associated player number.
	private int playerNumber;
	
	//The color's associated drawing color.
	private Color paintColor;
	
	//The color's associated char.
	private char nameChar;
	
	/**
	 * Creates a new color.
     * @param playerNumber
     *           int - The color's associated player number.
     * @param paintColor
     *           Color- The color's associated drawing color.       
     * @param nameChar
     *           char - The color's associated char.       
	 * @see Color
	 */
	private SelectableColor(int playerNumber, Color paintColor, char nameChar) {
		this.playerNumber = playerNumber;
		this.paintColor = paintColor;
		this.nameChar = nameChar;
	}
	
	/**
	 * Returns the SelectableColor associated with a char.
     * @param associatedChar
     *           char - The color's associated char.  
     * @return SelectableColor - The color associated with the char.          
	 */
	public static SelectableColor getColorFromChar(char associatedChar) {
		for (SelectableColor color : values()) {
			if (color.getNameChar() == associatedChar) {
				return color;
			}
		}
		return WHITE;
	}
	
	/**
	 * Returns the SelectableColor associated with an int.
     * @param associatedInt
     *           int - The color's associated int.  
     * @return SelectableColor - The color associated with the int (player number).          
	 */
	public static SelectableColor getColorFromInt(int associatedInt) {
		for (SelectableColor color : values()) {
			if (color.getPlayerNumber() == associatedInt) {
				return color;
			}
		}
		return WHITE;
	}
	
	/**
	 * Returns the color's associated player number.
     * @return int - The color's associated player number.          
	 */
	public int getPlayerNumber() {
		return this.playerNumber;
	}
	
	/**
	 * Returns the color's associated drawing color. 
     * @return Color - The color's associated drawing color.    
     * @see Color      
	 */
	public Color getPaintColor() {
		return this.paintColor;
	}
	
	/**
	 * Returns the color's associated char.
     * @return char - The color's associated char.          
	 */
	private char getNameChar() {
		return this.nameChar;
	}

}