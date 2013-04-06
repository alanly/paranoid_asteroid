package game.ui;

import game.GameController;
import game.GameController.GameType;
import game.ui.menu.ButtonPanel;
import game.ui.menu.GamePanel;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private CardLayout layout;
	private GameController controller;
	private JPanel cardPanel;
	private ButtonPanel buttonPanel;
	private GamePanel gamePanel;
	
	public GameFrame() {
		this.setPreferredSize(new Dimension(GameCanvas.WIDTH, GameCanvas.HEIGHT));
		
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
		
		buttonPanel = new ButtonPanel(this);
		gamePanel = new GamePanel();
		
		cardPanel.add(buttonPanel, "button");
		cardPanel.add(gamePanel, "game");
		
		this.add(cardPanel);
	}
}
