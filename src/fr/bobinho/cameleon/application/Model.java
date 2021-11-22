package fr.bobinho.cameleon.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import fr.bobinho.cameleon.board.GameBoard;
import fr.bobinho.cameleon.board.PlayableMove;
import fr.bobinho.cameleon.board.Square;
import fr.bobinho.cameleon.selectable.SelectableAI;
import fr.bobinho.cameleon.selectable.SelectableColor;
import fr.bobinho.cameleon.selectable.SelectableRule;

/**
 * The Model class contains the application data and logic for manipulate this data.
 * 
 * @author Kylian GERARD
 * @version 1.0
 */
public class Model {
	
	/*
	 * Fields of board parameters.
	 */
	private String boardGenerationSeed;
	private SelectableRule gameRule;
	private SelectableAI AI;
	private int boardSize;
	
	/*
	 * Fields of game parameters.
	 */
	private GameBoard gameboard;
	private boolean isStarted;
	private boolean canPlay;
	private int whoMustPlay;
	
	//Graphical representation of Model allowing interactions with users.
	private View view;
	
	/**
	 * Creates Model that contains the application data and the logic to manipulate this data.
	 */
	public Model() {
		this.whoMustPlay = 0;
		this.isStarted = false;
		this.canPlay = true;
	}
	
	/**
	 * Creates a link from Model to View.
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
		this.canStart();
	}

	/**
     * Returns the board generation seed.
     * @return String - The board generation key.
     */
	public String getBoardGenerationSeed() {
		return this.boardGenerationSeed;
	}
	
	/**
	 * Defines the game rule.
     * @param gameRule
     *           SelectableRule - The rule used for the next game.
     * @see SelectableRule
	 */
	public void setGameRule(SelectableRule gameRule) {
		this.gameRule = gameRule;
		if (gameRule == SelectableRule.RECKLESS) {
			this.AI = null;
		}
		this.canStart();
	}
	
	/**
     * returns the game rule.
     * @return SelectableRule - The selected game rule.
     * @see SelectableRule
	 */
	public SelectableRule getGameRule() {
		return this.gameRule;
	}
	
	/**
	 * Defines the AI.
     * @param AI
     *           SelectableAI - The AI used for the next game.
     * @see SelectableAI
	 */
	public void setAI(SelectableAI AI) {
		this.AI = AI;
		this.canStart();
	}
	
	/**
     * returns the AI.
     * @return SelectableAI - The selected game AI.
     * @see SelectableAI
	 */
	public SelectableAI getAI() {
		return this.AI;
	}
	
	/**
	 * Defines the board size.
     * @param boardSize
     *           int - The length in number of squares of one side of the board.
	 */
	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
		this.boardGenerationSeed = "";
		this.canStart();
	}
	
	/**
     * Returns the board side.
     * @return int - The size of one side of the board.
     */
	public int getBoardSize() {
		return this.boardSize;
	}
	
	/**
	 * Creates gameboard with selected settings.
     * @param boardGenerationSeed
     *           String - The board generation seed.
     * @param boardSize
     *           int - The size of one side of the board.
	 */
	public void createGameBoard() {
		this.gameboard = new GameBoard(getBoardGenerationSeed(), getGameRule(), getAI(), getBoardSize());
		this.view.setGameBoard(gameboard.getBoard());
		view.update(new int[] {0, 0});
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
     * Allows player to start a game.
	 */
	private void canStart() {
		view.canStart(getBoardGenerationSeed() != null && getGameRule() != null && (getAI() != null || getGameRule() == SelectableRule.BRAVE) && getBoardSize() != 0);
	}

	/**
	 * Starts a new game with selected settings.
	 */
	public void start() {
		this.isStarted = true;
		this.view.start();
		this.createGameBoard();
	}
	
	/**
	 * Resets the view and the game board settings.
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
	 * Allows or not the player to play.
	 * @param canPlay
     *           String - The new player can play's status.
	 */
	private void setCanPlay(boolean canPlay) {
		this.canPlay = canPlay;
	}
	
	/**
     * returns if the player can play or if he must wait the IA.
     * @return boolean - The player can play's status.
	 */
	private boolean canPlay() {
		return this.canPlay;
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
	 * Plays on a defined square and puts AI to work.
     * @param i
     *           int - The i coordinate of the played square.
     * @param j
     *           int - The j coordinate of the played square.
	 */
	public void play(int i, int j) {
		
		//Prevents conflict play problem (because of sleep(1)).
		if (!canPlay()) return;
		setCanPlay(false);
		
		//Player play with the selected rule.
		getGameBoard().updateBoard(getGameRule() == SelectableRule.BRAVE ? getAffectedSquaresWithBrave(i, j) : getAffectedSquaresWithReckless(i, j));
		
		//Updates the board and the number of the player who must played.
		setWhoMustPlay(1 - getWhoMustPlay());
		view.update(getGameBoard().getScore());
		
		//Checks if the game is done
		if (getGameBoard().isFinish()) {
			view.finish(getGameBoard().getScore());
			return;
		}
		
		//AI play 1 second after.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(1);
					
					//IA play with the selected rule and the selected IA.
					getGameBoard().updateBoard(getAI() == SelectableAI.SMART ? getSmartAffectedSquaresWithReckless() : getGameRule() == SelectableRule.BRAVE ? getBestAffectedSquaresWithBrave() : getBestAffectedSquaresWithReckless(getGameBoard().getIterableEmptySquares()));
					
					//Updates the board and the number of the player who must played.
					setWhoMustPlay(1 - getWhoMustPlay());
					view.update(getGameBoard().getScore());
					
					//Checks if the game is done
					if (getGameBoard().isFinish()) {
						view.finish(getGameBoard().getScore());
					}
					setCanPlay(true);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Cheats on a defined square and see the new score generated by using this square.
     * @param i
     *           int - The i coordinate of the cheated square.
     * @param j
     *           int - The j coordinate of the cheated square.
	 */
	public void cheat(int i, int j) {
		view.cheat(getGameRule() == SelectableRule.BRAVE ? getNewScoreWithBrave(i, j) : getNewScoreWithReckless(i, j));
	}
	
	/**
	 * EvalCaseBrave
	 * Returns the new score generated by using this square with brave.
     * @param i
     *           int - The i coordinate of the evaluated square.
     * @param j
     *           int - The j coordinate of the evaluated square.
     * @return int[] - The new score generated by using this square.
	 */	
	public int[] getNewScoreWithBrave(int i, int j) {
		return getAffectedSquaresWithBrave(i, j).getNewScore(getGameBoard().getScore());
	}

	/**
	 * EvalCaseTemeraire
	 * Returns the new score generated by using this square with reckless.
     * @param i
     *           int - The i coordinate of the evaluated square.
     * @param j
     *           int - The j coordinate of the evaluated square.
     * @return int[] - The new score generated by using this square.
	 */	
	public int[] getNewScoreWithReckless(int i, int j) {
		return getAffectedSquaresWithReckless(i, j).getNewScore(getGameBoard().getScore());
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
	 * JouerGloutonBrave
	 * Returns information about a move by playing on the best square to play, defined by a gluttonous strategy with brave rule.
     * @returns PlayableMove - All information associated with the use of such a square with brave rule.
     * @see PlayableMove
	 */
	public PlayableMove getBestAffectedSquaresWithBrave() {
		PlayableMove move = null;
		
		//Loops all white squares to get the best square.
		for (Square square : getGameBoard().getIterableEmptySquares()) {
			PlayableMove testedmove = getAffectedSquaresWithBrave(square.getX(), square.getY());
			
			//Checks if the new tested move is better than the previous.
			move = move == null || testedmove.getRelativeNewScore(getGameBoard().getScore()) > move.getRelativeNewScore(getGameBoard().getScore()) ? testedmove : move;
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
		
		//Gets affected Square with the classic reckless rule (without acquiring).
		PlayableMove affectedMove = new PlayableMove(getGameBoard().getNeighbors(i, j, "Reckless"), SelectableColor.getColorFromInt(getWhoMustPlay()));
		
		//Checks if the selected Square causes an acquirement.
		PlayableMove acquiredMove = getGameBoard().getLargestAcquiredRegion(i, j, SelectableColor.getColorFromInt(getWhoMustPlay()));
		
		//Choose the right movement according to a possible acquirement.
		return acquiredMove.hasAcquired() ? getGameBoard().getAcquiredRegionSquares(acquiredMove) : affectedMove;
	}
	
	/**
	 * JouerGloutonTemeraire
	 * Returns information about a move by playing on the best square to play, defined by a gluttonous strategy with reckless rule.
     * @param squaresListUsed
     *           List<Square> - A list of Squares to test (added because of the smart ia).
     * @returns PlayableMove - All information associated with the use of such a square with reckless rule.
     * @see PlayableMove
     * @see List
     * @see Square
	 */
	public PlayableMove getBestAffectedSquaresWithReckless(List<Square> squaresListUsed) {
		PlayableMove move = null;
		
		//Loops all white squares to get the best square.
		for (Square square : squaresListUsed) {
			
			PlayableMove testedClassicMove = new PlayableMove(getGameBoard().getNeighbors(square.getX(), square.getY(), "Reckless"), SelectableColor.getColorFromInt(getWhoMustPlay()));
			PlayableMove testedAcquiredmove = getGameBoard().getLargestAcquiredRegion(square.getX(), square.getY(), SelectableColor.getColorFromInt(getWhoMustPlay()));
			
			int selectedMoveSquare = testedAcquiredmove.hasAcquired() ? (int) (9 * Math.pow(4, testedAcquiredmove.getLargestRegionAcquired().getLevel())) : testedClassicMove.getAffectedSquares().size();
			int actualMoveSquare = move == null ? 0 : move.hasAcquired() ? (int) (9 * Math.pow(4, move.getLargestRegionAcquired().getLevel())) : move.getAffectedSquares().size();
			
			move = move != null && selectedMoveSquare <= actualMoveSquare ? move : (selectedMoveSquare > 8 ? testedAcquiredmove : testedClassicMove);
			
		}
		return move.hasAcquired() ? getGameBoard().getAcquiredRegionSquares(move) : move;
	}

	/**
	 * JouerIATemeraire
	 * Returns information about a move by playing on the best square to play, defined by a smartest strategy with reckless rule.
     * @returns PlayableMove - All information associated with the use of such a square with reckless rule.
     * @see PlayableMove
	 */
	public PlayableMove getSmartAffectedSquaresWithReckless() {
		List<Square> selectedRegion = new ArrayList<Square>();
		
		//Loops all empty little regions
		for (Square square : getGameBoard().getIterableEmptyLittleRegions()) {
			List<Square> region = getGameBoard().getNeighbors(square.getX(), square.getY(), "Colored");
			
			//Check if a little region need one square to be acquired (we will choose a region of this type if possible).
			//Or we get a region with less than 8 colored squares.
			if (region.size() == 8 || (region.size() < 7 && selectedRegion.size() == 0)) {
				selectedRegion.add(square);
			}
		}
		
		//If there are several regions requiring only one square to be acquired, the one with the most points (best acquisition) is chosen.
		if (selectedRegion.size() > 1) {
			return getBestAffectedSquaresWithReckless(selectedRegion);
		}
		
		//Otherwise we play on a region with less than 8 colored squares.
		else if (selectedRegion.size() == 1) {
			Square selectedSquare = getGameBoard().getNeighbors(selectedRegion.get(0).getX(), selectedRegion.get(0).getY(), "Uncolored").get(0);
			return getAffectedSquaresWithReckless(selectedSquare.getX(), selectedSquare.getY());
		}
		
		//Security if no squares were found (e.g. only regions with 8 squares filled).
		return getAffectedSquaresWithReckless(getGameBoard().getIterableEmptySquares().get(0).getX(), getGameBoard().getIterableEmptySquares().get(0).getX());
	}
	
}