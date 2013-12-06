package game;

import game.events.PauseHandler;
import game.events.SaveHandler;
import game.ui.GameCanvas;
import game.ui.menu.GamePanel;

public class GameController implements PauseHandler, SaveHandler {


    private Game game;
    private GameType gameType;
    private GamePanel gamePanel;
    //private boolean saved = false;
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
        gameType = type;

        gameType.playGame(loadSaved, canvas, this);
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
            ((SinglePlayer)gameType).setSaved(true);
            game.handleSave();
        }
    }
}
