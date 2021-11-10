package board;

import utils.Point;
import utils.Utils;

public class QuadTree {

	private int level;
	private QuadTree[] subTrees;
	private Point coordinates;
	private SelectableColor acquiredColor;
	
	public QuadTree(Point coordinates, int level) {
		this.coordinates = coordinates;
		this.level = level;
		if (level != 0) {
			this.subTrees = new QuadTree[4];
		}
	}
	
	public void addSubTree(int number, QuadTree subTree) {
		if (Utils.isBeetwen(0, 3, number)) {
			this.subTrees[number] = subTree;
		}
	}
	
	public QuadTree getSubTree(int number) {
		return this.subTrees[number];
	}
	
	public boolean isLeave() {
		return getLevel() == 0;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public Point getCoordinates() {
		return this.coordinates;
	}
	
	public float getX() {
		return this.getCoordinates().getX();
	}
	
	public float getY() {
		return this.getCoordinates().getY();
	}
	
	public SelectableColor getAcquiredColor() {
		return this.acquiredColor;
	}
	
	public boolean isAcquired() {
		return getAcquiredColor() != null;
	}
	
	public void acquired(SelectableColor acquiredColor) {
		this.acquiredColor = acquiredColor;
	}
	
	public String toString() {
		QuadTree.toString(this);
		return "";
	}
	public static void toString(QuadTree tree) {
		System.out.println(tree.getCoordinates().toString());
		if (!tree.isLeave()) {
			QuadTree.toString(tree.subTrees[0]);
			QuadTree.toString(tree.subTrees[1]);
			QuadTree.toString(tree.subTrees[2]);
			QuadTree.toString(tree.subTrees[3]);
		}
	}

}