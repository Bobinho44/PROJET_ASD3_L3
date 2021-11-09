package board;

import java.awt.Point;

import utils.Utils;

public class QuadTree {

	private boolean isLeave;
	private QuadTree[] subTrees;
	private Point coordinates;
	
	public QuadTree(Point coordinates, boolean isLeave) {
		this.coordinates = coordinates;
		this.isLeave = isLeave;
	}
	
	public void addSubTree(int number, QuadTree subTree) {
		if (Utils.isBeetwen(0, 3, number)) {
			this.subTrees[number] = subTree;
		}
	}
	
	public boolean isLeave() {
		return this.isLeave;
	}
	
	public Point getCoordinates() {
		return this.coordinates;
	}

}