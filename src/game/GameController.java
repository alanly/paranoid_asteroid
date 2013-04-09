package game;

import game.ui.GameCanvas;
import game.ui.menu.GamePanel;

import javax.swing.JOptionPane;

public class GameController {
	public enum GameType {
		SINGLE_PLAYER,
		TWO_PLAYER
	}
	
	private static final int MAX_TWO_PLAYER_TURNS = 3;
	
	private GamePanel gamePanel;
	
	public GameController(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void playGame(GameType type) {
		GameCanvas canvas = gamePanel.getGameCanvas();
		
		if (type == GameType.SINGLE_PLAYER) {
			playSinglePlayer(canvas);
		} else if (type == GameType.TWO_PLAYER) {
			playTwoPlayer(canvas);
		}
	}
	
	private void playSinglePlayer(GameCanvas canvas) {
		Game game = new Game();
		bindAndStartGame(game, canvas);
		
		long points = game.getPoints();
		
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
	
	private void playTwoPlayer(GameCanvas canvas) {
		Game game;
		BasicGameState playerOneState = new BasicGameState();
		BasicGameState playerTwoState = new BasicGameState();
		
		for (int i = 0; i < MAX_TWO_PLAYER_TURNS; i++) {
			// Player one turn
			JOptionPane.showMessageDialog(gamePanel, "Player One's turn!", "Turn Start", JOptionPane.PLAIN_MESSAGE);
			game = new Game(playerOneState);
			bindAndStartGame(game, canvas);
			playerOneState = game.extractState();
			
			// Player one turn
			JOptionPane.showMessageDialog(gamePanel, "Player Two's turn!", "Turn Start", JOptionPane.PLAIN_MESSAGE);
			game = new Game(playerTwoState);
			bindAndStartGame(game, canvas);
			playerTwoState = game.extractState();
		}
		
		long playerOnePoints = playerOneState.getPoints();
		long playerTwoPoints = playerTwoState.getPoints();
		
		announceWinner(playerOnePoints, playerTwoPoints);
	}
	
	private void bindAndStartGame(Game game, GameCanvas canvas) {
		canvas.setGame(game);
		
		gamePanel.revalidate();
		gamePanel.repaint();
		
		game.start();
	}
	
	private void announceWinner(long p1, long p2) {
		String message = "Player One: " + p1 + " points\nPlayer Two: " + p2 + " points\n";
		
		if (p1 == p2) {
			message += "The game ended in a tie!";
		} else {
			if (p1 > p2) {
				message += "Player One wins!";
			} else {
				message += "Player Two wins!";
			}
		}
		
		JOptionPane.showMessageDialog(gamePanel, message, "Game Over", JOptionPane.PLAIN_MESSAGE);
	}
}
