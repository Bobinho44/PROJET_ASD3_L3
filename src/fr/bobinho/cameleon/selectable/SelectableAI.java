package fr.bobinho.cameleon.selectable;

/**
 * The SelectableAI enum contains all selectable AI.
 * 
 * @author Kylian GERARD and Quentin GOMES DOS RIES
 * @version 1.0
 */
public enum SelectableAI {
	GLUTTONOUS("Gluttonous"),
	SMART("Smart");
	
	//The AI name.
	private String name;
	
	/**
	 * Creates a new AI.
     * @param name
     *           String - The AI name.       
	 * @see String
	 */
	private SelectableAI(String name) {
		this.name = name;	
	}
	
	/**
	 * Returns the AI name. 
     * @return String - The AI name.    
     * @see String  
	 */
	public String getName() {
		return this.name;
	}
	
}