package game.ui;

import game.GameController;
import game.GameController.GameType;
import game.ui.menu.GamePanel;
import game.ui.menu.MainPanel;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private CardLayout layout;
	private GameController controller;
	private JPanel cardPanel;
	private JPanel mainPanel;
	private GamePanel gamePanel;
	
	public GameFrame() {
		initPanels();
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void playSinglePlayer() {
		controller = null;
		
		new Thread(new Runnable() {
			public void run() {
				controller = new GameController(gamePanel);
				controller.playGame(GameType.SINGLE_PLAYER);
				showButtonPanel();
			}
		}).start();
		
		showGamePanel();
	}
	
	private void showGamePanel() {
		layout.show(cardPanel, "game");
	}
	
	private void showButtonPanel() {
		layout.show(cardPanel, "button");
	}
	
	private void initPanels() {
		layout = new CardLayout();
		cardPanel = new JPanel(layout);
		
		mainPanel = new MainPanel(this);
		gamePanel = new GamePanel();
		
		cardPanel.add(mainPanel, "button");
		cardPanel.add(gamePanel, "game");
		
		this.add(cardPanel);
	}
}
