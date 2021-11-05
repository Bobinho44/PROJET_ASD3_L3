package board.region;

import java.awt.Point;
import java.util.List;

import board.SelectableColor;
import board.Square;

public class LittleRegion extends Region {

	private List<Square> squares;
	
	public LittleRegion(Point coordinates, SelectableColor color) {
		super(coordinates, color);
	}
	
}