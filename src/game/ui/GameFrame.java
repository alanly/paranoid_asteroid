package game.ui;

import game.GameController;
import game.GameController.GameType;
import game.ui.menu.GamePanel;
import game.ui.menu.MainPanel;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private CardLayout layout;
	private GameController controller;
	private JPanel cardPanel;
	private JPanel mainPanel;
	private HighScoresPanel highScoresPanel;
	private GamePanel gamePanel;
	
	public GameFrame() {
		initPanels();
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void playSinglePlayer() {
		controller = null;
		
		new Thread(new Runnable() {
			public void run() {
				controller = new GameController(gamePanel);
				controller.playGame(GameType.SINGLE_PLAYER);
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
	
	private void initPanels() {
		layout = new CardLayout();
		cardPanel = new JPanel(layout);
		highScoresPanel = new HighScoresPanel(this);
		
		mainPanel = new MainPanel(this);
		gamePanel = new GamePanel();
		
		cardPanel.add(mainPanel, "main");
		cardPanel.add(gamePanel, "game");
		cardPanel.add(highScoresPanel, "highScores");
		
		this.add(cardPanel);
	}
}
