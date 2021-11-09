package board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import application.View;
import utils.Utils;

public class GameBoard {

	private Square[][] board;
	private HashSet<Square> emptySquares;
	private QuadTree regions;
	private int[] score;
	
	public GameBoard(String boardGenerationSeed, int boardSize) {
		this.board = new Square[boardSize][boardSize];
		this.emptySquares = new HashSet<Square>();
		this.score = new int[2];
		
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
	}
	
	public void setSquaresColor(List<Square> squares, SelectableColor color) {
		for (Square square : squares) {
			if (square.getColor() == SelectableColor.WHITE) {
				removeEmptySquare(square);
			}
			updateScore(color.getPlayerNumber(), square.getColor().getPlayerNumber());
			square.setColor(color);
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
		return this.score;
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
					if (square.getColor() != SelectableColor.WHITE && !square.isAcquired()) {
						neighbors.add(square);
					}
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
		if (heigth == (int) (Math.log(View.GAMEBOARD_SIZE) / Math.log(2))) {
			return new QuadTree(center, true);
		}
		else {
			QuadTree bigRegion = new QuadTree(center, false);
			int a = (int) (((int) center.x) / Math.pow(2, heigth));
			int b = (int) (Math.floor(center.x) / Math.pow(2, heigth));
			int c = ((int) center.y) / 2;
			int d = (int) (Math.floor(center.y) / 2);
			//TODO CALCUL DES POS
			bigRegion.addSubTree(0, new QuadTree(new Point(a,b), false));
			bigRegion.addSubTree(1, new QuadTree(new Point(a,b), false));
			bigRegion.addSubTree(2, new QuadTree(new Point(a,b), false));
			bigRegion.addSubTree(3, new QuadTree(new Point(a,b), false));
			return bigRegion;
		}
	}
	
	public boolean parcours(int i, int j, QuadTree tree, SelectableColor color) {
		if (tree.isLeave()) {
			List<Square> littleRegionSquare = getNeighbors(tree.getCoordinates().x, tree.getCoordinates().y, "Brave");
			if (littleRegionSquare.size() == 8) {
				setSquaresColor(littleRegionSquare, color);
				for (Square square : littleRegionSquare ) {
					square.setIsAcquired(true);
				}
				return true;
			}
			return false;
			
		}
		else {
			//TODO
			QuadTree selectedTree = null;
			if (parcours(i, j, selectedTree, color)) {
				//acquired all
			}
			return false;
		}
	}
	/*public Region createBoard(Point p1, Point p2) { 
		if (p1.x - p1.x <= 3) {
			return new LittleRegion(new Point((p2.x - p1.x) / 2, (p2.y - p1.y) / 2), Color.White);
		}
		BigRegion region = new BigRegion(new Point((p2.x - p1.x) / 2, (p2.y - p1.y) / 2), Color.White);
		region.setSubRegion(0, createBoard(p1, region.getCoordinates()));
		region.setSubRegion(1, createBoard(region.getCoordinates(), new Point(p1.y, p2.x)));
		region.setSubRegion(2, createBoard(new Point(p1.x, p2.y), region.getCoordinates()));
		region.setSubRegion(3, createBoard(region.getCoordinates(), p2));
		return region;
	}*/
}