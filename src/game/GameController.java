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
		
		HighScores highScores = HighScores.getInstance();
		
		if (highScores.isHighScore(points)) {
			String name = JOptionPane.showInputDialog(gamePanel, "Enter your name to save your high score!", "New High Score", JOptionPane.PLAIN_MESSAGE);
			
			if (name != null && !name.trim().equals("")) {
				highScores.submit(points, name.trim());
			}
		} else {
			JOptionPane.showMessageDialog(gamePanel, "Sorry, you didn't get a high score!", "Game Over", JOptionPane.PLAIN_MESSAGE);
		}
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
