package board.region;

import java.awt.Point;

import board.SelectableColor;
import board.Square;

public class LittleRegion extends Region {

	private Square centralSquare;
	
	public LittleRegion(Point coordinates, SelectableColor color) {
		super(coordinates, color);
		this.centralSquare = centralSquare;
	}
	
}