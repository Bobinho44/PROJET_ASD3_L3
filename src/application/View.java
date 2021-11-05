package application;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.awt.Robot;
import java.awt.Point;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import board.SelectableColor;
import board.SelectableRule;
import board.GameBoard;
import board.Square;

@SuppressWarnings("serial")
public class View extends JFrame implements MouseListener {
	
	private static final int MENU_BAR_HEIGHT = 60;
	
	public static int GAMEBOARD_SIZE;
	public static Point GAMEBOARD_TOP_LEFT_CORNER;
	public static int SQUARE_SIZE;
	
	private DrawingPanel drawingPanel;
	private JPanel scorePanel;
	private GameBoard gameBoard;
	
	private Controller controller;

	private ButtonGroup gameRuleGroup;
	private ButtonGroup AIGroupe;
	private ButtonGroup GameBoardFillingGroup;
	
	public View(String title) {
		super(title);
		build();
	}
	
	public void linkController(Controller controller) {
		this.controller= controller;
	}
	
	public void createGameBoard(String boardGenerationSeed, int boardSize) {
		this.gameBoard = new GameBoard(boardGenerationSeed, boardSize);
		drawingPanel.setGameBoard(gameBoard.getBoard());
	}
	
	public GameBoard getGameBoard() {
		return this.gameBoard;
	}
	
	private void build() {
		drawingPanel = new DrawingPanel();
		drawingPanel.addMouseListener(this);
		scorePanel = new JPanel(new GridLayout(2, 2));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().add(drawingPanel , BorderLayout.CENTER);
		gameRuleGroup = new ButtonGroup();
		AIGroupe = gameRuleGroup = new ButtonGroup();
		GameBoardFillingGroup = new ButtonGroup();
		buildContentPane();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public JTextField createJTextField(String text) {
		JTextField textField = new JTextField(text);
		textField.setEditable(false);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		return textField;
	}
	
	private void buildContentPane() {
		setVisible(true);
		
		/*JPanel b = new JPanel();
		b.setPreferredSize(new Dimension(300, 20));
		JLabel a = new JLabel("aaaaaaaaa");
		b.add(a, BorderLayout.CENTER);
		add(b, BorderLayout.WEST);*/

		JPanel panel = new JPanel(new GridLayout(0, 1));
		add(panel, BorderLayout.WEST);
		
		panel.add(createJTextField(""));
		panel.add(createJTextField("Score:"));
		scorePanel.add(createJTextField("   Player 1   "));
		scorePanel.add(createJTextField("0"));
		scorePanel.add(createJTextField("   Player 2   "));
		scorePanel.add(createJTextField("0"));
		panel.add(scorePanel);
		panel.add(createJTextField(""));
		
		
		
		
		
		
		GAMEBOARD_SIZE = (int) (getWidth() / 2.4);
		GAMEBOARD_TOP_LEFT_CORNER = new Point(getWidth() / 2 - GAMEBOARD_SIZE / 2, getHeight() / 2 - GAMEBOARD_SIZE / 2);
		JMenuBar menuBar = new JMenuBar();
		drawingPanel.add(menuBar);
		
		// Creating the Rules menu.
		JMenu menuShape = new JMenu("Rules");
		menuBar.add(menuShape);
		for (SelectableRule gameRule : SelectableRule.values()) {
			JRadioButtonMenuItem ruleButton = new JRadioButtonMenuItem(gameRule.getName() + " cameleon");
			ruleButton.addActionListener(new ActionListener() {
				  
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.setGameRule(gameRule);
				}
			});
			gameRuleGroup.add(ruleButton);
			menuShape.add(ruleButton);
		}
		
		// Creating the filling board menu.
		JMenu menuFillingBoard = new JMenu("Filling board");
		menuBar.add(menuFillingBoard);
		
		JRadioButtonMenuItem emptyButton = new JRadioButtonMenuItem("Empty board");
		emptyButton.addActionListener(new ActionListener() {
			  
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!controller.setBoardSize(JOptionPane.showInputDialog("Enter the board's size"))) {
					GameBoardFillingGroup.clearSelection();	
				}
			}
		});
		GameBoardFillingGroup.add(emptyButton);
				
		JRadioButtonMenuItem selectFileButton = new JRadioButtonMenuItem("Select board file...");
		selectFileButton.addActionListener(new ActionListener() {
				  
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileDlg = new JFileChooser();
				if (fileDlg.showOpenDialog(null) != JFileChooser.APPROVE_OPTION || !controller.setBoardGenerationSeed(fileDlg.getSelectedFile())) { 
					GameBoardFillingGroup.clearSelection();
				}
			}
		});
	  	GameBoardFillingGroup.add(selectFileButton);
	  	
		menuFillingBoard.add(emptyButton);
		menuFillingBoard.add(selectFileButton);
		
		// Creating the start button.
		JMenuItem start = new JMenuItem("Start");
		start.setEnabled(false);
		start.addActionListener(new ActionListener() {
							
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.start();
								
			}
		});
		menuBar.add(start);
				
		// Creating the reset button.
		JMenuItem reset = new JMenuItem("Reset");
		reset.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.reset();
						
			}
		});
		menuBar.add(reset);
	
		// Creating the cheat button.
		JMenuItem cheat = new JMenuItem("Cheat");
		cheat.addActionListener(new ActionListener() {
							
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.reset();
						
			}
		});
		menuBar.add(cheat);
				
		// Creating the quit button.
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
						
			}
		});
		menuBar.add(quit);
	}
	
	public void setSquaresColor(List<Square> squares, SelectableColor color) {
		getGameBoard().setSquaresColor(squares, color);
	}
	
	private AbstractButton getMenuItem(int i) {
		return (AbstractButton) ((JMenuBar) drawingPanel.getComponents()[0]).getComponents()[i - 1];
	}
	
	private JTextField getPlayerScore(int playerNumber) {
		return (JTextField) scorePanel.getComponents()[2 * playerNumber + 1];
	}
	
	public void start() {
		getMenuItem(3).setEnabled(false);
	}
	
	public void canStart() {
		getMenuItem(3).setEnabled(true);
	}
	
	public void reset() {
		getMenuItem(3).setEnabled(false);
		gameBoard = null;
		drawingPanel.setGameBoard(null);
		gameRuleGroup.clearSelection();
		AIGroupe.clearSelection();
		GameBoardFillingGroup.clearSelection();
		update();
	}
	/**
	 * Submitting a request to refresh the view to the drawingPanel.
	 * @see DrawingPanel
	 */
	public void update() {
		drawingPanel.repaint();
		for (int i = 0; i < 2; i++) {
			getPlayerScore(i).setText(getGameBoard() == null ? "0" : "" + getGameBoard().getScore()[i]);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		try {
			controller.play(event.getPoint(), new Robot().getPixelColor(event.getLocationOnScreen().x, event.getLocationOnScreen().y));
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