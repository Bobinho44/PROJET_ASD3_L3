package board.region;

import java.awt.Point;
import java.util.List;

import board.SelectableColor;

public class BigRegion extends Region {

	private List<Region> subRegions;
	
	public BigRegion(Point coordinates, SelectableColor color) {
		super(coordinates, color);
	}
	
	public void setSubRegion(int i, Region subRegion) {
		subRegions.set(i, subRegion);
	}

}
