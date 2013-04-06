package game.ui.menu;

import game.ui.GameCanvas;

import java.awt.BorderLayout;

public class GamePanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	
	private GameCanvas canvas;
	
	public GamePanel() {
		canvas = new GameCanvas();
		this.setLayout(new BorderLayout());
		this.add(canvas, BorderLayout.CENTER);
	}
	
	public GameCanvas getGameCanvas() {
		return canvas;
	}
}
