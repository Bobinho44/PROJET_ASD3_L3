package application;

import java.util.ArrayList;
import java.util.List;

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
		List<Square> neighbors = new ArrayList<Square>();
		for (int x = 0; x < 9; x++) {
			if (Utils.isValidIndex(i + (x / 3 - 1), j + (x % 3 - 1), getBoardSize(), getBoardSize())) {
				Square square = view.getGameBoard().getSquare(i + (x / 3 - 1), j + (x % 3 - 1));
				if (square.getColor() == SelectableColor.WHITE) {
					neighbors.add(square);
				}
			}
		}
		return neighbors;
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