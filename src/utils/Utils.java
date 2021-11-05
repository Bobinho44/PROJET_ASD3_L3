package utils;

import java.awt.Point;

public class Utils {

	public static Point translate(Point point, int translation) {
		Point translatedPoint = new Point(point.x, point.y);
		translatedPoint.translate(translation, translation);
		return translatedPoint;
	}
	
	public static boolean isBeetwen(Point p1, Point p2, Point p3) {
		return p3.x >= p1.x && p3.x <= p2.x && p3.y >= p1.y && p3.y <= p2.y;
	}
	
	public static boolean isValidIndex(int x, int y, int maxX, int maxY) {
		return x >= 0 && y >= 0 && x < maxX && y < maxY;
	}
	
}
