package fr.bobinho.cameleon.board;

import fr.bobinho.cameleon.selectable.SelectableColor;
import fr.bobinho.cameleon.utils.Point;
import fr.bobinho.cameleon.utils.Utils;

/**
 * The QuadTree class in an abtraction of the game board region (and subregion).
 * 
 * @author Kylian GERARD and Quentin GOMES DOS REIS
 * @version 1.0
 */
public class QuadTree {

	//The color of a region if it is acquired.
	private SelectableColor acquiredColor;
	
	//The sub-regions (if null, this region is a leaf of the tree).
	private QuadTree[] subTrees;
	
	//The coordinates of the central point of the region (the central square for small regions, or the intersection of the four sub-regions for others).
	private Point coordinates;
	
	//The height of the region in the tree (0 representing a leaf). Used to know the number of cells associated to this region.
	private int level;
	
	/**
	 * Creates a QuadTree.
     * @param coordinates
     *           Point - The central point of the region.
     * @param level
     *           int - The height of the region in the tree. 
     * @see Point
	 */
	public QuadTree(Point coordinates, int level) {
		this.coordinates = coordinates;
		this.level = level;
		if (level != 0) {
			this.subTrees = new QuadTree[4];
		}
	}
	
	/**
	 * Sets the square's acquired status and color.
     * @param acquiredColor
     *           SelectableColor - The Square's acquired color.   
     * @see SelectableColor
	 */
	public void acquired(SelectableColor acquiredColor) {
		this.acquiredColor = acquiredColor;
		this.subTrees = null;
	}
	
	/**
	 * Returns the Square's acquired color.
     * @return SelectableColor - The Square's acquired color.
     * @see SelectableColor
	 */
	public SelectableColor getAcquiredColor() {
		return this.acquiredColor;
	}
	
	/**
	 * Returns the Square's acquired status.
     * @return boolean - The Square's acquired status.
	 */
	public boolean isAcquired() {
		return getAcquiredColor() != null;
	}
	
	/**
	 * Adds a new sub tree at a specific position (0: up-left; 1: up-right; 2: down-left; 3: down-right).
     * @param number
     *           int - The selected sub tree position.   
     * @param subTree
     *           QuadTree - The added sub tree.   
	 */
	public void addSubTree(int number, QuadTree subTree) {
		if (Utils.isBeetwen(0, 3, number)) {
			this.subTrees[number] = subTree;
		}
	}
	
	/**
	 * Returns the sub tree at the specific position.
     * @param number
     *           int - The selected position.   
     * @return QuadTree - The sub tree at the specific position.   
	 */
	public QuadTree getSubTree(int number) {
		return this.subTrees[number];
	}
	
	/**
	 * Returns the tree's status (leaf or not).
     * @return boolean - The tree's status.
	 */
	public boolean isLeaf() {
		return this.subTrees == null;
	}
	
	/**
	 * Returns the tree's coordinates.
     * @return Point - The tree's coordinates.
     * @see Point
	 */
	private Point getCoordinates() {
		return this.coordinates;
	}
	
	/**
	 * Returns the tree's x-coordinates.
     * @return float - The tree's x-coordinates.
	 */
	public float getX() {
		return getCoordinates().getX();
	}
	
	/**
	 * Returns the tree's y-coordinates.
     * @return float - The tree's y-coordinates.
	 */
	public float getY() {
		return getCoordinates().getY();
	}
	
	/**
	 * Returns the tree's level (0 if leave).
     * @return int - The tree's level.
	 */
	public int getLevel() {
		return this.level;
	}

}