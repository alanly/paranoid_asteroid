package game;


import javax.swing.JOptionPane;

import game.ui.GameCanvas;
import game.ui.menu.GamePanel;

/**
 * @see game.GameController.GameType#TWO_PLAYER
 */
public class TwoPlayer extends GameType {
    private static final int MAX_TWO_PLAYER_TURNS = 3;

    public TwoPlayer(GamePanel gamePanel) {
        super(gamePanel);
    }
    public void playGame(boolean loadSaved, GameCanvas canvas, GameController gameController) {
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