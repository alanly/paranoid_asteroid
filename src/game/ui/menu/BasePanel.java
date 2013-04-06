package game.ui.menu;

import game.ui.GameCanvas;

import java.awt.Dimension;

import javax.swing.JPanel;

public abstract class BasePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public BasePanel() {
		this.setPreferredSize(new Dimension(GameCanvas.WIDTH, GameCanvas.HEIGHT));
	}
}
