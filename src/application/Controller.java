package application;

import java.awt.Color;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.List;

import board.SelectableRule;
import utils.Point;
import utils.Utils;

public class Controller {

	private Model model;

	public Controller(Model model) {
		this.model = model;
	}
	
	public boolean setBoardSize(String boardSize) {
		if (Utils.isValidGameBoardSize(boardSize)) {
			model.setBoardSize(Integer.valueOf(boardSize));
			return true;
		}
		return false;
	}
	
	public boolean setBoardGenerationSeed(File boardFillingFile) {
		String boardGenerationSeed = "";
		try {
			List<String> lines = Files.readAllLines(boardFillingFile.toPath(), StandardCharsets.UTF_8);
			for (int i = 1; i < lines.size(); i++) {
				for (int j = 0; j < lines.get(i).length(); j++) {
					char testedChar = lines.get(i).charAt(j);
					if (testedChar != 'R' && testedChar != 'B' && testedChar != 'A') {
						return false;
					}
					boardGenerationSeed += testedChar;
				}
			}
			if (!Utils.isValidGameBoardSize(lines.get(0), (int) Math.sqrt(boardGenerationSeed.length()))) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.setBoardGenerationSeed(boardGenerationSeed);
		return true;
	}

	public void play(Point clickedPoint, Color color) {
		if (Utils.isBeetwen(View.GAMEBOARD_TOP_LEFT_CORNER, View.GAMEBOARD_TOP_LEFT_CORNER.translate(View.GAMEBOARD_SIZE), clickedPoint) && color.equals(Color.WHITE)) {
			int i = (int) ((clickedPoint.getX() - 1 - View.GAMEBOARD_TOP_LEFT_CORNER.getX()) / (View.SQUARE_SIZE + 1));
			int j = (int) ((clickedPoint.getY() - 1 - View.GAMEBOARD_TOP_LEFT_CORNER.getY()) / (View.SQUARE_SIZE + 1));
			model.play(i, j);
		}
	}
	
	public void setGameRule(SelectableRule gameRule) {
		model.setGameRule(gameRule);
	}
	
	public void start() {
		model.start();
	}
	
	public void reset() {
		model.reset();
	}
	
	public void cheat(Point clickedPoint, Color color) {
		if (Utils.isBeetwen(View.GAMEBOARD_TOP_LEFT_CORNER, View.GAMEBOARD_TOP_LEFT_CORNER.translate(View.GAMEBOARD_SIZE), clickedPoint) && color.equals(Color.WHITE)) {
			int i = (int) ((clickedPoint.getX() - 1 - View.GAMEBOARD_TOP_LEFT_CORNER.getX()) / (View.SQUARE_SIZE + 1));
			int j = (int) ((clickedPoint.getY() - 1 - View.GAMEBOARD_TOP_LEFT_CORNER.getY()) / (View.SQUARE_SIZE + 1));
			model.cheat(i, j);
		}
	}
	
}