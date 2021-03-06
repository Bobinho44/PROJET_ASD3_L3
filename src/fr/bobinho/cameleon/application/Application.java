package fr.bobinho.cameleon.application;

import javax.swing.SwingUtilities;

/**
 * Application is the application class of this program. 
 * It instantiates the three pillars of the application (MVC), 
 * creates the elemental links between them, and launches the application.
 * 
 * @author Kylian GERARD and Quentin GOMES DOS REIS
 * @version 1.0
 */
public class Application {
	
	public static void main(String[] args) {
		final Model model = new Model();
		final Controller controller = new Controller(model); 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final View view = new View("Cameleon");
				model.linkView(view);
				view.linkController(controller);
			}
		});
	}

}