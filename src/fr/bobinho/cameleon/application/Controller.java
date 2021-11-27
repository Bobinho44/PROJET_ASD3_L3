package fr.bobinho.cameleon.application;

import java.awt.Color;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.List;

import fr.bobinho.cameleon.selectable.SelectableAI;
import fr.bobinho.cameleon.selectable.SelectableColor;
import fr.bobinho.cameleon.selectable.SelectableRule;
import fr.bobinho.cameleon.utils.Point;
import fr.bobinho.cameleon.utils.Utils;

/**
 * The controller class processes user requests and synchronizes the Model and the View.
 * 
 * @see Model
 * @see View
 * @author Kylian GERARD and Quentin GOMES DOS REIS
 * @version 1.0
 */
public class Controller {

	//Model contains the application data and logic for manipulate this data.
	private Model model;

	/**
	 * Creates Controller that processes user requests and synchronizes the Model and the View 
	 * and creates a link from Controller to Model.
     * @param model
     *           Model - The model that contains the application data and the logic to manipulate this data.
     * @see Model
	 */
	public Controller(Model model) {
		this.model = model;
	}
	
	/**
	 * Checks if the input file is valid or not. If everything went well, the generation seed is transmitted to the Model.
	 * Otherwise the View is informed that there is a problem.
     * @param boardFillingFile
     *           File - A file containing the representation of a game board in text format.
     * @return boolean - A boolean indicating whether the input file was valid or not.
     * @see Model
     * @see View
     * @see File
	 */
	public boolean setBoardGenerationSeed(File boardFillingFile) {
		String boardGenerationSeed = "";
		int score[] = new int[2];
		try {
			List<String> lines = Files.readAllLines(boardFillingFile.toPath(), StandardCharsets.UTF_8);
			for (int i = 1; i < lines.size(); i++) {
				for (int j = 0; j < lines.get(i).length(); j++) {
					char testedChar = lines.get(i).charAt(j);
					if (testedChar == 'R' || testedChar == 'B') {
						score[SelectableColor.getColorFromChar(testedChar).getPlayerNumber()]++;
					}
					else if (testedChar != 'A') {
						return false;
					}
					boardGenerationSeed += testedChar;
				}
			}
			if (!Utils.isValidGameBoardSize(lines.get(0), (int) Math.sqrt(boardGenerationSeed.length())) || score[0] != score[1]) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.setBoardGenerationSeed(boardGenerationSeed);
		return true;
	}
	
	/**
	 * Transmits the selected game rule to the Model.
     * @param gameRule
     *           SeletableRule - The selected game rule.
     * @see Model
     * @see SelectableRule
	 */
	public void setGameRule(SelectableRule gameRule) {
		model.setGameRule(gameRule);
	}
	
	/**
	 * Transmits the selected AI to the Model.
     * @param AI
     *           SeletableRule - The selected AI.
     * @see Model
     * @see SelectableAI
	 */
	public void setAI(SelectableAI AI) {
		model.setAI(AI);
	}
	
	/**
	 * Checks if the input board size is valid or not. If everything went well, the board size is transmitted to the Model.
	 * Otherwise the View is informed that there is a problem.
     * @param boardSize
     *           String - A representation of the number of squares per side of the game board.
     * @return boolean - A boolean indicating whether the input board size was valid or not.
     * @see Model
     * @see View
     * @see String
	 */
	public boolean setBoardSize(String boardSize) {
		if (Utils.isValidGameBoardSize(boardSize)) {
			model.setBoardSize(Integer.valueOf(boardSize));
			return true;
		}
		return false;
	}
	
	/**
	 * Tells the Model to start the game.
     * @see Model
	 */
	public void start() {
		model.start();
	}
	
	/**
	 * Tells the Model to reset the game board.
     * @see Model
	 */
	public void reset() {
		model.reset();
	}
	
	/**
	 * Checks if the clicked point belongs to the game board, and if this square is playable (white).
	 * If the player has right-clicked, the Model is asked to preview the future score using this square.
	 * Otherwise we ask the Model to play on this square.
     * @param clickedPoint
     *           Point - The position where you clicked.
     * @param color
     *           Color - The color of the pixel clicked on.
     * @param isLeftClick
     *           boolean - A representation of the type of click (right or left).          
     * @see Model
     * @see Color
     * @see Point
	 */
	public void click(Point clickedPoint, Color color, boolean isLeftClick) {
		if (Utils.isBeetwen(View.GAMEBOARD_TOP_LEFT_CORNER, View.GAMEBOARD_TOP_LEFT_CORNER.translate(View.GAMEBOARD_SIZE), clickedPoint) && color.equals(Color.WHITE)) {
			int i = (int) ((clickedPoint.getX() - 1 - View.GAMEBOARD_TOP_LEFT_CORNER.getX()) / (View.SQUARE_SIZE + 1));
			int j = (int) ((clickedPoint.getY() - 1 - View.GAMEBOARD_TOP_LEFT_CORNER.getY()) / (View.SQUARE_SIZE + 1));
			if (isLeftClick)
				model.play(i, j);
			else
				model.cheat(i, j);
		}
	}
	
}