package board;

import java.util.ArrayList;
import java.util.List;

/**
 * The FastRemoveList is a data structure used to store empty boxes. The advantage of this data structure, 
 * is that it allows to add and remove a Square in constant time thanks to the emptyNumber field of the Square.
 * 
 * @see Square
 * @author Kylian GERARD and Quentin GOMES DOS RIES
 * @version 1.0
 */
public class FastRemoveList {

	// The squares list.
	private List<Square> squares;
	
	/**
	 * Creates an empty list.
	 */
	public FastRemoveList() {
		this.squares = new ArrayList<Square>();
	}
	
	/**
	 * Returns the actual squares list.
     * @return List<Square> - The squares list.
	 * @see Square
	 * @see List
	 */
	public List<Square> getSquares() {
		return this.squares;
	}
	
	/**
	 * Returns the index of the last object in the list..
     * @return int - The index of the last Square in the list.
	 * @see Square
	 */
	public int getLastIndex() {
		return this.getSquares().size() - 1;
	}
	
	/**
	 * Returns the last Square in the list.
     * @return Square - The finded Square.
	 * @see Square
	 */
	public Square getLastElement() {
		return this.getSquares().get(getLastIndex());
	}
	
	/**
	 * Adds a Square to the list. It will be added at the end of the list.
     * @param added
     *           Square - The added Square.
	 * @see Square
	 */
	public void add(Square added) {
		this.getSquares().add(added);
		added.setEmptyNumber(getLastIndex());
	}
	
	/**
	 * Removes a Square from the list. The last element of the list will be moved to the removed index, 
	 * then the last element of the list (duplicate) will be removed.
     * @param removed
     *           Square - The removed Square.
	 * @see Square
	 */
	public void remove(Square removed) {
		this.getLastElement().setEmptyNumber(removed.getEmptyNumber());
		this.getSquares().set(removed.getEmptyNumber(), getLastElement());
		this.getSquares().remove(getLastIndex());
		removed.setEmptyNumber(-1);
	}
	
}