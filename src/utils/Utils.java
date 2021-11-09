package utils;

public class Utils {
	
	public static Point javaPointToPoint(java.awt.Point point) {
		return new Point(point.x, point.y);
	}
	
	public static boolean isBeetwen(Point p1, Point p2, Point p3) {
		return p3.getX() >= p1.getX() && p3.getX() <= p2.getX() && p3.getY() >= p1.getY() && p3.getY() <= p2.getY();
	}
	
	public static boolean isBeetwen(int from, int to, int tested) {
		return tested >= from && tested <= to;
	}
	
	public static boolean isValidIndex(int x, int y, int maxX, int maxY) {
		return x >= 0 && y >= 0 && x < maxX && y < maxY;
	}
	
	public static boolean isValidGameBoardSize(String boardSize, int... expectedValue) {
		if (boardSize != null && boardSize.matches("^[0-9]+$")) {
			int intValue = Integer.valueOf(boardSize);
			return isBeetwen(1, 99, intValue) && ((intValue/3 & (intValue/3 - 1)) == 0) && (expectedValue.length == 1 ? intValue == expectedValue[0] : true);
		}
		return false;
	}
	
}
