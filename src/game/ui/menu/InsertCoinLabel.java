package game.ui.menu;

import game.Colors;
import game.Fonts;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * InsertCoinLabel is a subclass of JLabel and represents a blinking label with the text "Insert coin to start".
 */
public class InsertCoinLabel extends JLabel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final String COIN_TEXT = "Insert coin to start";
	private static final int PADDING = 5;
	
	private Color visibleColor = Colors.WHITE.getColor();
	private Color invisibleColor = Colors.DARK_BLUE.getColor();
	private boolean visible = true;
	
	/**
	 * Creates a new InsertCoinLabel with a new Timer dictating the blink rate of the label.
	 */
	public InsertCoinLabel() {
		super(COIN_TEXT);
		
		this.setOpaque(false);
		this.setHorizontalAlignment(SwingConstants.RIGHT);
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, PADDING, PADDING));
		this.setFont(Fonts.HUD_FONT.getFont());
		this.setForeground(visibleColor);
		
		new Timer(750, this).start();
	}
	
	/**
	 * Toggles the visibility of the label
	 * @param e the ActionEvent which triggers the toggling
	 */
	public void actionPerformed(ActionEvent e) {
		if (this.visible) {
			this.setForeground(visibleColor);
		} else {
			this.setForeground(invisibleColor);
		}
		
		this.visible = !this.visible;
	}
}
