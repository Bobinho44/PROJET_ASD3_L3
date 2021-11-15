package board;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import application.View;
import utils.Point;

public class Square {
	
	private SelectableColor color;
	private boolean isAcquired;
	private Point coordinates;
	
	public Square(Point coordinates, SelectableColor color) {
		this.coordinates = coordinates;
		this.color = color;
		this.isAcquired = false;
	}
	
	public SelectableColor getColor() {
		return this.color;
	}
	
	public void setColor(SelectableColor color) {
		this.color = color;
	}
	
	public boolean isAcquired() {
		return this.isAcquired;
	}
	
	public void acquired(boolean isAcquired) {
		this.isAcquired = isAcquired;
	}
	
	public Point getCoordinates() {
		return this.coordinates;
	}
	
	public int getX() {
		return (int) getCoordinates().getX();
	}
	
	public int getY() {
		return (int) getCoordinates().getY();
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor().getPaintColor());
		int x = (int) (View.GAMEBOARD_TOP_LEFT_CORNER.getX() + getX() * (View.SQUARE_SIZE + 1) + 1);
		int y = (int) (View.GAMEBOARD_TOP_LEFT_CORNER.getY() + getY() * (View.SQUARE_SIZE + 1) + 1);
		g.fillRect(x, y, View.SQUARE_SIZE, View.SQUARE_SIZE);
		g.setColor(Color.BLACK);
		//g.setFont(new Font("TimesRoman", Font.PLAIN, View.SQUARE_SIZE/6)); 
		if (getY() == 0) g.drawString("" + (getX() + 1), x + View.SQUARE_SIZE / 2, y - 15);
		if (getX() == 0) g.drawString("" + (getY() + 1), x - 25, y + View.SQUARE_SIZE / 2);
	}
	
}