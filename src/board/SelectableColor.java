package board;

import java.awt.Color;

public enum SelectableColor {
	BLUE('B', Color.BLUE, 0),
	RED('R', Color.RED, 1),
	WHITE('A', Color.WHITE, 2);
	
	private char associatedChar;
	private Color paintColor;
	private int playerNumber;
	
	private SelectableColor(char associatedChar, Color paintColor, int playerNumber) {
		this.associatedChar = associatedChar;
		this.paintColor = paintColor;
		this.playerNumber = playerNumber;
	}
	
	public static SelectableColor getColorFromChar(char associatedChar) {
		switch (associatedChar) {
		case 'B':
			return BLUE;
		case 'R':
			return RED;
		default:
			return WHITE;
		}
	}
	
	public static SelectableColor getColorFromInt(int associatedInt) {
		switch (associatedInt) {
		case 0:
			return BLUE;
		case 1:
			return RED;
		default:
			return WHITE;
		}
	}
	
	public Color getPaintColor() {
		return this.paintColor;
	}
	
	public int getPlayerNumber() {
		return this.playerNumber;
	}

}