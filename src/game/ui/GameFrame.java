package game.ui;

import game.GameController;
import game.GameController.GameType;
import game.ui.menu.CreditsPanel;
import game.ui.menu.GamePanel;
import game.ui.menu.HighScoresPanel;
import game.ui.menu.MainPanel;

import io.InputHandler;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private CardLayout layout;
	private GameController controller;
	private JPanel cardPanel;
	private JPanel mainPanel;
	private CreditsPanel creditsPanel;
	private HighScoresPanel highScoresPanel;
	private GamePanel gamePanel;
	
	public GameFrame() {
		this.addKeyListener(InputHandler.getInstance());
		
		initPanels();
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
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
	
	public void showHighScores() {
		highScoresPanel.reload();
		layout.show(cardPanel, "highScores");
	}
	
	public void showMainMenu() {
		layout.show(cardPanel, "main");
	}
	
	public void showGamePanel() {
		layout.show(cardPanel, "game");
	}
	
	public void showCreditsPanel() {
		layout.show(cardPanel, "credits");
	}
	
	private void initPanels() {
		layout = new CardLayout();
		cardPanel = new JPanel(layout);
		creditsPanel = new CreditsPanel(this);
		highScoresPanel = new HighScoresPanel(this);
		
		mainPanel = new MainPanel(this);
		gamePanel = new GamePanel();
		
		cardPanel.add(mainPanel, "main");
		cardPanel.add(gamePanel, "game");
		cardPanel.add(creditsPanel, "credits");
		cardPanel.add(highScoresPanel, "highScores");
		
		this.add(cardPanel);
	}
}
