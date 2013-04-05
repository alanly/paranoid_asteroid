package game;

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
	}
	
	private long playSinglePlayer() {
		return 0;
	}
	
	private long playTwoPlayer() {
		return 0;
	}
}
