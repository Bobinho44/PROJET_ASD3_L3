package board;

import java.util.ArrayList;
import java.util.List;

import application.View;
import selectable.SelectableColor;
import selectable.SelectableRule;
import utils.Point;
import utils.Utils;

/*TODO:
 * - Implémenter stratégie intelligente
 * - Peut être un petit problème au niveau de la couleur d'une grosse sous region ou on clique pour acquired, ça la modifie pas.
 */

/**
 * The GameBoard class represents the game board including Squares (empty or not) and regions.
 * 
 * @author Kylian GERARD and Quentin GOMES DOS RIES
 * @version 1.0
 */
public class GameBoard {

	//The list with all empty Squares (colored in white).
	private FastRemoveList emptySquares;
	
	//The 2d array with all board Squares (Square[0][0] is the top-left corner of the board).
	private Square[][] board;
	
	//An abtraction of the regions of the game board, and their sub regions.
	private QuadTree regions;
	
	//The actual score.
	private int[] score;
	
	/*
	 * RemplirPlateau
	 */
	/**
	 * Creates a GameBoard with Squares and regions so that the game board can be properly used to start playing.
     * @param boardGenerationSeed
     *           String - The board generation seed generated with a file.
     * @param gameRule
     *           SelectableRule - The selected game rule.
     * @param boardSize
     *           int - The length in number of squares of one side of the board.. 
     * @see String
     * @see SelectableRule
	 */
	public GameBoard(String boardGenerationSeed, SelectableRule gameRule, int boardSize) {
		this.emptySquares = new FastRemoveList();
		this.board = new Square[boardSize][boardSize];
		this.score = new int[2];
		
		//Resizes view dimension to remove rounding error.
		View.SQUARE_SIZE = (((View.GAMEBOARD_SIZE - 1) / boardSize)) - 1;
		View.GAMEBOARD_SIZE = boardSize * (View.SQUARE_SIZE + 1) + 1;
		
		//Creates squares
		for (int i = 0; i < boardSize * boardSize; i++) {
			SelectableColor color = boardGenerationSeed.equals("") ? SelectableColor.WHITE : SelectableColor.getColorFromChar(boardGenerationSeed.charAt(i));
			setSquare(i / boardSize, i % boardSize, color);
			
			//Adds uncolored square into the empty square list or adds a point to the player associated with this color square.
			if (color == SelectableColor.WHITE) {
				addEmptySquare(getSquare(i / boardSize, i % boardSize));
			}
			else {
				updateScore(color.getPlayerNumber(), SelectableColor.WHITE.getPlayerNumber());
			}
		}
		
		//Creates regions.
		if (gameRule == SelectableRule.RECKLESS) {
			this.regions = createAllRegions(new Point((float) (getBoardSize() - 1)/2, (float) (getBoardSize() - 1)/2), 0);
			
			//Manages the regions potentially already acquired via the generation of the board by file.
			if (!boardGenerationSeed.equals("")) {
				acquiredInitialRegions();
			}
		}
	}
	
	/**
	 * Adds a Square to the empty Square list.
     * @param added
     *           Square - The added Square.   
     * @see Square
	 */
	private void addEmptySquare(Square added) {
		getEmptySquares().add(added);
	}
	
	/**
	 * Removes a Square from the empty Square list.
     * @param removed
     *           Square - The removed Square.   
     * @see Square
	 */
	private void removeEmptySquare(Square removed) {
		getEmptySquares().remove(removed);
	}
	
	/**
	 * Returns the empty Squares data structure.
     * @return FastRemoveList - The empty Squares's structure.  
     * @see Square
     * @see FastRemoveList
	 */
	private FastRemoveList getEmptySquares() {
		return this.emptySquares;
	}
	
	/**
	 * Returns an iterable list of all empty Squares.
     * @return List<Square> - The empty Squares's list.   
     * @see Square
     * @see List
	 */
	public List<Square> getIterableEmptySquares() {
		return getEmptySquares().getSquares();
	}
	
	
	/**
	 * Updates the Squares's color and region's color and manages acquired status and the score. 
     * @param move
     *           PlayableMove - A move played by a player.  
     * @see Square
     * @see PlayableMove
	 */
	public void updateBoard(PlayableMove move) {
		
		//Update the score.
		setScore(move.getNewScore(getScore()));
		
		//Changes the affected squares's color and acquired status
		for (Square square : move.getAffectedSquares()) {
			if (move.hasAcquired()) {
				square.acquired(true);
			}
			if (square.getColor() == SelectableColor.WHITE) {
				removeEmptySquare(square);
			}
			square.setColor(move.getColor());
		}
		
		//Acquired the largest region affected by this move. This region become a leaf of the QuadTree.
		if (move.hasAcquired()) {
			move.getLargestRegionAcquired().acquired(move.getColor());
		}
	}
	
	/**
	 * Returns the game board with all Squares.
     * @return Square[][] - The board.   
     * @see Square
	 */
	public Square[][] getBoard() {
		return this.board;
	}
	
	/**
	 * Returns the size of one side of the board.
     * @return int - The size of the board.   
	 */
	public int getBoardSize() {
		return getBoard().length;
	}
	
	/**
	 * Returns the Square at coordinates (i, j).
     * @param i
     *           int - The x-coordinate of the selected Square.   
     * @param j
     *           int - The y-coordinate of the selected Square.   
     * @return Square - The selected Square.
     * @see Square
	 */
	public Square getSquare(int i, int j) {
		return board[i][j];
	}
	
	/**
	 * Creates a new Squares and adds it on the board.
     * @param i
     *           int - The x-coordinate of the selected Square.   
     * @param j
     *           int - The y-coordinate of the selected Square.
     * @param color
     *           SelectableColor - The Square color.   
     * @see Square
     * @see SelectableColor
	 */
	public void setSquare(int i, int j, SelectableColor color) {
		this.getBoard()[i][j] = new Square(color, new Point(i, j));
	}
	
	/**
	 * Returns all Square's neighbors with a specific condition.
     * @param i
     *           int - The x-coordinate of the selected Square.   
     * @param j
     *           int - The y-coordinate of the selected Square.
     * @param condition
     *           String - The selection condition.  
     * @return List<Square> - All Square's neighbors that validate a specific condition.
     * @see String
     * @see Square
     * @see List
	 */
	public List<Square> getNeighbors(int i, int j, String condition) {
		List<Square> neighbors = new ArrayList<Square>();
		
		//Checks all square(i, j)'s neighbors.
		for (int x = 0; x < 9; x++) {
			
			//The selected point must be part of the game board.
			if (Utils.isValidIndex(i + (x / 3 - 1), j + (x % 3 - 1), getBoardSize(), getBoardSize())) {
				Square square = getSquare(i + (x / 3 - 1), j + (x % 3 - 1));
				
				switch (condition) {
				
				//Returns neighbors with brave rule.
				case "Brave":
					if (square.getColor() != SelectableColor.WHITE || square.getX() == i && square.getY() == j) {
						neighbors.add(square);
					}
					break;
					
				//Returns neighbors with reckless rule (without acquiring).
				case "Reckless":
					if (square.getX() == i && square.getY() == j || (square.getColor() != SelectableColor.WHITE && !square.isAcquired())) {
						neighbors.add(square);
					}
					break;
					
				//Returns all colored neighbors.	
				case "Colored":
					if (square.getColor() != SelectableColor.WHITE) {
						neighbors.add(square);
					}
					break;
					
				//Returns all neighbors with a specific color, condition must be a valid player number (between 0 and 2).
				default:
					if (square.getColor().getPlayerNumber() == Integer.valueOf(condition)) {
						neighbors.add(square);
					}
					break;
				}
			}
		}
		return neighbors;
	}
	
	/**
	 * Returns a tree representing all the regions and sub-regions, created recursively, of the game board. 
     * @param center
     *           Point - The center of the region.
     * @param heigth
     *           int - The actual heigth of the tree.  
     * @see Point
     * @see QuadTree  
	 */
	public QuadTree createAllRegions(Point center, int heigth) {
		
		//The power giving the size of the array in the formula: size = 3 * 2^n. Also set the height of the tree.
		int powerOfGenerationBoard = (int) (Math.log(getBoardSize()) / Math.log(2)) - 1;
		
		//Creates the tree's leaf
		if (heigth >= powerOfGenerationBoard) {
			return new QuadTree(center, powerOfGenerationBoard - heigth);
		}
		
		//Create internal nodes.
		else {
			QuadTree bigRegion = new QuadTree(center, powerOfGenerationBoard - heigth);
			
			//The length of displacement to be made, on both coordinates, from the center of a region to reach the center of a sub region.
			double translation = 3 * ( (Math.pow(2, powerOfGenerationBoard - heigth)) / 4);
			
			//The coordinates of the new sub-regional centers according to the selected sub-region.
			float littleValueX = (float) (center.getX() - translation);
			float bigValueX = (float) (center.getX() + translation);
			float littleValueY = (float) (center.getY() - translation);
			float bigValueY = (float) (center.getY() + translation);
			
			//Creates sub region (0: up-left; 1: up-right; 2: down-left; 3: down-right).
			bigRegion.addSubTree(0, createAllRegions(new Point(littleValueX, littleValueY), heigth + 1));
			bigRegion.addSubTree(1, createAllRegions(new Point(bigValueX, littleValueY), heigth + 1));
			bigRegion.addSubTree(2, createAllRegions(new Point(littleValueX, bigValueY), heigth + 1));
			bigRegion.addSubTree(3, createAllRegions(new Point(bigValueX, bigValueY), heigth + 1));
			return bigRegion;
		}
	}
	
	/**
	 * Manages the regions potentially already acquired via the generation of the board by file. 
	 */
	public void acquiredInitialRegions() {
		
		//Checks all little region's center
		for (int i = 0; i < Math.pow(getBoardSize() / 3, 2); i++) {
			
			//Gets little region center
			Square littleRegionCenter = getSquare(3 * (i / (getBoardSize() / 3)) + 1, 3 * (i % (getBoardSize() / 3)) + 1);
			
			//Checks if a little region is totally colored with one color. If a region is totally colored with two colors, the region will not be acquired.
			if (littleRegionCenter.getColor() != SelectableColor.WHITE) {
				List<Square> neigbours = getNeighbors(littleRegionCenter.getX(), littleRegionCenter.getY(), "" + littleRegionCenter.getColor().getPlayerNumber());
				
				//The region must be acquired
				if (neigbours.size() == 9) {
					updateBoard(acquiredSquares(littleRegionCenter.getX(), littleRegionCenter.getY(), littleRegionCenter.getColor()));
				}
			}	
		}
	}
	
	/**
	 * RemplirRegion
	 * Returns a move used with reckless rule to try to acquire regions.
    * @param i
     *           int - The x-coordinate of the selected Square.   
     * @param j
     *           int - The y-coordinate of the selected Square.
	 * @param color
     *           SelectableColor - The color of the player who played this move.
	 * @param usedTree
     *           QuadTree... - The node of the tree. Used to browse the tree. (optionnal: will use the root of the tree otherwise).
	 * @return PlayableMove - All information associated with the use this square with the reckless rule.
	 * @see SelectableColor
	 * @see QuadTree
	 * @see Square
	 * @see PlayableMove
	 */
	public PlayableMove acquiredSquares(int i, int j, SelectableColor color, QuadTree... usedTree) {
		QuadTree tree = usedTree.length == 1 ? usedTree[0] : getRegions();
		
		//Checks if a little region will be totally colored with this move
		if (tree.isLeaf()) {
			List<Square> affectedSquare = getNeighbors((int) tree.getX(), (int) tree.getY(), "Colored");
			affectedSquare.add(getSquare(i, j));
			
			//Creates the begin of the move with an acquired region if necessary
			return new PlayableMove(affectedSquare.size() > 8 ? tree : null, affectedSquare, color);
			
		}
		
		//Finds the Square(i, j)'s region and acquired large region if necessary
		else {
			
			//The number of the selected sub tree according to the coordinates of the square you are looking for
			int selectedTreeNumber = (i < tree.getX() ? 0 : 1) + (j < tree.getY() ? 0 : 2);
			PlayableMove move = acquiredSquares(i, j, color, tree.getSubTree(selectedTreeNumber));
			
			//If this move has generated an acquisition in a sub-region, we check if the current region should not also be acquired
			if (move.hasAcquired()) {
				int[] whoAcquired = new int[2];
				
				//Gets the sub region acquired by the player
				for (int k= 0; k < 4; k++) {
					if (tree.getSubTree(k).isAcquired()) {
						whoAcquired[tree.getSubTree(k).getAcquiredColor().getPlayerNumber()]++;
					}
				}
				
				//If three of the four direct sub-regions (the largest area acquired is just one level down) are acquired 
				//(the fourth acquired region being the one we just played)
				if (whoAcquired[0] + whoAcquired[1] == 3 && tree.getLevel() - 1 == move.getLargestRegionAcquired().getLevel()) {
					
					//This sub-region is acquired according to the R4-R5 rule
					move.setLargestRegionAcquired(tree);
					move.setColor(whoAcquired[color.getPlayerNumber()] > 0 ? color : SelectableColor.getColorFromInt(1 - color.getPlayerNumber()));
					
					//Acquired all sub-regions of this region without the one where the player has just played, being already added in the move, nor the sub regions
					//already acquired by the right player
					for (int k= 0; k < 4; k++) {
						if (k != selectedTreeNumber &&  tree.getSubTree(k).getAcquiredColor() != move.getColor()) {
							int size = (int) (3 * Math.pow(2, tree.getSubTree(k).getLevel()));
							
							//Gets all unacquired Squares of the sub-regions of the largest acquired region by the acquired player
							for (int x = 0; x < Math.pow(size, 2); x++) {
								QuadTree subTree = tree.getSubTree(k);
								Square square = getSquare((int) (subTree.getX() + x / size + (-size + 1) / 2), (int) (subTree.getY() + x % size + (-size + 1) / 2));
								move.addAffectedSquare(square);
							}
						}
					}
				}
			}
			return move;
		}
	}
	
	/**
	 * Returns an abtraction of the regions of the game board, and their sub regions.
	 * @return QuadTree - A tree representation of the regions.
	 * @see QuadTree
	 */
	public QuadTree getRegions() {
		return this.regions;
	}
	
	/**
	 * Replaces the current score. Used to define the new score calculated by a PlayableMove. 
     * @param score
     *           int[] - The new score.  
     * @see PlayableMove
	 */
	private void setScore(int[] score) {
		this.score = score;
	}
	
	/**
	 * Updates the current score. Used only during the game board creation to set initial points with a file board's generation. 
     * @param winnerPlayerNumber
     *           int - The player number of the Square's claimer.
     * @param looserPlayerNumber
     *           int - The player number of the Square's unclaimer.  
     * @see Square   
	 */
	private void updateScore(int winnerPlayerNumber, int looserPlayerNumber) {
		if (winnerPlayerNumber < 2 ) getScore()[winnerPlayerNumber]++;
		if (looserPlayerNumber < 2 ) getScore()[looserPlayerNumber]--;
	}
	
	/**
	 * Returns the game actuel score. 
     * @return int[] - The score of the players.
	 */
	public int[] getScore() {
		return this.score;
	}
	
	/**
	 * Returns if the game is finished or not (all Squares are colored). 
     * @return boolean - The finished game's status.
     * @see Square
	 */
	public boolean isFinish() {
		return getScore()[0] + getScore()[1] == Math.pow(getBoardSize(), 2);
	}
	
}