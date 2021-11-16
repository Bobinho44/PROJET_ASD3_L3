package board;

import java.awt.Color;

/**
 * The SelectableColor enum contains all player colors .
 * 
 * @author Kylian GERARD and Quentin GOMES DOS RIES
 * @version 1.0
 */
public enum SelectableColor {
	RED('B', Color.RED, 0),
	BLUE('R', Color.BLUE, 1),
	WHITE('A', Color.WHITE, 2);
	
	//The associated player number.
	private int playerNumber;
	
	//The color's associated drawing color.
	private Color paintColor;
	
	//The color's associated char.
	private char nameChar;
	
	/**
	 * Creates a new color.
     * @param nameChar
     *           char- The color's associated char.
     * @param paintColor
     *           Color- The color's associated drawing color.
     * @param playerNumber
     *           int - The color's associated player number.              
	 * @see Color
	 */
	private SelectableColor(char nameChar, Color paintColor, int playerNumber) {
		this.playerNumber = playerNumber;
		this.paintColor = paintColor;
		this.nameChar = nameChar;
	}
	
	/**
	 * Returns the Selectable color associated with a char.
     * @param nameChar
     *           char- The color's associated char.  
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
	
	public static SelectableColor getColorFromInt(int associatedInt) {
		for (SelectableColor color : values()) {
			if (color.getPlayerNumber() == associatedInt) {
				return color;
			}
		}
		return WHITE;
	}
	
	public int getPlayerNumber() {
		return this.playerNumber;
	}
	
	public Color getPaintColor() {
		return this.paintColor;
	}
	
	private char getNameChar() {
		return this.nameChar;
	}

}