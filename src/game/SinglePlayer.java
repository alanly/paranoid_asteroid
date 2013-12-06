package game;


import javax.swing.JOptionPane;

import game.ui.GameCanvas;
import game.ui.menu.GamePanel;

public class SinglePlayer extends GameType {
    private boolean saved = false;
    public SinglePlayer(GamePanel gamePanel) {
        super(gamePanel);
    }
    public void playGame(boolean loadSaved, GameCanvas canvas, GameController gameController) {
        BasicGameState state;

        if (loadSaved) {
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

        long points = state.getPoints();
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
    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}