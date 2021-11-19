package fr.bobinho.cameleon.board;

import java.util.ArrayList;
import java.util.List;

import fr.bobinho.cameleon.selectable.SelectableColor;

/**
 * The PlayableMove class in an abtraction of a move made by a player and its consequences (affected Squares, new score...).
 * 
 * @author Kylian GERARD and Quentin GOMES DOS RIES
 * @version 1.0
 */
public class PlayableMove {

	//The largest region acquired with this move (null if no region will be acquired).
	private QuadTree largestRegionAcquired;
	
	//The list of all the Squares concerned by this move.
	private List<Square> affectedSquares;
	
	//The color of the player who wins these Squares (can be different from the player who made this move).
	private SelectableColor color;
	
	/**
	 * Creates a PlayableMove with an acquired region.
     * @param largestRegionAcquired
     *           QuadTree - The largest region acquired with this move.
     * @param affectedSquares
     *           List<Square> - The list of all the Squares concerned by this move. 
     * @param color
     *           SelectableColor - The color of the player who wins these Squares. 
     * @see QuadTree
     * @see List  
     * @see SelectableColor
	 */
	public PlayableMove(QuadTree largestRegionAcquired, List<Square> affectedSquares, SelectableColor color) {
		this.largestRegionAcquired = largestRegionAcquired;
		this.affectedSquares = affectedSquares;
		this.color = color;
	}
	
	/**
	 * Creates a PlayableMove with an acquired region but whitout Squares.
     * @param largestRegionAcquired
     *           QuadTree - The largest region acquired with this move.
     * @param color
     *           SelectableColor - The color of the player who wins these Squares. 
     * @see QuadTree
     * @see List  
     * @see SelectableColor
	 */
	public PlayableMove(QuadTree largestRegionAcquired, SelectableColor color) {
		this.largestRegionAcquired = largestRegionAcquired;
		this.affectedSquares = new ArrayList<Square>();
		this.color = color;
	}
	
	/**
	 * Creates a PlayableMove without acquired region.
     * @param affectedSquares
     *           List<Square> - The list of all the Squares concerned by this move. 
     * @param color
     *           SelectableColor - The color of the player who wins these Squares. 
     * @see List  
     * @see SelectableColor
	 */
	public PlayableMove(List<Square> affectedSquares, SelectableColor color) {
		this.affectedSquares = affectedSquares;
		this.color = color;
	}
	
	/**
	 * Defines the largest region acquired with this move.
     * @param largestRegionAcquired
     *           QuadTree - The largest region acquired with this move.  
     * @see QuadTree          
	 */
	public void setLargestRegionAcquired(QuadTree largestRegionAcquired) {
		this.largestRegionAcquired = largestRegionAcquired;
	}
	
	/**
	 * Returns the largest region acquired with this move.
     * @return QuadTree - The largest region acquired with this move.  
     * @see QuadTree          
	 */
	public QuadTree getLargestRegionAcquired() {
		return this.largestRegionAcquired;
	}
	
	/**
	 * Returns if this move has acquired a region.
     * @return boolean - The information on whether this move has acquired a region.         
	 */
	public boolean hasAcquired() {
		return getLargestRegionAcquired() != null;
	}
	
	/**
	 * Adds a new affected Square to this move.
     * @param largestRegionAcquired
     *           Square - The new square affected by this move.  
     * @see Square          
	 */
	public void addAffectedSquare(Square affectedSquare) {
		this.affectedSquares.add(affectedSquare);
	}
	
	/**
	 * Returns all affected Squares by this move.
     * @return List<Square> - The list of affected Squares.         
	 */
	public List<Square> getAffectedSquares() {
		return this.affectedSquares;
	}
	
	/**
	 * Sets the color of the player who wins these Squares.
     * @param color
     *           SelectableColor - The color of the player.  
     * @see SelectableColor          
	 */
	public void setColor(SelectableColor color) {
		this.color = color;
	}
	
	/**
	 * Returns the color of the player who wins these Squares.
     * @return SelectableColor - The color of the player.  
     * @see SelectableColor          
	 */
	public SelectableColor getColor() {
		return this.color;
	}
	
	/**
	 * CalculeScore
	 * Returns the new score when playing this move.
     * @param oldScore
     *           int[] - The actual score. 
     * @return int[] - The new score when playing this move.       
	 */
	public int[] getNewScore(int[] oldScore) {
		int[] newScore = oldScore.clone();
		for (Square square : getAffectedSquares()) {
			if (square.getColor() == SelectableColor.getColorFromInt(1 - getColor().getPlayerNumber()))
				newScore[1 - getColor().getPlayerNumber()]--;
			if (square.getColor() != getColor())
				newScore[getColor().getPlayerNumber()]++;
		}
		return newScore;
	}
	
	/**
	 * Returns the new relative score when playing this move : Squares(Player) - Squares(Opponent).
     * @param oldScore
     *           int[] - The actual score. 
     * @return int[] - The new score when playing this move.       
	 */
	public int getRelativeNewScore(int[] oldScore) {
		int[] newScore = getNewScore(oldScore);
		return newScore[getColor().getPlayerNumber()] - newScore[1 - getColor().getPlayerNumber()];
	}
	
}