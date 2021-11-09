package application;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import board.SelectableColor;
import board.SelectableRule;
import board.Square;
import utils.Utils;

public class Model {

	private View view;
	
	private String boardGenerationSeed;
	private SelectableRule gameRule;
	private int boardSize;
	private int whoMustPlay;
	private boolean isStarted;
	
	public Model() {
		this.whoMustPlay = 0;
		this.isStarted = false;
	}
	
	public void linkView(View view) {
		this.view = view;
	}
	
	public void setBoardGenerationSeed(String boardGenerationSeed) {
		this.boardGenerationSeed = boardGenerationSeed;
		this.boardSize = (int) Math.sqrt(boardGenerationSeed.length());
	}

	public String getBoardGenerationSeed() {
		return this.boardGenerationSeed;
	}
	
	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
		this.boardGenerationSeed = "";
	}
	
	public int getBoardSize() {
		return this.boardSize;
	}
	
	public void play(int i, int j) {
		//if (whoMustPlay == 1) {
		List<Square> affectedSquares = getGameRule() == SelectableRule.BRAVE ? getAffectedSquaresWithBrave(i, j) : getAffectedSquaresWithBrave(i, j);
			view.setSquaresColor(affectedSquares, SelectableColor.getColorFromInt(whoMustPlay));
			whoMustPlay = 1 - whoMustPlay;
		//}
	}
	
	public List<Square> getAffectedSquaresWithBrave(int i, int j) {
		return view.getGameBoard().getNeighbors(i, j, "Brave");
	}
	
	//TODO continuer;
	//actuellement juste récupération des cases adjacentes non acquise
	public List<Square> getAffectedSquaresWithReckless(int i, int j) {
		return view.getGameBoard().getNeighbors(i, j, "Reckless");
		
		//utiliser int... acquired pour donner la position du premier acquired (potentiellement SelectableColor... acquiredColor
		// Recherche dans le quadtree la case (i,j). Si cette case appartient à une petite region maintenant colorié -> 
		// Si 8 cases de cette petite région sont colorié: la petite region est acquise
		
		//Quand une sous region est acquise et que la region est totalement colorié:
		//Si le joueur qui a acquis, ne possède qu'une sous region, il perd toute la region
		//Sinon il gagne toute la region.
	}
	
	public Square getBestAffectedSquaresWithBrave() {
		Square selectedSquare = null;
		int affectedSquares = 0;
		for (Square square : view.getGameBoard().getEmptySquares()) {
			if (square.getColor() == SelectableColor.WHITE) {
				int affected =  getAffectedSquaresWithBrave(square.getX(), square.getY()).size();
				if (affected >= affectedSquares) {
					affectedSquares = affected;
					selectedSquare = square;
				}
				if (affectedSquares == 9) {
					return selectedSquare;
				}
			}
		}
		return selectedSquare;
	}
	
	public void setGameRule(SelectableRule gameRule) {
		this.gameRule = gameRule;
	}
	
	public SelectableRule getGameRule() {
		return this.gameRule;
	}
	
	public void start() {
		this.isStarted = true;
		view.start();
	}
	
	public void reset() {
		this.isStarted = false;
		this.boardGenerationSeed = null;
		this.gameRule = null;
		this.whoMustPlay = 0;
		view.reset();
	}
	
	public void cheat(int i, int j) {
		int[] newScore = {view.getGameBoard().getScore()[0], view.getGameBoard().getScore()[1]};
		for (Square square : getAffectedSquaresWithBrave(i, j)) {
			switch (square.getColor()) {
			case WHITE:
				newScore[0]++;
				break;
			case RED:
				newScore[0]++;
				newScore[1]--;
			default:
				break;
			}
		}
		JOptionPane.showMessageDialog(view, "New estimated score:\n" + newScore[0] + " - "  + newScore[1], "Information", JOptionPane.INFORMATION_MESSAGE);

	}
	
	public boolean isStarted() {
		return this.isStarted;
	}

	public void evolve() {
		while (true) {
			if (isStarted()) {
				if (view.getGameBoard() == null) {
					view.createGameBoard(getBoardGenerationSeed(), getBoardSize());
				}
				view.update();
			} else if (getBoardGenerationSeed() != null && getGameRule() != null) {
				view.canStart();
			}
			try {
				Thread.sleep(50);
			}
			catch(Exception e) {
				
			}
		}
	}

}