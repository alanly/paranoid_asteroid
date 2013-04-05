package game;

import game.ui.GameCanvas;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameController {
	enum GameType {
		SINGLE_PLAYER,
		TWO_PLAYER
	}
	
	private JPanel gamePanel;
	
	public GameController(JPanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void playGame(GameType type) {
		long points = 0;
		
		if (type == GameType.SINGLE_PLAYER) {
			points = playSinglePlayer();
		} else if (type == GameType.TWO_PLAYER) {
			points = playTwoPlayer();
		}
		
		JOptionPane.showMessageDialog(null, "You got " + points + " points!");
	}
	
	private long playSinglePlayer() {
		Game game = new Game();
		GameCanvas canvas = new GameCanvas(game);
		
		gamePanel.add(canvas);
		gamePanel.revalidate();
		gamePanel.repaint();
		
		game.start();
		
		return game.getPoints();
	}
	
	private long playTwoPlayer() {
		return 0;
	}
}
