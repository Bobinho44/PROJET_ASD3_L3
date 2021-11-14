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

import board.SelectableRule;
import board.Square;

import utils.Point;
import utils.Utils;

@SuppressWarnings("serial")
public class View extends JFrame implements MouseListener {
	
	public static int SQUARE_SIZE;
	public static int GAMEBOARD_SIZE;
	public static Point GAMEBOARD_TOP_LEFT_CORNER;
	
	private Controller controller;
	
	private JPanel scorePanel;
	private DrawingPanel drawingPanel;

	private ButtonGroup gameRuleGroup;
	private ButtonGroup AIGroupe;
	private ButtonGroup GameBoardFillingGroup;
	
	public View(String title) {
		super(title);
		build();
	}
	
	public void linkController(Controller controller) {
		this.controller = controller;
	}
	
	public JPanel getScorePanel() {
		return this.scorePanel;
	}
	
	public DrawingPanel getDrawingPanel() {
		return this.drawingPanel;
	}
	
	public ButtonGroup getGameRuleGroup() {
		return this.gameRuleGroup;
	}
	
	public ButtonGroup getAIGroup() {
		return this.AIGroupe;
	}
	
	public ButtonGroup getGameBoardFillingGroup() {
		return this.GameBoardFillingGroup;
	}
	
	public void setGameBoard(Square[][] gameBoard) {
		this.drawingPanel.setGameBoard(gameBoard);
	}
	
	private void build() {
		this.drawingPanel = new DrawingPanel();
		this.drawingPanel.addMouseListener(this);
		this.scorePanel = new JPanel(new GridLayout(2, 2));
		this.gameRuleGroup = new ButtonGroup();
		this.AIGroupe = gameRuleGroup = new ButtonGroup();
		this.GameBoardFillingGroup = new ButtonGroup();
		buildContentPane();
	}
	
	private void buildContentPane() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().add(getDrawingPanel() , BorderLayout.CENTER);
		setVisible(true);	
		//pack();
		GAMEBOARD_SIZE = (int) (getWidth() / 2.4);
		GAMEBOARD_TOP_LEFT_CORNER = new Point(getWidth()  / 2 - GAMEBOARD_SIZE / 2, getHeight() / 2 - GAMEBOARD_SIZE / 2);
		
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
			
		JMenuBar menuBar = new JMenuBar();
		getDrawingPanel().add(menuBar);
		
		//Creates the Rules menu.
		JMenu menuShape = new JMenu("Rules");
		menuBar.add(menuShape);
		for (final SelectableRule gameRule : SelectableRule.values()) {
			JRadioButtonMenuItem ruleButton = new JRadioButtonMenuItem(gameRule.getName() + " cameleon");
			ruleButton.addActionListener(new ActionListener() {
				  
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.setGameRule(gameRule);
				}
			});
			getGameRuleGroup().add(ruleButton);
			menuShape.add(ruleButton);
		}
		
		//Creating the Filling board menu.
		JMenu menuFillingBoard = new JMenu("Filling board");
		menuBar.add(menuFillingBoard);
		
		JRadioButtonMenuItem emptyButton = new JRadioButtonMenuItem("Empty board");
		emptyButton.addActionListener(new ActionListener() {
			  
			@Override
			public void actionPerformed(ActionEvent e) {
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
	
	private JTextField createJTextField(String text) {
		JTextField textField = new JTextField(text);
		textField.setEditable(false);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		return textField;
	}
	
	private AbstractButton getMenuItem(int i) {
		return (AbstractButton) ((JMenuBar) getDrawingPanel().getComponents()[0]).getComponents()[i - 1];
	}
	
	private JTextField getPlayerScore(int playerNumber) {
		return (JTextField) getScorePanel().getComponents()[2 * playerNumber + 1];
	}
	
	public void start() {
		for (int i = 1; i < 4; i++)
			getMenuItem(i).setEnabled(false);
	}
	
	public void canStart() {
		for (int i = 1; i < 4; i++)
			getMenuItem(i).setEnabled(true);
	}
	
	public void reset() {
		this.drawingPanel.setGameBoard(null);
		this.gameRuleGroup.clearSelection();
		this.AIGroupe.clearSelection();
		this.GameBoardFillingGroup.clearSelection();
		for (int i = 1; i < 3; i++)
			getMenuItem(i).setEnabled(true);
		getMenuItem(3).setEnabled(false);
		update(new int[]{0, 0});
	}
	
	public void cheat(int[] newScore) {
		JOptionPane.showMessageDialog(this, "New estimated score:\n" + newScore[0] + " - "  + newScore[1], "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Submitting a request to refresh the view to the drawingPanel.
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