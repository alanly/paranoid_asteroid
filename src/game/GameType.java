package game;


import game.ui.GameCanvas;
import game.ui.menu.GamePanel;

public abstract class GameType {
    protected Game game;
    protected GamePanel gamePanel;

    public GameType(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public abstract void playGame(boolean loadSaved, GameCanvas canvas, GameController gameController);

    /**
     * Binds the <tt>game</tt> to <tt>canvas</tt>, revalidates and repaint <tt>gamepanel</tt> and starts the game.
     * @param game
     * @param canvas
     */
    protected void bindAndStartGame(Game game, GameCanvas canvas) {
        canvas.setGame(game);

        gamePanel.revalidate();
        gamePanel.repaint();

        game.start();
    }
}