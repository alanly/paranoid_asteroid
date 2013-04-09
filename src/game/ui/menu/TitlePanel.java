package game.ui.menu;

import game.Fonts;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TitlePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel titleLabel;
	
	public TitlePanel() {
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		titleLabel = new JLabel("<html>Paranoid<br>Asteroid<br>&nbsp;</html>");
		titleLabel.setFont(Fonts.TITLE_FONT.getFont());
		titleLabel.setForeground(new Color(0xF0F0F0));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		this.add(titleLabel);
	}
}
