package board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import application.View;
import utils.Point;
import utils.Utils;

public class GameBoard {

	private Square[][] board;
	private HashSet<Square> emptySquares;
	private QuadTree regions;
	public static int[] score;
	
	/*
	 * RemplirPlateau
	 */
	public GameBoard(String boardGenerationSeed, int boardSize, SelectableRule gameRule) {
		this.board = new Square[boardSize][boardSize];
		this.emptySquares = new HashSet<Square>();
		GameBoard.score = new int[2];
		
		View.SQUARE_SIZE = (((View.GAMEBOARD_SIZE - 1) / boardSize)) - 1;
		View.GAMEBOARD_SIZE = boardSize * (View.SQUARE_SIZE + 1) + 1;
		
		for (int i = 0; i < boardSize * boardSize; i++) {
			SelectableColor color = boardGenerationSeed.equals("") ? SelectableColor.WHITE : SelectableColor.getColorFromChar(boardGenerationSeed.charAt(i));
			setSquare(i / boardSize, i % boardSize, color);
			switch (color) {
			case WHITE:
				addEmptySquare(getSquare(i / boardSize, i % boardSize));
				break;
			default:
				updateScore(color.getPlayerNumber(), SelectableColor.WHITE.getPlayerNumber());
				break;
			}
		}
		if (gameRule == SelectableRule.RECKLESS) {
			regions = createAllRegions(new Point((float) (getBoardSize() - 1)/2, (float) (getBoardSize() - 1)/2), 0);
		}
	}
	
	public void setSquaresColor(PlayableMove move) {
		setScore(move.getNewScore());
		for (Square square : move.getAffectedSquares()) {
			if (move.hasAcquired()) {
				square.acquired(true);
			}
			if (square.getColor() == SelectableColor.WHITE) {
				removeEmptySquare(square);
			}
			square.setColor(move.getColor());
		}
		if (move.hasAcquired()) {
			move.getLargestRegionAcquired().acquired(move.getColor());
		}
	}
	
	public Square[][] getBoard() {
		return this.board;
	}
	
	public HashSet<Square> getEmptySquares() {
		return this.emptySquares;
	}
	
	public void addEmptySquare(Square emptySquare) {
		getEmptySquares().add(emptySquare);
	}
	
	public void removeEmptySquare(Square emptySquare) {
		getEmptySquares().remove(emptySquare);
	}
	
	public int[] getScore() {
		return GameBoard.score;
	}
	
	public void setScore(int[] score) {
		GameBoard.score = score;
	}
	
	public void updateScore(int winnerPlayerNumber, int looserPlayerNumber) {
		if (winnerPlayerNumber < 2 ) getScore()[winnerPlayerNumber]++;
		if (looserPlayerNumber < 2 ) getScore()[looserPlayerNumber]--;
	}
	
	public int getBoardSize() {
		return getBoard().length;
	}
	
	public Square getSquare(int i, int j) {
		return board[i][j];
	}
	
	public void setSquare(int i, int j, SelectableColor color) {
		this.getBoard()[i][j] = new Square(new Point(i, j), color);
	}
	
	public List<Square> getNeighbors(int i, int j, String condition) {
		List<Square> neighbors = new ArrayList<Square>();
		for (int x = 0; x < 9; x++) {
			if (Utils.isValidIndex(i + (x / 3 - 1), j + (x % 3 - 1), getBoardSize(), getBoardSize())) {
				Square square = getSquare(i + (x / 3 - 1), j + (x % 3 - 1));
				switch (condition) {
				case "Brave":
					if (square.getColor() != SelectableColor.WHITE || square.getX() == i && square.getY() == j) {
						neighbors.add(square);
					}
					break;
				case "Reckless":
					if (square.getX() == i && square.getY() == j || (square.getColor() != SelectableColor.WHITE && !square.isAcquired())) {
						neighbors.add(square);
					}
					break;
				case "Colored":
					if (square.getColor() != SelectableColor.WHITE) {
						neighbors.add(square);
					}
				default:
					break;
				}
			}
		}
		return neighbors;
	}
	
	public QuadTree createAllRegions(Point center, int heigth) {
		int puissance = (int) (Math.log(getBoardSize()) / Math.log(2)) - 1;
		if (heigth >= puissance) {
			return new QuadTree(center, puissance - heigth);
		}
		else {
			QuadTree bigRegion = new QuadTree(center, puissance - heigth);
			float littleValueX = (float) (center.getX() - 3 * ( (Math.pow(2, puissance - heigth)) / 4));
			float bigValueX = (float) (center.getX() + 3 * ( (Math.pow(2, puissance - heigth)) / 4));
			float littleValueY = (float) (center.getY() - 3 * ( (Math.pow(2, puissance - heigth)) / 4));
			float bigValueY = (float) (center.getY() + 3 * ( (Math.pow(2, puissance - heigth)) / 4));
			bigRegion.addSubTree(0, createAllRegions(new Point(littleValueX, littleValueY), heigth + 1));
			bigRegion.addSubTree(1, createAllRegions(new Point(bigValueX, littleValueY), heigth + 1));
			bigRegion.addSubTree(2, createAllRegions(new Point(littleValueX, bigValueY), heigth + 1));
			bigRegion.addSubTree(3, createAllRegions(new Point(bigValueX, bigValueY), heigth + 1));
			return bigRegion;
		}
	}
		
	// IA pas bete
	public PlayableMove getAcquiredSquares(int i, int j, SelectableColor color, QuadTree... usedTree) {
		QuadTree tree = usedTree.length == 1 ? usedTree[0] : regions;
		if (tree.isLeave()) {
			List<Square> affectedSquare = getNeighbors((int) tree.getX(), (int) tree.getY(), "Colored");
			affectedSquare.add(getSquare(i, j));
			return new PlayableMove(affectedSquare.size() > 8 ? tree : null, affectedSquare, color);
			
		}
		else {
			int selectedTreeNumber = (i < tree.getX() ? 0 : 1) + (j < tree.getY() ? 0 : 2);
			PlayableMove move = getAcquiredSquares(i, j, color, tree.getSubTree(selectedTreeNumber));
			if (move.hasAcquired()) {
				int[] whoAcquired = new int[2];
				for (int k= 0; k < 4; k++) {
					if (tree.getSubTree(k).isAcquired()) {
						whoAcquired[tree.getSubTree(k).getAcquiredColor().getPlayerNumber()]++;
					}
				}
				if (whoAcquired[0] + whoAcquired[1] == 3 && tree.getLevel() - 1 == move.getLargestRegionAcquired().getLevel()) {
					move.setLargestRegionAcquired(tree);
					move.setColor(whoAcquired[color.getPlayerNumber()] > 0 ? color : SelectableColor.getColorFromInt(1 - color.getPlayerNumber()));
					for (int k= 0; k < 4; k++) {
						if (k == selectedTreeNumber) {
							continue;
						}
						int size = (int) (3 * Math.pow(2, tree.getSubTree(k).getLevel()));
						for (int x = 0; x < Math.pow(size, 2); x++) {
							QuadTree subTree = tree.getSubTree(k);
							Square square = getSquare((int) (subTree.getX() + x / size + (-size + 1) / 2), (int) (subTree.getY() + x % size + (-size + 1) / 2));
							move.addAffectedSquare(square);
						}
					}
				}
			}
			return move;
		}
	}
	
}