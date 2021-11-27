package fr.bobinho.cameleon.board;

import java.util.ArrayList;
import java.util.List;

/**
 * The FastRemoveList is a data structure used to store empty boxes. The advantage of this data structure, 
 * is that it allows to add and remove a Square in constant time thanks to the emptyNumber field of the Square.
 * 
 * @see Square
 * @author Kylian GERARD and Quentin GOMES DOS REIS
 * @version 1.0
 */
public class FastRemoveList {

	// The squares list.
	private List<Square> squares;
	
	private String type;
	
	/**
	 * Creates an empty list.
     * @param type
     *           String - The type of data used with this list ("Squares" or "Regions").
     * @see String
	 */
	public FastRemoveList(String type) {
		this.squares = new ArrayList<Square>();
		this.type = type;
	}
	
	/**
	 * Returns the actual squares list.
     * @return List - The squares list.
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
		return getSquares().size() - 1;
	}
	
	/**
	 * Returns the last Square in the list.
     * @return Square - The finded Square.
	 * @see Square
	 */
	public Square getLastElement() {
		return getSquares().get(getLastIndex());
	}
	
	/**
	 * Adds a Square to the list. It will be added at the end of the list.
     * @param added
     *           Square - The added Square.
	 * @see Square
	 */
	public void add(Square added) {
		getSquares().add(added);
		setEmptyNumber(added, getLastIndex());
	}
	
	/**
	 * Removes a Square from the list. The last element of the list will be moved to the removed index, 
	 * then the last element of the list (duplicate) will be removed.
     * @param removed
     *           Square - The removed Square.
	 * @see Square
	 */
	public void remove(Square removed) {
		setEmptyNumber(getLastElement(), getEmptyNumber(removed));
		getSquares().set(getEmptyNumber(removed), getLastElement());
		getSquares().remove(getLastIndex());
		setEmptyNumber(removed, -1);
	}
	
	/**
	 * Changes the Square's empty "Type" number. (type = squares or regions)
     * @param square
     *           Square - The modified Square.
     * @param number
     *           int - The new empty number.
	 * @see Square
	 */
	private void setEmptyNumber(Square square, int number) {
		if (type.equals("Squares")) {
			square.setEmptySquaresNumber(number);
		}
		else {
			square.setEmptyLittleRegionsNumber(number);
		}
	}
	
	
	/**
	 * Returns the Square's empty "Type" number. (type = squares or regions)
     * @param square
     *           Square - The selected Square.
     * @return int - The Square's empty "Type" number. 
	 * @see Square
	 */
	private int getEmptyNumber(Square square) {
		return type.equals("Squares") ? square.getEmptySquaresNumber() : square.getEmptyLittleRegionsNumber();
	}
	
}