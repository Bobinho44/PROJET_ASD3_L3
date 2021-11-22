package fr.bobinho.cameleon.application;

import java.awt.Color;

import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import fr.bobinho.cameleon.board.Square;

/**
 * DrawingPanel is the class representing the drawing panel where the drawing part of the application will be managed.
 * 
 * @author Kylian GERARD
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {
		
    //The game board to be drawn.
	private Square[][] gameBoard;
	
	/**
     * Creates the panel and its design.
     */
	public DrawingPanel() {
		super();
		setBackground(Color.lightGray);
		setBorder(BorderFactory.createLineBorder(Color.lightGray));
	}

	/**
     * Sets the game board to be drawn.
     * @param gameBoard
     *           Square[][] - The game board you want to draw.
     * @see Square
     */
	public void setGameBoard(Square[][] gameBoard) {
		this.gameBoard = gameBoard;
	}

	/**
	 * Redefines the drawing method.
	 */
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