package application;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.List;

import board.SelectableRule;
import utils.Utils;

import java.awt.Color;
import java.awt.Point;


public class Controller {

	private Model model;

	public Controller(Model model) {
		this.model = model;
	}
	
	public boolean setBoardSize(String boardSize) {
		if (boardSize != null && boardSize.matches("^[0-9]+$")) {
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
			if (Math.pow(Integer.valueOf(lines.get(0)), 2) != boardGenerationSeed.length()) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.setBoardGenerationSeed(boardGenerationSeed);
		return true;
	}

	public void play(Point clickedPoint, Color color) {
		if (Utils.isBeetwen(View.GAMEBOARD_TOP_LEFT_CORNER, Utils.translate(View.GAMEBOARD_TOP_LEFT_CORNER, View.GAMEBOARD_SIZE), clickedPoint) && color.equals(Color.WHITE)) {
			int i = (clickedPoint.x - 1 - View.GAMEBOARD_TOP_LEFT_CORNER.x) / (View.SQUARE_SIZE + 1);
			int j = (clickedPoint.y - 1 - View.GAMEBOARD_TOP_LEFT_CORNER.y) / (View.SQUARE_SIZE + 1);
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
	
}