package game.ui.menu;

import game.Colors;
import game.ui.GameCanvas;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * BasePanel is a subclass of JPanel and represents the basic panel design to which all panels used adhere
 */
public abstract class BasePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new BasePanel
	 */
	public BasePanel() {
		this.setPreferredSize(new Dimension(GameCanvas.WIDTH, GameCanvas.HEIGHT));
		this.setBackground(Colors.DARK_BLUE.getColor());
		this.setFocusable(false);
	}
}
