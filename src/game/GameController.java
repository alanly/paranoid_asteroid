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
		if (type == GameType.SINGLE_PLAYER) {
			playSinglePlayer();
		} else if (type == GameType.TWO_PLAYER) {
			playTwoPlayer();
		}
	}
	
	private long playSinglePlayer() {
		Game game = new Game();
		GameCanvas canvas = gamePanel.getGameCanvas();
		long points = 0;
		
		canvas.setGame(game);
		
		gamePanel.revalidate();
		gamePanel.repaint();
		
		game.start();
		
		points = game.getPoints();
		HighScores highScores = HighScores.getInstance();
		
		if (highScores.isHighScore(points)) {
			String name = JOptionPane.showInputDialog(gamePanel, "Enter your name to save your high score!", "New High Score", JOptionPane.PLAIN_MESSAGE);
			
			if (name != null && !name.trim().equals("")) {
				highScores.submit(points, name.trim());
			}
		} else {
			JOptionPane.showMessageDialog(gamePanel, "Sorry, you didn't get a high score!", "Game Over", JOptionPane.PLAIN_MESSAGE);
		}
		
		return points;
	}
	
	private void playTwoPlayer() {
		JOptionPane.showMessageDialog(gamePanel, "Player One's turn!", "Turn Start", JOptionPane.PLAIN_MESSAGE);
		long p1Points = playSinglePlayer();
		
		JOptionPane.showMessageDialog(gamePanel, "Player Two's turn!", "Turn Start", JOptionPane.PLAIN_MESSAGE);
		long p2Points = playSinglePlayer();
		
		if (p1Points > p2Points) {
			JOptionPane.showMessageDialog(gamePanel, "Player One wins!", null, JOptionPane.PLAIN_MESSAGE);
		} else if (p1Points < p2Points) {
			JOptionPane.showMessageDialog(gamePanel, "Player Two wins!", null, JOptionPane.PLAIN_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(gamePanel, "The game ended in a tie!", "Game Over", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
