package board;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import application.View;
import utils.Utils;

public class Square {
	
	private Point coordinates;
	private SelectableColor color;
	private boolean isAcquired;
	
	public Square(Point coordinates, SelectableColor color) {
		this.coordinates = coordinates;
		this.color = color;
		this.isAcquired = false;
	}
	
	public void setColor(SelectableColor color) {
		this.color = color;
	}
	
	public SelectableColor getColor() {
		return this.color;
	}
	
	public Point getCoordinates() {
		return this.coordinates;
	}
	
	public int getX() {
		return getCoordinates().x;
	}
	
	public int getY() {
		return getCoordinates().y;
	}
	
	public List<Square> getNeighbors(Square[][] gameBoard) {
		List<Square> neighbors = new ArrayList<Square>();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				int x = getX() + i;
				int y = getY() + j;
				if (Utils.isValidIndex(x, y, gameBoard[0].length, gameBoard[1].length)) {
					neighbors.add(gameBoard[getX() + i][getY() + j]);
				}
			}
		}
		return neighbors;
	}
	
	public void setIsAcquired(boolean isAcquired) {
		this.isAcquired = isAcquired;
	}
	
	public boolean isAcquired() {
		return this.isAcquired;
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor().getPaintColor());
		int x = View.GAMEBOARD_TOP_LEFT_CORNER.x + getX() * (View.SQUARE_SIZE + 1) + 1;
		int y = View.GAMEBOARD_TOP_LEFT_CORNER.y + getY() * (View.SQUARE_SIZE + 1) + 1;
		g.fillRect(x, y, View.SQUARE_SIZE, View.SQUARE_SIZE);
		g.setColor(Color.BLACK);
		if (getY() == 0) g.drawString("" + (getX() + 1), x + View.SQUARE_SIZE / 2, y - 15);
		if (getX() == 0) g.drawString("" + (getY() + 1), x - 25, y + View.SQUARE_SIZE / 2);
	}
	
}