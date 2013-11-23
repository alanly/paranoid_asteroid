package game;

import game.events.PauseHandler;
import game.events.SaveHandler;
import game.ui.GameCanvas;
import game.ui.menu.GamePanel;

import javax.swing.JOptionPane;

public class GameController implements PauseHandler, SaveHandler {
	public enum GameType {
		SINGLE_PLAYER,
		TWO_PLAYER
	}
	
	private static final int MAX_TWO_PLAYER_TURNS = 3;
	
	private Game game;
	private GamePanel gamePanel;
	private boolean saved = false;
	/**
	 * Creates a new GameController and assigns to it a GamePanel
	 * @param gamePanel GamePanel which will be assigned to the controller
	 */
	public GameController(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	/**
	 * Calls the playGame method with the parameter <tt>type</tt> and <tt>false</tt> as the second parameter 
	 * @param type
	 */
	public void playGame(GameType type) {
		playGame(type, false);
	}
	/**
	 * launches a single player if <tt>type</tt> is <tt>SINGLE_PLAYER</tt> and a two player game if it is <tt>TWO_PLAYER</tt>.
	 * @param type desire GameType
	 * @param loadSaved If <tt>true</tt> and <tt>type</tt> is <tt>SINGLE_PLAYER</tt>, loads a single player. Starts a new two player game otherwise.
	 */
	public void playGame(GameType type, boolean loadSaved) {
		GameCanvas canvas = gamePanel.getGameCanvas();
		
		if (type == GameType.SINGLE_PLAYER) {
			playSinglePlayer(canvas, loadSaved);
		} else if (type == GameType.TWO_PLAYER) {
			playTwoPlayer(canvas);
		}
	}
	/**
	 * calls the <tt>game</tt> handlePause() method
	 */
	public void handlePause() {
		if (game != null) {
			game.togglePause();
		}
	}
	/**
	 * Changes <tt>saved</tt> to <tt>true</tt> and calls the <tt>game</tt> handleSave() method 
	 */
	public void handleSave() {
		if (game != null) {
			saved = true;
			game.handleSave();
		}
	}
	/**
	 * Starts a new single player game or loads one from the load from BasicGameState. After the game is over without being saved, the method gets the points from the game and checks if a highscore was reached.
	 * If a highscore is reached a dialogue box prompts the user for their name.
	 * @param canvas GameCanvas on which the game field exists
	 * @param loadGame if <tt>true</tt>, loads a game from BasicGameState. Starts a new game otherwise.
	 */
	private void playSinglePlayer(GameCanvas canvas, boolean loadGame) {
		BasicGameState state;
		
		if (loadGame) {
			state = BasicGameState.load();
			
			if (state == null) {
				JOptionPane.showMessageDialog(gamePanel, "Could not load game!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {
			state = new BasicGameState();
		}
		
		game = new Game(state);
		bindAndStartGame(game, canvas);
		
		// Don't save high score if the game was saved
		if (saved) {
			return;
		}
		
		long points = game.getScore().getPoints();
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
	/**
	 * Starts a new two player game or loads one from the load from BasicGameState.
	 * @param canvas
	 */
	private void playTwoPlayer(GameCanvas canvas) {
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
	/**
	 * Binds the <tt>game</tt> to <tt>canvas</tt>, revalidates and repaint <tt>gamepanel</tt> and starts the game.
	 * @param game
	 * @param canvas
	 */
	private void bindAndStartGame(Game game, GameCanvas canvas) {
		canvas.setGame(game);
		
		gamePanel.revalidate();
		gamePanel.repaint();
		
		game.start();
	}
	/**
	 * Announces the winner of a two player game in a dialogue box. 
	 * @param p1 points accumulated by player one
	 * @param p2 points accumulated by player two
	 */
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
