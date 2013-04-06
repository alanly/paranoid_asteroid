package game;

import game.ui.GameCanvas;
import game.ui.menu.GamePanel;

import javax.swing.JOptionPane;

public class GameController {
	public enum GameType {
		SINGLE_PLAYER,
		TWO_PLAYER
	}
	
	private GamePanel gamePanel;
	
	public GameController(GamePanel gamePanel) {
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
		GameCanvas canvas = gamePanel.getGameCanvas();
		
		canvas.setGame(game);
		
		gamePanel.revalidate();
		gamePanel.repaint();
		
		game.start();
		
		return game.getPoints();
	}
	
	private long playTwoPlayer() {
		return 0;
	}
}
