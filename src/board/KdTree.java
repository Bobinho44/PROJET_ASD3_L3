package board;

public class KdTree {

	private KdTree[] subTrees;
	private Square square;
	
	public KdTree(Square square) {
		this.square = square;
		this.subTrees = null;
	}
	
	public void add(Square added, int... decoupe) {
		int dec = decoupe.length == 0 ? 0 : decoupe[0];
		boolean isLeft = dec == 0 ? added.getX() <= getSquare().getX() : added.getY() <= getSquare().getY();
		if (getSubTree(isLeft ? 0 : 1).isEmpty()) {
			setSubTree(isLeft ? 0 : 1, new KdTree(added));
		}
		else {
			getSubTree(isLeft ? 0 : 1).add(square, 1 - dec);
		}
	}
	
	public void remove(Square removed, int... decoupe) {
		int dec = decoupe.length == 0 ? 0 : decoupe[0];
		boolean isLeft = dec == 0 ? removed.getX() <= getSquare().getX() : removed.getY() <= getSquare().getY();
		if (getSubTree(isLeft ? 0 : 1).isEmpty()) {
			setSubTree(isLeft ? 0 : 1, null);
		}
		/*fonction  Enlever (A abr, x élément) : abr ;
		si Vide (A) alors
			retour (A)
		sinon si  x < Elt (A) alors
			retour (Cons(Elt(A), Enlever (G(A), x), D(A))) 
		sinon si  x >  Elt (A) alors
			retour (Cons(Elt(A), G(A), Enlever (D(A),x)) ) 
		retour (Cons(MAX(G(A)), Suppr_max (G(A)), D(A)))
		 */
	}
	
	public boolean isEmpty() {
		return subTrees == null;
	}
	
	public Square getSquare() {
		return this.square;
	}
	
	public KdTree[] getSubTrees() {
		return this.subTrees;
	}
	
	public void setSubTree(int i, KdTree tree) {
		getSubTrees()[i] = tree;
	}
	
	public KdTree getSubTree(int i) {
		return getSubTrees()[i];
	}
	
}