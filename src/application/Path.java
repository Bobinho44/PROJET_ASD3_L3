package application;

import java.awt.Point;

/**
 * Path is the interface that defines the different paths that Shapes will be able to follow.
 * @see Shape
 * 
 * @author Kylian GERARD and Eugénie SHEKA KASONGO
 * @version 1.0
 */
public interface Path {

	/**
	 * Getter: 
     * Returns the progression of this path.
     * @return A double representing the progression of this path.
     */
	public double getPathProgression();
	
	/**
	 * Setter: 
     * Updates the progression of this path.
     * @param progressOnPath
     *           The new progression of this path.
     */
	public void setProgressOnPath(double progressOnPath);
	
    /**
     *  Returns the position of the next point of this path.
     *  @return A Point representing the next position to progress on this path.
     */
	public Point nexPoint(); 
	
}
