package game.ui.menu;

import game.ui.GameCanvas;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public abstract class BasePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final Color DEFAULT_BACKGROUND = new Color(0x292b36);
	
	public BasePanel() {
		this.setPreferredSize(new Dimension(GameCanvas.WIDTH, GameCanvas.HEIGHT));
		this.setBackground(DEFAULT_BACKGROUND);
		this.setFocusable(false);
	}
}
