package game.ui;

import game.GameController;
import game.GameController.GameType;
import game.ui.menu.CreditsPanel;
import game.ui.menu.GamePanel;
import game.ui.menu.HelpPanel;
import game.ui.menu.HighScoresPanel;
import game.ui.menu.MainPanel;

import io.InputHandler;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *  GameFrame is a subclass of JFrame and
 * 
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private CardLayout layout;
	private GameController controller;
	private JPanel cardPanel;
	private JPanel mainPanel;
	private CreditsPanel creditsPanel;
	private HighScoresPanel highScoresPanel;
	private GamePanel gamePanel;
	private HelpPanel helpPanel;
	
	/**
	 * Creates a new GameFrame by initializing its panels and adding the InputHandler singleton as a KeyListener to the JFrame
	 */
	public GameFrame() {
		super("Paranoid Asteroid");
		this.addKeyListener(InputHandler.getInstance());
		
		initPanels();
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	/**
	 * Launches a single player game in a new Thread in which a new GameController is reassigned to the frame. The game can be a new game or a loading of a previously saved game.
	 * @param loadGame the method loads a previously saved game when <tt>true</tt>, launches a new game otherwise.
	 */
	public void playSinglePlayer(final boolean loadGame) {
		controller = null;
		
		new Thread(new Runnable() {
			public void run() {
				controller = new GameController(gamePanel);
				InputHandler.getInstance().addPauseHandler(controller);
				InputHandler.getInstance().addSaveHandler(controller);
				controller.playGame(GameType.SINGLE_PLAYER, loadGame);
				InputHandler.getInstance().removePauseHandler(controller);
				InputHandler.getInstance().removeSaveHandler(controller);
				showMainMenu();
			}
		}).start();
		
		showGamePanel();
	}
	
	/**
	 * Launches a single player game in a new Thread in which a new GameController is reassigned to the frame. The game can be a new game or a loading of a previously saved game.
	 */
	public void playTwoPlayer() {
		controller = null;
		
		new Thread(new Runnable() {
			public void run() {
				controller = new GameController(gamePanel);
				InputHandler.getInstance().addPauseHandler(controller);
				controller.playGame(GameType.TWO_PLAYER);
				InputHandler.getInstance().removePauseHandler(controller);
				showMainMenu();
			}
		}).start();
		
		showGamePanel();
	}
	
	/**
	 * Displays the highscoresPanel
	 */
	public void showHighScores() {
		highScoresPanel.reload();
		layout.show(cardPanel, "highScores");
	}
	
	/**
	 * Displays the MainPanel
	 */
	public void showMainMenu() {
		layout.show(cardPanel, "main");
	}
	
	/**
	 * Displays the GamePanel
	 */
	public void showGamePanel() {
		layout.show(cardPanel, "game");
	}
	
	/**
	 * Displays the CreditsPanel
	 */
	public void showCreditsPanel() {
		layout.show(cardPanel, "credits");
	}
	
	/**
	 * Displays the HelpPanel
	 */
	public void showHelpPanel() {
		layout.show(cardPanel, "help");
	}
	
	/**
	 * Initializes the UI panels and lays them out in a CardLayout which permits switching between which panel is being displayed. 
	 */
	private void initPanels() {
		layout = new CardLayout();
		cardPanel = new JPanel(layout);
		creditsPanel = new CreditsPanel(this);
		highScoresPanel = new HighScoresPanel(this);
		helpPanel = new HelpPanel(this);
		
		mainPanel = new MainPanel(this);
		gamePanel = new GamePanel();
		
		cardPanel.add(mainPanel, "main");
		cardPanel.add(gamePanel, "game");
		cardPanel.add(creditsPanel, "credits");
		cardPanel.add(highScoresPanel, "highScores");
		cardPanel.add(helpPanel, "help");
		
		this.add(cardPanel);
	}
}
