package board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayableMove {

	private QuadTree largestRegionAcquired;
	private List<Square> affectedSquares;
	private SelectableColor color;
	
	public PlayableMove(QuadTree largestRegionAcquired, List<Square> affectedSquares, SelectableColor color) {
		this.largestRegionAcquired = largestRegionAcquired;
		this.affectedSquares = affectedSquares;
		this.color = color;
	}
	
	public PlayableMove(List<Square> affectedSquares, SelectableColor color) {
		this.affectedSquares = affectedSquares;
		this.color = color;
	}
	
	public PlayableMove(Square affectedSquare, SelectableColor color) {
		this.affectedSquares = new ArrayList<Square>(Arrays.asList(affectedSquare));
		this.color = color;
	}
	
	public boolean hasAcquired() {
		return this.largestRegionAcquired != null;
	}
	
	public QuadTree getLargestRegionAcquired() {
		return this.largestRegionAcquired;
	}
	
	public void setLargestRegionAcquired(QuadTree largestRegionAcquired) {
		this.largestRegionAcquired = largestRegionAcquired;
	}
	
	public List<Square> getAffectedSquares() {
		return this.affectedSquares;
	}
	
	public void addAffectedSquare(Square affectedSquare) {
		this.affectedSquares.add(affectedSquare);
	}
	
	public SelectableColor getColor() {
		return this.color;
	}
	
	public void setColor(SelectableColor color) {
		this.color = color;
	}
	
	public int[] getNewScore() {
		return calculateNewScore();
	}
	
	public int getRelativeNewScore() {
		return getNewScore()[getColor().getPlayerNumber()] - getNewScore()[1 - getColor().getPlayerNumber()];
	}
	
	public int[] calculateNewScore() {
		int[] newScore = GameBoard.score.clone();
		for (Square square : getAffectedSquares()) {
			if (square.getColor() == SelectableColor.getColorFromInt(1 - getColor().getPlayerNumber()))
				newScore[1 - getColor().getPlayerNumber()]--;
			if (square.getColor() != getColor())
				newScore[getColor().getPlayerNumber()]++;
		}
		return newScore;
	}
	
}