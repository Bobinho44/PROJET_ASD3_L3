package board;

public enum SelectableRule {
	BRAVE("Brave"),
	RECKLESS("Reckless");
	
	private String name;
	
	private SelectableRule(String name) {
		this.name =name;	
	}
	
	public String getName() {
		return this.name;
	}
	
}