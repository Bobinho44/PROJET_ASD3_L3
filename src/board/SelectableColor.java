package board;

import java.awt.Color;

public enum SelectableColor {
	RED('B', Color.RED, 0),
	BLUE('R', Color.BLUE, 1),
	WHITE('A', Color.WHITE, 2);
	
	private int playerNumber;
	private Color paintColor;
	private char nameChar;
	
	private SelectableColor(char nameChar, Color paintColor, int playerNumber) {
		this.playerNumber = playerNumber;
		this.paintColor = paintColor;
		this.nameChar = nameChar;
	}
	
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