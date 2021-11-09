package application;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import board.Square;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {
		
    // The list of shapes to be drawn.
	private Square[][] gameBoard;
	
	public DrawingPanel() {
		super();
		setBackground(Color.lightGray);
		setBorder(BorderFactory.createLineBorder(Color.lightGray));
	}

	public void setGameBoard(Square[][] gameBoard) {
		this.gameBoard = gameBoard;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (gameBoard != null) {
			g.setColor(Color.BLACK);
			g.fillRect((int) View.GAMEBOARD_TOP_LEFT_CORNER.getX(), (int) View.GAMEBOARD_TOP_LEFT_CORNER.getY(), View.GAMEBOARD_SIZE, View.GAMEBOARD_SIZE);
			for (int i = 0; i < gameBoard[0].length; i++) {
				for (int j = 0; j < gameBoard[1].length; j++) {
					gameBoard[i][j].draw(g);
				}
			}
		}
	}
}
