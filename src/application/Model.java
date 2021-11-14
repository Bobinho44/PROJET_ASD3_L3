package application;

import board.GameBoard;
import board.PlayableMove;
import board.SelectableColor;
import board.SelectableRule;
import board.Square;

public class Model {
	
	/*
	 * Fields of board parameters.
	 */
	private String boardGenerationSeed;
	private SelectableRule gameRule;
	public int boardSize;
	
	/*
	 * Fields of game parameters.
	 */
	private GameBoard gameboard;
	private boolean isStarted;
	private int whoMustPlay;
	
	//Graphical representation of Model allowing interactions with users.
	private View view;
	
	/**
	 * Creates Model that contains the application data and the logic to manipulate this data.
	 */
	public Model() {
		this.whoMustPlay = 0;
		this.isStarted = false;
	}
	
	/**
	 * Creates a link between Model to View.
     * @param view
     *           View - A graphical representation of the Model allowing interactions with users.
	 * @see View
	 */
	public void linkView(View view) {
		this.view = view;
	}
	
	/**
	 * Defines the board generation seed.
     * @param boardGenerationSeed
     *           String - A seed used to define the color of the squares when creating the gameboard.
	 */
	public void setBoardGenerationSeed(String boardGenerationSeed) {
		this.boardGenerationSeed = boardGenerationSeed;
		this.boardSize = (int) Math.sqrt(boardGenerationSeed.length());
	}

	/**
     * Returns the board generation seed.
     * @return String - The board generation key.
     */
	public String getBoardGenerationSeed() {
		return this.boardGenerationSeed;
	}
	
	/**
	 * Defines the board size.
     * @param boardSize
     *           int - The length in number of squares of one side of the board.
	 */
	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
		this.boardGenerationSeed = "";
	}
	
	/**
     * Returns the board side.
     * @return int - The size of one side of the board.
     */
	public int getBoardSize() {
		return this.boardSize;
	}
	
	/**
	 * Defines the game rule.
     * @param gameRule
     *           SelectableRule - The rule used for the next game.
     * @see SelectableColor
	 */
	public void setGameRule(SelectableRule gameRule) {
		this.gameRule = gameRule;
	}
	
	/**
     * returns the game rule.
     * @return SelectableRule - The selected game rule.
     * @see SelectableColor
	 */
	public SelectableRule getGameRule() {
		return this.gameRule;
	}
	
	/**
	 * Creates gameboard with selected settings.
     * @param boardGenerationSeed
     *           String - The board generation seed.
     * @param boardSize
     *           int - The size of one side of the board.
	 */
	public void createGameBoard(String boardGenerationSeed, int boardSize) {
		this.gameboard = new GameBoard(boardGenerationSeed, boardSize, gameRule);
		this.view.setGameBoard(gameboard.getBoard());
	}
	
	/**
     * returns the gameboard.
     * @return GameBoard - The actual gameboard.
     * @see GameBoard
	 */
	public GameBoard getGameBoard() {
		return this.gameboard;
	}
	
	/**
	 * Starts a new game with selected settings.
	 */
	public void start() {
		this.isStarted = true;
		this.view.start();
	}
	
	/**
	 * Resets the view and the gameboard settings.
	 */
	public void reset() {
		this.isStarted = false;
		this.boardGenerationSeed = null;
		this.gameRule = null;
		this.gameboard = null;
		this.whoMustPlay = 0;
		this.view.reset();
	}
	
	/**
     * returns the launch status of the game.
     * @return boolean - The current state of the game.
	 */
	public boolean isStarted() {
		return this.isStarted;
	}
	
	/**
	 * Defines who will play in the next round.
     * @param whoMustPlay
     *           int - The number of the next player to play.
	 */
	public void setWhoMustPlay(int whoMustPlay) {
		this.whoMustPlay = whoMustPlay;
	}
	
	/**
	 * Returns the player who will play in the next round.
     * @return int - the next player to play.
	 */
	public int getWhoMustPlay() {
		return this.whoMustPlay;
	}
	
	/**
	 * Plays on a defined square.
     * @param i
     *           int - The i coordinate of the played square.
     * @param j
     *           int - The j coordinate of the played square.
	 */
	public void play(int i, int j) {
		getGameBoard().setSquaresColor(getGameRule() == SelectableRule.BRAVE ? getAffectedSquaresWithBrave(i, j) : getAffectedSquaresWithReckless(i, j));
		setWhoMustPlay(1 - getWhoMustPlay());
	}
	
	/**
	 * Cheats on a defined square and see the new score generated by using this square.
     * @param i
     *           int - The i coordinate of the cheated square.
     * @param j
     *           int - The j coordinate of the cheated square.
	 */
	public void cheat(int i, int j) {
		view.cheat(getAffectedSquaresWithBrave(i, j).getNewScore());
	}
	
	/**
	 * Returns information about a move by playing on this square with brave rule.
     * @param i
     *           int - The i coordinate of the selected square.
     * @param j
     *           int - The j coordinate of the selected square.
     * @returns PlayableMove - All information associated with the use of this square with brave rule.
     * @see PlayableMove
	 */
	public PlayableMove getAffectedSquaresWithBrave(int i, int j) {
		return new PlayableMove(getGameBoard().getNeighbors(i, j, "Brave"), SelectableColor.getColorFromInt(getWhoMustPlay()));
	}
	
	/**
	 * Returns information about a move by playing on the best square to play, defined by a gluttonous strategy with brave rule.
     * @returns PlayableMove - All information associated with the use of such a square with brave rule.
     * @see PlayableMove
	 */
	public PlayableMove getBestAffectedSquaresWithBrave() {
		PlayableMove move = null;
		for (Square square : getGameBoard().getEmptySquares()) {
			PlayableMove testedmove = getAffectedSquaresWithBrave(square.getX(), square.getY());
			move = move == null || testedmove.getRelativeNewScore() > move.getRelativeNewScore() ? testedmove : move;
		}
		return move;
	}
	
	/**
	 * Returns information about a move by playing on this square with reckless rule.
     * @param i
     *           int - The i coordinate of the selected square.
     * @param j
     *           int - The j coordinate of the selected square.
     * @returns PlayableMove - All information associated with the use of this square with reckless rule.
     * @see PlayableMove
	 */
	public PlayableMove getAffectedSquaresWithReckless(int i, int j) {
		PlayableMove affectedMove = new PlayableMove(getGameBoard().getNeighbors(i, j, "Reckless"), SelectableColor.getColorFromInt(getWhoMustPlay()));
		PlayableMove acquiredMove = getGameBoard().getAcquiredSquares(i, j, SelectableColor.getColorFromInt(getWhoMustPlay()));
		return acquiredMove.hasAcquired() ? acquiredMove : affectedMove;
	}
	
	/**
	 * Returns information about a move by playing on the best square to play, defined by a gluttonous strategy with reckless rule.
     * @returns PlayableMove - All information associated with the use of such a square with reckless rule.
     * @see PlayableMove
	 */
	public PlayableMove getBestAffectedSquaresWithReckless() {
		PlayableMove move = null;
		for (Square square : getGameBoard().getEmptySquares()) {
			PlayableMove testedmove = getAffectedSquaresWithReckless(square.getX(), square.getY());
			move = move == null || testedmove.getRelativeNewScore() > move.getRelativeNewScore() ? testedmove : move;
		}
		return move;
	}
	
	
	
	
	public void evolve() {
		while (true) {
			if (isStarted()) {
				if (getGameBoard() == null) {
					createGameBoard(getBoardGenerationSeed(), getBoardSize());
					/*QuadTree tree = getGameBoard().createAllRegions(new Point((float) (boardSize - 1)/2, (float) (boardSize - 1)/2), 0);
					tree.toString();
					System.out.println(getGameBoard().parcours(38, 13, tree).toString());
					getGameBoard().getSquare(38, 13).setColor(SelectableColor.RED);
					Point point = getGameBoard().parcours(38, 13, tree).getCoordinates();
					getGameBoard().getSquare((int) point.getX(), (int) point.getY()).setColor(SelectableColor.BLUE);*/
					
				}
				view.update(getGameBoard().getScore());
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