package fr.bobinho.cameleon.utils;

/**
 * The Utils class is a utility class for checking the validity of data.
 * @author Kylian GERARD and Quentin GOMES DOS RIES
 * @version 1.0
 */
public class Utils {
	
	/**
	 * Returns a Java point represented by a custom implementation.
     * @param point
     *           java.awt.Point - The class point implemented by Java.   
     * @return Point - The point represented by a custom implementation.   
     * @See java.awt.Point
     * @see Point    
	 */
	public static Point javaPointToPoint(java.awt.Point point) {
		return new Point(point.x, point.y);
	}
	
	
	/**
	 * Returns whether or not a point lies between two other points on a 2D space.
     * @param p1
     *           Point - The up left point.  
     * @param p2
     *           Point - The down right point.
     * @param p3
     *           Point - The tested point.     
     * @return boolean - Whether or not a point lies between two other points on a 2D space. 
     * @see Point    
	 */
	public static boolean isBeetwen(Point p1, Point p2, Point p3) {
		return p3.getX() >= p1.getX() && p3.getX() <= p2.getX() && p3.getY() >= p1.getY() && p3.getY() <= p2.getY();
	}
	
	/**
	 * Returns whether or not a point lies between two other points on a 1D space.
     * @param from
     *           int - The lower bound.  
     * @param to
     *           int - The upper bound.
     * @param tested
     *           int - The tested value.     
     * @return boolean - Whether or not a point lies between two other points on a 1D space. 
	 */
	public static boolean isBeetwen(int from, int to, int tested) {
		return tested >= from && tested <= to;
	}
	
	/**
	 * Returns whether or not x and y are respectively less than or equal to, but positive, to maxX and maxY.
     * @param x
     *           int - The tested x.  
     * @param y
     *           int - The tested y.
     * @param maxX
     *           int - The max x value   
     * @param maxY
     *           int - The max y value.    
     * @return boolean - whether or not x and y are respectively less than or equal to, but positive, to maxX and maxY. 
	 */
	public static boolean isValidIndex(int x, int y, int maxX, int maxY) {
		return x >= 0 && y >= 0 && x < maxX && y < maxY;
	}
	
	/**
	 * Returns whether or not the board size is a valid value.
     * @param boardSize
     *           String - A representation of the number of squares per side of the game board.  
     * @param expectedValue
     *           int... - (Optional) The expected size value.   
     * @return boolean - whether or not the board size is a valid value. 
	 */
	public static boolean isValidGameBoardSize(String boardSize, int... expectedValue) {
		if (boardSize != null && boardSize.matches("^[0-9]+$")) {
			int intValue = Integer.valueOf(boardSize);
			return isBeetwen(1, 99, intValue) && ((intValue/3 & (intValue/3 - 1)) == 0) && (expectedValue.length == 1 ? intValue == expectedValue[0] : true);
		}
		return false;
	}
	
}