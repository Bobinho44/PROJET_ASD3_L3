package board;

import utils.Point;
import utils.Utils;

public class QuadTree {

	private boolean isLeave;
	private QuadTree[] subTrees;
	private Point coordinates;
	
	public QuadTree(Point coordinates, boolean isLeave) {
		this.coordinates = coordinates;
		this.isLeave = isLeave;
		if (!isLeave) {
			this.subTrees = new QuadTree[4];
		}
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
	
	public static void toString(QuadTree tree) {
		System.out.println(tree.getCoordinates().toString());
		if (!tree.isLeave) {
			QuadTree.toString(tree.subTrees[0]);
			QuadTree.toString(tree.subTrees[1]);
			QuadTree.toString(tree.subTrees[2]);
			QuadTree.toString(tree.subTrees[3]);
		}
	}

}