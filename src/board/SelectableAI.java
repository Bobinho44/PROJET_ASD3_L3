package board;

public enum SelectableAI {
	GLUTTONOUS("Gluttonous"),
	SMART("Smart");
	
	private String name;
	
	private SelectableAI(String name) {
		this.name = name;	
	}
	
	public String getName() {
		return this.name;
	}
	
}