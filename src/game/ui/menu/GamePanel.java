package game.ui.menu;

import game.ui.GameCanvas;

import java.awt.BorderLayout;

/**
 * The GamePanel is a subclass of BasePanel and represents the panel containing the GameCanvas.
 */
public class GamePanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	
	private GameCanvas canvas;
	
	/**
	 * Creates a new GamePanel with a new GameCanvas added to it
	 */
	public GamePanel() {
		canvas = new GameCanvas();
		this.setLayout(new BorderLayout());
		this.add(canvas, BorderLayout.CENTER);
	}
	
	/**
	 * Returns the GameCanvas <tt>canvas</tt>
	 * @return the GameCanvas <tt>canvas</tt>
	 */
	public GameCanvas getGameCanvas() {
		return canvas;
	}
}
