package board.region;

import board.SelectableColor;
import player.Player;

import java.awt.Point;

public abstract class Region {
	
	private Point coordinates;
	private SelectableColor color;
	
	public Region(Point coordinates, SelectableColor color) {
		this.coordinates = coordinates;
		this.color = color;
	}
	
	public SelectableColor getColor() {
		return this.color;
	}
	
	public void setColor(SelectableColor color) {
		this.color = color;
	}
	
	public Point getCoordinates() {
		return this.coordinates;
	}
	
	public boolean isAcquired(Player player) {
		return getColor() == player.getColor();
	}
	
	public static Region createAllRegions(Point p1, Point p2) { 
		if (p1.x - p1.x <= 3) {
			return new LittleRegion(new Point((p2.x - p1.x) / 2, (p2.y - p1.y) / 2), SelectableColor.WHITE);
		}
		BigRegion region = new BigRegion(new Point((p2.x - p1.x) / 2, (p2.y - p1.y) / 2), SelectableColor.WHITE);
		/*for (int i = 0; i < 4; i++) {
			region.setSubRegion(i, region);
		}*/
		region.setSubRegion(0, createAllRegions(p1, region.getCoordinates()));
		region.setSubRegion(1, createAllRegions(region.getCoordinates(), new Point(p1.y, p2.x)));
		region.setSubRegion(2, createAllRegions(new Point(p1.x, p2.y), region.getCoordinates()));
		region.setSubRegion(3, createAllRegions(region.getCoordinates(), p2));
		return region;
	}

}
