package application;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Robot;
import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import board.SelectableAI;
import board.SelectableRule;
import board.Square;

import utils.Point;
import utils.Utils;

/**
 * The View class is a graphical representation of the Model allowing interactions with users.
 * 
 * @author Kylian GERARD and Quentin GOMES DOS RIES
 * @version 1.0
 */
@SuppressWarnings("serial")
public class View extends JFrame implements MouseListener {
	
	//The coordinates of the game board's top left corner
	public static Point GAMEBOARD_TOP_LEFT_CORNER;
	
	//The game board side size in pixel
	public static int GAMEBOARD_SIZE;
	
	//A Square side size in pixel
	public static int SQUARE_SIZE;
	
	/*
	 * Fields of panels (draw and score).
	 */
	private DrawingPanel drawingPanel;
	private JPanel scorePanel;

	/*
	 * Fields of parameters selection buttons.
	 */
	private ButtonGroup GameBoardFillingGroup;
	private ButtonGroup gameRuleGroup;
	private ButtonGroup AIGroupe;
	
	//Processing user requests and synchronises the model and the view.
	private Controller controller;
	
    /**
     * Instantiates the View and creates the graphical interface 
     * with the different menus. Also sets up the various interactions with these menus.
     * @param title
     *           String - The application's name.
     * @see String
     */
	public View(String title) {
		super(title);
		this.build();
	}
	
	/**
	 * Create the link from View to Controller.
     * @param controller
     *           Controller - The controller that processes user requests and synchronizes the Model and the View.
	 * @see Controller
	 */
	public void linkController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * Returns the panel where the drawing part of the application will be managed.
	 * @return DrawingPanel - The drawing panel.
	 * @see DrawingPanel
	 */
	public DrawingPanel getDrawingPanel() {
		return this.drawingPanel;
	}
	
	/**
	 * Returns the panel where the players' scores are displayed.
	 * @return JPanel - The score panel.
	 * @see JPanel
	 */
	public JPanel getScorePanel() {
		return this.scorePanel;
	}
	
	/**
	 * Returns the game board filling parameters selection button.
	 * @return ButtonGroup - The button with game board filling parameters selection.
	 * @see ButtonGroup
	 */
	public ButtonGroup getGameBoardFillingGroup() {
		return this.GameBoardFillingGroup;
	}
	
	/**
	 * Returns the game rule parameters selection button.
	 * @return ButtonGroup - The button with game rule parameters selection.
	 * @see ButtonGroup
	 */
	public ButtonGroup getGameRuleGroup() {
		return this.gameRuleGroup;
	}
	
	/**
	 * Returns the AI parameters selection button.
	 * @return ButtonGroup - The button with AI parameters selection.
	 * @see ButtonGroup
	 */
	public ButtonGroup getAIGroup() {
		return this.AIGroupe;
	}
	
	/**
	 * Builds the frame and the View structure.
	 */
	private void build() {
		this.drawingPanel = new DrawingPanel();
		this.drawingPanel.addMouseListener(this);
		this.scorePanel = new JPanel(new GridLayout(2, 2));
		this.gameRuleGroup = new ButtonGroup();
		this.AIGroupe = new ButtonGroup();
		this.GameBoardFillingGroup = new ButtonGroup();
		buildContentPane();
	}
	
	/**
	 * Builds the graphics interface with all menus.
	 */
	private void buildContentPane() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().add(getDrawingPanel() , BorderLayout.CENTER);
		setVisible(true);	
		pack();
		GAMEBOARD_SIZE = (int) (1440 / 2.4);
		GAMEBOARD_TOP_LEFT_CORNER = new Point(1440  / 2 - GAMEBOARD_SIZE / 2, 900 / 2 - GAMEBOARD_SIZE / 2);
	
		//Creates the Scores panel.
		JPanel panel = new JPanel(new GridLayout(0, 1));
		add(panel, BorderLayout.WEST);
		panel.add(createJTextField(""));
		panel.add(createJTextField("Score:"));
		getScorePanel().add(createJTextField("   Player 1   "));
		getScorePanel().add(createJTextField("0"));
		getScorePanel().add(createJTextField("   Player 2   "));
		getScorePanel().add(createJTextField("0"));
		panel.add(getScorePanel());
		panel.add(createJTextField(""));
			
		//Creates the bar menu.
		JMenuBar menuBar = new JMenuBar();
		getDrawingPanel().add(menuBar);
		
		//Creates the Rules menu.
		JMenu menuRules = new JMenu("Rules");
		menuBar.add(menuRules);
		for (final SelectableRule gameRule : SelectableRule.values()) {
			JRadioButtonMenuItem ruleButton = new JRadioButtonMenuItem(gameRule.getName() + " cameleon");
			ruleButton.addActionListener(new ActionListener() {
				  
				@Override
				public void actionPerformed(ActionEvent e) {
					if (gameRule == SelectableRule.RECKLESS) {
						controller.setAI(SelectableAI.GLUTTONOUS);
						getAIGroup().clearSelection();
					}
					getMenuItem(2).setEnabled(gameRule == SelectableRule.RECKLESS);
					controller.setGameRule(gameRule);
				}
			});
			getGameRuleGroup().add(ruleButton);
			menuRules.add(ruleButton);
		}
		
		//Creates the AI menu.
		JMenu menuAI = new JMenu("AI");
		menuAI.setEnabled(false);
		menuBar.add(menuAI);
		for (final SelectableAI AI : SelectableAI.values()) {
			JRadioButtonMenuItem AIButton = new JRadioButtonMenuItem(AI.getName());
			AIButton.addActionListener(new ActionListener() {
				  
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.setAI(AI);
				}
			});
			AIButton.setSelected(true);
			getAIGroup().add(AIButton);
			menuAI.add(AIButton);
		}
		
		//Creates the Filling board menu.
		JMenu menuFillingBoard = new JMenu("Filling board");
		menuBar.add(menuFillingBoard);
		
		JRadioButtonMenuItem emptyButton = new JRadioButtonMenuItem("Empty board");
		emptyButton.addActionListener(new ActionListener() {
			  
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Checks if the size is valid
				if (!controller.setBoardSize(JOptionPane.showInputDialog("Enter the board's size"))) {
					getGameBoardFillingGroup().clearSelection();	
					JOptionPane.showMessageDialog(View.this, "The integer entered is not valid!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		getGameBoardFillingGroup().add(emptyButton);
		menuFillingBoard.add(emptyButton);	
		
		JRadioButtonMenuItem selectFileButton = new JRadioButtonMenuItem("Select board file...");
		selectFileButton.addActionListener(new ActionListener() {
				  
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileDlg = new JFileChooser();
				
				//Checks if the file is valid
				if (fileDlg.showOpenDialog(null) != JFileChooser.APPROVE_OPTION || !controller.setBoardGenerationSeed(fileDlg.getSelectedFile())) { 
					getGameBoardFillingGroup().clearSelection();
					JOptionPane.showMessageDialog(View.this, "The file used is not valid!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		getGameBoardFillingGroup().add(selectFileButton);
		menuFillingBoard.add(selectFileButton);
		
		//Creates the Start button.
		JMenuItem start = new JMenuItem("Start");
		start.setEnabled(false);
		start.addActionListener(new ActionListener() {
							
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.start();
								
			}
		});
		menuBar.add(start);
				
		//Creates the Reset button.
		JMenuItem reset = new JMenuItem("Reset");
		reset.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.reset();
						
			}
		});
		menuBar.add(reset);

		//Creates the Quit button.
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
						
			}
		});
		menuBar.add(quit);
	}
	
	/**
	 * Returns an area with a specified text (used to player's score).
    * @param text
     *           String - The JTextField's text.
     * @Return JTextField - The menu of selections of the parameters.
     * @see JTextField
	 */
	private JTextField createJTextField(String text) {
		JTextField textField = new JTextField(text);
		textField.setEditable(false);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		return textField;
	}
	
	/**
	 * Returns the i th menus of selections of the parameters to be able to modify them (block, clear the selection...).
    * @param i
     *           int - The menu number.
     * @Return AbstractButton - The menu of selections of the parameters.
     * @see AbstractButton
	 */
	private AbstractButton getMenuItem(int i) {
		return (AbstractButton) ((JMenuBar) getDrawingPanel().getComponents()[0]).getComponents()[i - 1];
	}
	
	/**
	 * Returns the area where the player's score "playeNumber" is displayed, to be able to edit it.
     * @param playerNumber
     *           int - The player number (0, 1).
     * @see JTextField
	 */
	private JTextField getPlayerScore(int playerNumber) {
		return (JTextField) getScorePanel().getComponents()[2 * playerNumber + 1];
	}
	
	/**
	 * Remove from player the possibility to press the start button to start a game.
	 */
	public void start() {
		for (int i = 1; i < 5; i++)
			getMenuItem(i).setEnabled(false);
	}
	
	/**
	 * Gives the player the possibility to press the start button to start a game.
	 */
	public void canStart() {
		getMenuItem(4).setEnabled(true);
	}
	
	/**
	 * Resets the view and the game board settings.
	 * The player can select his settings again.
	 */
	public void reset() {
		for (int i = 1; i < 5; i++) {
			getMenuItem(i).setEnabled(i != 4 && i != 2  ? true : false);
		}
		getDrawingPanel().setGameBoard(null);
		getGameBoardFillingGroup().clearSelection();
		getGameRuleGroup().clearSelection();
		getAIGroup().clearSelection();
		update(new int[]{0, 0});
	}
	
	/**
	 * Display the new potential score, calculated by cheating..
     * @param newScore
     *           int[] - The new score.
	 */
	public void cheat(int[] newScore) {
		JOptionPane.showMessageDialog(this, "New estimated score:\n" + newScore[0] + " - "  + newScore[1], "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
     * Sets the game board to be drawn by the DrawingPanel.
     * @param gameBoard
     *           Square[][] - The game board you want to draw.
     * @see Square
     * @see DrawingPanel
     */
	public void setGameBoard(Square[][] gameBoard) {
		getDrawingPanel().setGameBoard(gameBoard);
	}
	
	/**
	 * Submits a request to refresh the view to the drawingPanel.
     * @param score
     *           int[] - The new score.
	 * @see DrawingPanel
	 */
	public void update(int[] score) {
		getDrawingPanel().repaint();
		for (int i = 0; i < 2; i++) {
			getPlayerScore(i).setText("" + score[i]);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Detects a click and sends the Controller various information  
	 * like the coordinates of the click or the color of the clicked pixel.
	 * @see Color
	 * @see Controller
	 * 
	 */
	@Override
	public void mousePressed(MouseEvent event) {
		try {
			Color color = new Robot().getPixelColor(event.getLocationOnScreen().x, event.getLocationOnScreen().y);
			controller.click(Utils.javaPointToPoint(event.getPoint()), color, SwingUtilities.isLeftMouseButton(event));
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}