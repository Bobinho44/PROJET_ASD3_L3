package selectable;

/**
 * The SelectableRule enum contains all the rules the player can use.
 * 
 * @author Kylian GERARD and Quentin GOMES DOS RIES
 * @version 1.0
 */
public enum SelectableRule {
	BRAVE("Brave"),
	RECKLESS("Reckless");
	
	//The rule name
	private String name;
	
	/**
	 * Creates a new rule.
     * @param name
     *           String - The rule name.
	 * @see String
	 */
	private SelectableRule(String name) {
		this.name = name;	
	}
	
	/**
	 * Returns the rule name.
     * @return String - The rule name.
	 * @see String
	 */
	public String getName() {
		return this.name;
	}
	
}